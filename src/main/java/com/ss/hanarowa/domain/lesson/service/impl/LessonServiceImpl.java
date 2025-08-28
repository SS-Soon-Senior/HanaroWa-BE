package com.ss.hanarowa.domain.lesson.service.impl;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.lesson.dto.request.AppliedLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.request.OfferedLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.AppliedLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.lesson.service.LessonService;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMoreDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.CurriculumResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.ReviewResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.Review;
import com.ss.hanarowa.domain.member.repository.MyLessonRepository;
import com.ss.hanarowa.domain.lesson.repository.ReviewRepository;
import com.ss.hanarowa.domain.member.entity.MyLesson;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
	private final LessonRepository lessonRepository;
	private final MyLessonRepository myLessonRepository;
	private final ReviewRepository reviewRepository;

	@Override
	public LessonMoreDetailResponseDTO getLessonMoreDetail(Long lessonId) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new RuntimeException("Lesson not found"));

		// LessonGisu 정보와 각 기수별 수강 인원 수 조회
		List<LessonGisuResponseDTO> lessonGisuDTOs = lesson.getLessonGisus().stream()
			.map(lessonGisu -> {
				int currentEnrollment = myLessonRepository.countByLessonGisu(lessonGisu);

				List<CurriculumResponseDTO> curriculumDTOs = lessonGisu.getCurriculums().stream()
					.map(curriculum -> CurriculumResponseDTO.builder()
						.id(curriculum.getId())
						.content(curriculum.getContent())
						.build())
					.collect(Collectors.toList());

				return LessonGisuResponseDTO.builder()
					.id(lessonGisu.getId())
					.capacity(lessonGisu.getCapacity())
					.lessonFee(lessonGisu.getLessonFee())
					.duration(lessonGisu.getDuration())
					.lessonState(lessonGisu.getLessonState())
					.currentEnrollment(currentEnrollment)
					.curriculums(curriculumDTOs)
					.build();
			})
			.collect(Collectors.toList());

		// 전체 리뷰 정보 수집 (Lesson에 따른 모든 LessonGisu의 리뷰)
		List<Review> allReviews = lesson.getLessonGisus().stream()
			.flatMap(lessonGisu -> reviewRepository.findByLessonGisu(lessonGisu).stream())
			.toList();

		// Review 엔티티를 ReviewResponseDTO로 변환
		List<ReviewResponseDTO> reviewDTOs = allReviews.stream()
			.map(review -> ReviewResponseDTO.builder()
				.id(review.getId())
				.rating(review.getRating())
				.reviewTxt(review.getReviewTxt())
				.memberName(review.getMember().getName())
				.lessonGisuId(review.getLessonGisu().getId())
				.build())
			.collect(Collectors.toList());

		double averageRating = allReviews.stream()
			.mapToInt(Review::getRating)
			.average()
			.orElse(0.0);

		return LessonMoreDetailResponseDTO.builder()
			.lessonName(lesson.getLessonName())
			.instructor(lesson.getInstructor())
			.instruction(lesson.getInstruction())
			.description(lesson.getDescription())
			.category(lesson.getCategory())
			.lessonImg(lesson.getLessonImg())
			.reviews(reviewDTOs)
			.averageRating(averageRating)
			.totalReviews(allReviews.size())
			.lessonGisus(lessonGisuDTOs)
			.build();
	}

	// 신청 강좌 목록
	@Override
	public List<LessonListResponseDTO> getAllAppliedLessons(Long memberId, AppliedLessonRequestDTO req){
		List<MyLesson> myLessons = myLessonRepository.findAllByMemberId(memberId);

		if(myLessons.isEmpty()){
			throw new GeneralException(ErrorStatus.APPLIED_NOT_FOUND);
		}

		return myLessons.stream().map(myLesson -> {
			LessonGisu gisu = myLesson.getLessonGisu();
			Lesson lesson = gisu.getLesson();
			LessonRoom room = gisu.getLessonRoom();

			return LessonListResponseDTO.builder()
										.lessonId(lesson.getId())
										.lessonGisuId(gisu.getId())
										.lessonState(gisu.getLessonState())
										.startedAt(gisu.getStartedAt())
										.lessonName(lesson.getLessonName())
										.instructorName(lesson.getMember().getName()) //일단 외부강사 말고 개설 강좌만
										.duration(gisu.getDuration())
										.lessonRoomName(room.getName())
										//리뷰 추가해야함
										.build();
		}).toList();
	}

	// 개설 강좌 목록
	@Override
	public List<LessonListResponseDTO> getAllOfferedLessons(Long memberId, OfferedLessonRequestDTO req){
		List<Lesson> offeredLessons = lessonRepository.findAllByMemberId(memberId);

		return offeredLessons.stream().flatMap(lesson -> lesson.getLessonGisus().stream().map(gisu -> { LessonRoom room = gisu.getLessonRoom();

			return LessonListResponseDTO.builder()
										.lessonId(lesson.getId())
										.lessonGisuId(gisu.getId())
										.lessonState(gisu.getLessonState())
										.startedAt(gisu.getStartedAt())
										.lessonName(lesson.getLessonName())
										.instructorName(lesson.getMember().getName())
										.duration(gisu.getDuration())
										.lessonRoomName(room.getName())
										.build();
			})
		).toList();
	}
}
