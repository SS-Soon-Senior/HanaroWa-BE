package com.ss.hanarowa.domain.ai.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.hanarowa.domain.ai.client.GeminiApiClient;
import com.ss.hanarowa.domain.ai.dto.response.CourseRecResponseDto;
import com.ss.hanarowa.domain.ai.dto.response.JobRecResponseDto;
import com.ss.hanarowa.domain.ai.dto.response.RecResponseDto;
import com.ss.hanarowa.domain.ai.service.AIRecService;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.MyLesson;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AIRecServiceImpl implements AIRecService {

	private final MemberRepository memberRepository;
	private final LessonRepository lessonRepository;
	private final GeminiApiClient geminiApiClient;
	private final ObjectMapper objectMapper;

	@Override
	public RecResponseDto recommendCourses(String interest) {
		// DB에서 강좌 목록 조회
		List<Lesson> lessons = lessonRepository.findAll();

		// 강좌 데이터 가공
		String courseDataString = lessons.stream()
			.map(lesson -> String.format(
				"강좌명: %s, 설명: %s, 카테고리: %s",
				lesson.getLessonName(), lesson.getDescription(), lesson.getCategory()
			))
			.collect(Collectors.joining("\n"));

		// 프롬프트 생성
		String prompt = createCoursePrompt(interest, courseDataString);

		// Gemini API 호출
		String geminiResponse = geminiApiClient.generateContent(prompt);

		// DTO로 파싱하여 반환
		return parseCourseResponse(geminiResponse);
	}

	@Override
	public RecResponseDto recommendJobs(String experience) {
		String email = getCurrentUserEmail();
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		List<MyLesson> myLessons = member.getMyLessons();

		String completedCoursesString = myLessons.stream()
			.map(myLesson -> {
				var lesson = myLesson.getLessonGisu().getLesson();
				return String.format(
					"강좌명: %s (카테고리: %s)",
					lesson.getLessonName(), lesson.getCategory().name()
				);
			})
			.collect(Collectors.joining("\n"));

		if (completedCoursesString.isEmpty()) {
			completedCoursesString = "아직 수강한 강좌가 없습니다.";
		}

		String prompt = createJobPrompt(experience, completedCoursesString);

		String geminiResponse = geminiApiClient.generateContent(prompt);

		return parseJobResponse(geminiResponse);
	}

	// --- Helper Methods (프롬프트 생성 및 응답 파싱) ---

	private String getCurrentUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getName() == null) {
			throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
		}
		return authentication.getName();
	}

	private String createCoursePrompt(String interest, String courseData) {
		return String.format(
			"""
				당신은 시니어들의 디지털 교육과 재취업을 돕는 전문 컨설턴트입니다.
				아래는 현재 수강 가능한 강좌 목록입니다.
					
				---
				[강좌 목록]
				%s
				---
					
				이제 위 목록에 있는 강좌 중에서, "%s" 분야에 관심이 있는 시니어 수강생에게 가장 적합한 강좌 3개를 추천해주세요.
							
				각 강좌를 추천하는 이유를 시니어의 눈높이에 맞춰 쉽고 친절하게 1~2문장으로 설명해주세요.
							
				응답은 다른 설명 없이, 오직 유효한 JSON 객체만을 포함해야 합니다.
				JSON은 반드시 다음 구조를 따라야 합니다:
				```json
				{
				  "recommendations": [
					{
					  "name": "추천 강좌명",
					  "description": "이 강좌에 대한 설명...",
					  "reason": "이 강좌를 추천하는 이유..."
					},
					{
					  "name": "다음 추천 강좌명",
					  "description": "이 강좌에 대한 설명...",
					  "reason": "이 강좌를 추천하는 이유..."
					}
				  ]
				}
				```
				""",
			courseData,
			interest
		);
	}

	private String createJobPrompt(String experience, String completedCourses) {
		return String.format(
			"""
				당신은 시니어들의 성공적인 재취업을 돕는 전문 직업 컨설턴트입니다.
					
				한 시니어 지원자에 대한 정보는 다음과 같습니다.
				- 주요 경험/경력: "%s"
				- 최근 수강한 교육 목록:
				---
				%s
				---
					
				위 정보를 바탕으로, 이 시니어에게 가장 적합한 직업 3가지를 추천해주세요.
				특히 이 분이 수강한 교육 내용을 적극적으로 활용할 수 있는 직업 위주로 추천해주십시오.
					
				각 직업에 대한 설명과 함께, 왜 해당 직업이 이 시니어에게 적합한지 그 이유를 구체적으로 설명해주세요.
							
				응답은 다른 설명 없이, 오직 유효한 JSON 객체만을 포함해야 합니다.
				JSON은 반드시 다음 구조를 따라야 합니다:
				```json
				{
				  "recommendations": [
					{
					  "name": "추천 직업명",
					  "description": "이 직업에 대한 설명...",
					  "reason": "이 직업이 시니어에게 적합한 이유..."
					}
				  ]
				}
				```
				""",
			experience,
			completedCourses
		);
	}

	private RecResponseDto parseJobResponse(String jsonResponse) {
		try {
			log.info("Raw response from Gemini API: {}", jsonResponse);

			String cleanedJson = jsonResponse.trim();
			if (cleanedJson.startsWith("```json")) {
				cleanedJson = cleanedJson.substring(7, cleanedJson.length() - 3).trim();
			} else if (cleanedJson.startsWith("```")) {
				cleanedJson = cleanedJson.substring(3, cleanedJson.length() - 3).trim();
			}

			return objectMapper.readValue(cleanedJson, RecResponseDto.class);
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.AI_API_ERROR);
		}
	}

	private RecResponseDto parseCourseResponse(String jsonResponse) {
		try {

			String cleanedJson = jsonResponse.trim();
			if (cleanedJson.startsWith("```json")) {
				cleanedJson = cleanedJson.substring(7, cleanedJson.length() - 3).trim();
			} else if (cleanedJson.startsWith("```")) {
				cleanedJson = cleanedJson.substring(3, cleanedJson.length() - 3).trim();
			}

			return objectMapper.readValue(cleanedJson, RecResponseDto.class);
		} catch (Exception e) {
			throw new GeneralException(ErrorStatus.AI_API_ERROR);
		}
	}

}