package com.ss.hanarowa.domain.lesson.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.lesson.dto.request.LessonGisuStateUpdateRequestDto;
import com.ss.hanarowa.domain.lesson.dto.response.AdminLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.CurriculumResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuStateUpdateResponseDto;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMemberResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.service.AdminLessonService;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.MyLesson;
import com.ss.hanarowa.domain.member.repository.MyLessonRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminLessonLessonServiceImpl implements AdminLessonService {
	private final LessonRepository lessonRepository;
	private final LessonGisuRepository lessonGisuRepository;
	private final MyLessonRepository myLessonRepository;

	@Override
	public List<AdminLessonListResponseDTO> getAllLessons() {
		List<Lesson> lessons = lessonRepository.findAll();

		if (lessons.isEmpty()) {
			throw new GeneralException(ErrorStatus.LESSON_NOT_FOUND);
		}

		return lessons.stream()
			.map(l -> AdminLessonListResponseDTO.builder()
				.lessonName(l.getLessonName())
				.instructor(l.getInstructor())
				.instruction(l.getInstruction())
				.lessonImg(l.getLessonImg())
				.build())
			.toList();
	}

	/**
	 * 강좌 기수 상태 변경 (승인 / 거절)
	 * @param lessonGisuId 상태를 변경할 LessonGisu의 ID
	 * @param request 변경할 상태 정보 (APPROVED or REJECTED)
	 */
	@Override
	@Transactional
	public LessonGisuStateUpdateResponseDto updateLessonGisuState(long lessonGisuId,
		LessonGisuStateUpdateRequestDto request) {
		LessonGisu lessonGisu = lessonGisuRepository.findById(lessonGisuId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LESSONGISU_NOT_FOUND));

		lessonGisu.updateState(request.getLessonState());

		return LessonGisuStateUpdateResponseDto.builder()
			.lessonGisuId(lessonGisu.getId())
			.lessonState(lessonGisu.getLessonState().name())
			.build();
	}

	@Override
	public LessonDetailResponseDTO getLessonDetail(Long lessonId) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LESSON_NOT_FOUND));

		List<LessonGisu> lessonGisus = lessonGisuRepository.findByLessonId(lessonId);
		List<LessonGisuResponseDTO> lessonGisuDTOs = new ArrayList<>();
		
		for (LessonGisu lessonGisu : lessonGisus) {
			List<CurriculumResponseDTO> curriculums = new ArrayList<>();
			
			for (var curriculum : lessonGisu.getCurriculums()) {
				curriculums.add(CurriculumResponseDTO.builder()
					.id(curriculum.getId())
					.content(curriculum.getContent())
					.build());
			}
			
			lessonGisuDTOs.add(LessonGisuResponseDTO.builder()
				.id(lessonGisu.getId())
				.capacity(lessonGisu.getCapacity())
				.lessonFee(lessonGisu.getLessonFee())
				.duration(lessonGisu.getDuration())
				.lessonState(lessonGisu.getLessonState())
				.curriculums(curriculums)
				.build());
		}
		
		return LessonDetailResponseDTO.builder()
			.lessonName(lesson.getLessonName())
			.instructor(lesson.getInstructor())
			.instruction(lesson.getInstruction())
			.description(lesson.getDescription())
			.category(lesson.getCategory())
			.lessonImg(lesson.getLessonImg())
			.lessonGisus(lessonGisuDTOs)
			.build();
	}

	private Member getMember(MyLesson myLesson) {
		return myLesson.getMember();
	}

	@Override
	public List<LessonMemberResponseDTO> findAllByLessonGisuId(long lessonGisuId) {
		return myLessonRepository.findAllByLessonGisuId(lessonGisuId).stream()
			.map(MyLesson::getMember)               // 바로 member 추출
			.map(m -> new LessonMemberResponseDTO(
				m.getName(),
				m.getBranch().getName(), // null-safe
				m.getPhoneNumber(),
				m.getEmail(),
				m.getBirth()
			))
			.toList();
	}
}
