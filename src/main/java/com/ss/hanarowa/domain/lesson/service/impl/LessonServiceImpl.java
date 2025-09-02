package com.ss.hanarowa.domain.lesson.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.lesson.dto.request.CreateLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.MyOpenLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.entity.LessonState;
import com.ss.hanarowa.domain.lesson.entity.Curriculum;
import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.lesson.dto.response.LessonInfoResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListByBranchIdResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListSearchResponseDTO;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRoomRepository;
import com.ss.hanarowa.domain.lesson.repository.CurriculumRepository;
import com.ss.hanarowa.domain.lesson.service.LessonService;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMoreDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.CurriculumResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.ReviewResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Review;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.domain.member.repository.MyLessonRepository;
import com.ss.hanarowa.domain.lesson.repository.ReviewRepository;
import com.ss.hanarowa.domain.member.entity.MyLesson;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
	private final LessonRepository lessonRepository;
	private final MyLessonRepository myLessonRepository;
	private final ReviewRepository reviewRepository;
	private final BranchRepository branchRepository;
	private final MemberRepository memberRepository;
	private final LessonGisuRepository lessonGisuRepository;
	private final LessonRoomRepository lessonRoomRepository;
	private final CurriculumRepository curriculumRepository;

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
	public List<LessonListResponseDTO> getAllAppliedLessons(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		List<MyLesson> myLessons = myLessonRepository.findAllByMemberId(member.getId());

		if (myLessons.isEmpty()) {
			throw new GeneralException(ErrorStatus.APPLIED_NOT_FOUND);
		}

		return myLessons.stream().map(myLesson -> {
			LessonGisu gisu = myLesson.getLessonGisu();
			Lesson lesson = gisu.getLesson();
			LessonRoom room = gisu.getLessonRoom();

			String formattedStartedAt = getFormattedStartedAt(myLesson.getOpenedAt());
			//4월 5일 (금) 오후 2:00
			String formattedLessonFirstDate = getFormattedLessonFirstDate(gisu);

			boolean isprogress = isprogress(gisu);
			List<Review> byMemberAndLessonGisu = reviewRepository.findByMemberAndLessonGisu(member, gisu);

			boolean reviewed = true;
			if (byMemberAndLessonGisu.isEmpty()) {
				reviewed = false;
			}

			return LessonListResponseDTO.builder()
										.lessonId(lesson.getId())
										.lessonGisuId(gisu.getId())
										.lessonState(gisu.getLessonState())
										.startedAt(formattedStartedAt)
										.lessonName(lesson.getLessonName())
										.instructorName(lesson.getMember().getName())
										.duration(formattedLessonFirstDate)
										.reservedAt(formattedLessonFirstDate)
										.lessonRoomName(room.getName())
										.isInProgress(isprogress)
										.isReviewed(reviewed)
										.build();
		}).toList();
	}

	private static boolean isprogress(LessonGisu gisu) {
		String endDateString = gisu.getDuration().split(" ")[2];

		LocalDate endDate = LocalDate.parse(endDateString);

		LocalDate today = LocalDate.now();
		return !today.isAfter(endDate);
	}

	private static String getFormattedLessonFirstDate(LessonGisu gisu) {
		String[] parts = gisu.getDuration().split(" ");
		// 1. 시작 날짜 추출 및 포맷팅 (항상 첫 번째 요소)
		String startDateString = parts[0];
		LocalDate startDate = LocalDate.parse(startDateString);
		DateTimeFormatter dateFormatter = DateTimeFormatter
			.ofPattern("M월 d일 (E)")
			.withLocale(Locale.KOREAN);
		String formattedDate = startDate.format(dateFormatter);

		// 마지막 요소("17:00-18:00" 또는 "17:00")를 가져옴
		String timePart = parts[parts.length - 1];
		String startTimeString;

		// 시간 부분이 "17:00-18:00" 같은 범위인지 확인
		if (timePart.contains("-")) {
			startTimeString = timePart.split("-")[0];
		} else {
			// "17:00" 같은 단일 시간 형식일 경우
			startTimeString = timePart;
		}

		LocalTime startTime = LocalTime.parse(startTimeString);
		DateTimeFormatter timeFormatter = DateTimeFormatter
			.ofPattern("a h:mm")
			.withLocale(Locale.KOREAN);
		String formattedTime = startTime.format(timeFormatter);

		return formattedDate + " " + formattedTime;
	}

	private static String getFormattedStartedAt(LocalDateTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		String formattedStartedAt = time.format(formatter);
		return formattedStartedAt;
	}



	// 개설 강좌 목록
	@Override
	public List<MyOpenLessonListResponseDTO> getAllOfferedLessons(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		List<Lesson> offeredLessons = lessonRepository.findAllByMemberId(member.getId());

		if (offeredLessons.isEmpty()) {
			throw new GeneralException(ErrorStatus.OFFERED_NOT_FOUND);
		}

		return offeredLessons.stream()
							 .flatMap(lesson -> lesson.getLessonGisus().stream().map(gisu -> {
								 LessonRoom room = gisu.getLessonRoom();
								 String formattedStartedAt = getFormattedLessonFirstDate(gisu);

								 String lessonOpenedAt = getFormattedStartedAt(lesson.getOpenedAt());

								 return MyOpenLessonListResponseDTO.builder()
															 .lessonId(lesson.getId())
															 .lessonGisuId(gisu.getId())
															 .lessonState(gisu.getLessonState())
															 .startedAt(formattedStartedAt)
															 .lessonName(lesson.getLessonName())
															 .instructorName(lesson.getMember().getName())
															 .lessonRoomName(room.getName())
															 .openedAt(lessonOpenedAt)
															 .build();
							 }))
							 .toList();
	}

	@Override
	public LessonListByBranchIdResponseDTO getLessonListByBranchId(Long branchId, List<String> categories){
		// 지점 조회
		Branch branch = branchRepository.findById(branchId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.BRANCH_NOT_FOUND));

		// 지점별 강좌 목록 조회 (최신순)
		List<Lesson> lessons = lessonRepository.findByBranchIdOrderByIdDesc(branchId);

		if (categories != null && !categories.isEmpty()) {
			lessons = lessons.stream()
				.filter(lesson -> {
					return categories.contains(lesson.getCategory().toString());
				})
				.toList();
		}

		// 각 강좌별 LessonGisu 정보 및 수강 인원 수 조회
		List<LessonInfoResponseDTO> lessonInfos = lessons.stream()
			.flatMap(lesson -> lesson.getLessonGisus().stream())
			.map(lessonGisu -> {
				int currentEnrollment = myLessonRepository.countByLessonGisu(lessonGisu);

				return LessonInfoResponseDTO.builder()
					.lessonId(lessonGisu.getLesson().getId())
					.lessonGisuId(lessonGisu.getId())
					.lessonName(lessonGisu.getLesson().getLessonName())
					.instructor(lessonGisu.getLesson().getInstructor())
					.lessonImg(lessonGisu.getLesson().getLessonImg())
					.duration(lessonGisu.getDuration())
					.lessonFee(lessonGisu.getLessonFee())
					.capacity(lessonGisu.getCapacity())
					.lessonCategory(lessonGisu.getLesson().getCategory())
					.currentStudentCount(currentEnrollment)
					.build();
			})
			.collect(Collectors.toList());

		return LessonListByBranchIdResponseDTO.builder()
			.branchId(branch.getId())
			.locationName(branch.getLocation().getName())
			.branchName(branch.getName())
			.Lessons(lessonInfos)
			.build();
	}

	@Override
	public List<LessonListSearchResponseDTO> getLessonListSearch(String query) {
		List<Lesson> lessons;

		// query가 null이거나 빈 문자열이면 전체 강좌 조회, 아니면 검색
		if (query == null || query.trim().isEmpty()) {
			lessons = lessonRepository.findAllByOrderByIdDesc();
		} else {
			lessons = lessonRepository.findByLessonNameContainingOrderByIdDesc(query.trim());
		}

		// 각 강좌별 LessonGisu 정보 및 수강 인원 수 조회
		return lessons.stream()
			.flatMap(lesson -> lesson.getLessonGisus().stream())
			.map(lessonGisu -> {
				int currentEnrollment = myLessonRepository.countByLessonGisu(lessonGisu);

				return LessonListSearchResponseDTO.builder()
					.branchId(lessonGisu.getLesson().getBranch().getId())
					.locationName(lessonGisu.getLesson().getBranch().getLocation().getName())
					.branchName(lessonGisu.getLesson().getBranch().getName())
					.lessonId(lessonGisu.getLesson().getId())
					.lessonGisuId(lessonGisu.getId())
					.lessonName(lessonGisu.getLesson().getLessonName())
					.instructor(lessonGisu.getLesson().getInstructor())
					.lessonImg(lessonGisu.getLesson().getLessonImg())
					.duration(lessonGisu.getDuration())
					.lessonFee(lessonGisu.getLessonFee())
					.capacity(lessonGisu.getCapacity())
					.currentStudentCount(currentEnrollment)
					.build();
			})
			.collect(Collectors.toList());
	}

	@Override
	public void applyForLesson(Long lessonGisuId, String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		LessonGisu lessonGisu = lessonGisuRepository.findById(lessonGisuId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LESSONGISU_NOT_FOUND));

		if (member.getMyLessons().stream().anyMatch(myLesson -> myLesson.getLessonGisu().equals(lessonGisu))) {
			throw new GeneralException(ErrorStatus.LESSON_ALREADY_APPLIED);
		}

		int currentEnrollment = myLessonRepository.countByLessonGisuId(lessonGisuId);

		if (currentEnrollment >= lessonGisu.getCapacity()) {
			throw new GeneralException(ErrorStatus.LESSON_CAPACITY_EXCEEDED);
		}

		MyLesson newMyLesson = MyLesson.builder()
			.member(member)
			.lessonGisu(lessonGisu)
			.build();

		myLessonRepository.save(newMyLesson);
	}

	@Override
	@Transactional
	public void createLesson(CreateLessonRequestDTO createLessonRequestDTO, String email,String imageUrl) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

		Branch branch = branchRepository.findById(createLessonRequestDTO.getBranchId())
			.orElseThrow(() -> new GeneralException(ErrorStatus.BRANCH_NOT_FOUND));

		Lesson lesson = Lesson.builder()
			.lessonName(createLessonRequestDTO.getLessonName())
			.instructor(createLessonRequestDTO.getInstructor())
			.instruction(createLessonRequestDTO.getInstruction())
			.description(createLessonRequestDTO.getDescription())
			.category(createLessonRequestDTO.getCategory())
			.lessonImg(imageUrl)
			.branch(branch)
			.member(member)
			.build();

		Lesson savedLesson = lessonRepository.save(lesson);

		for (CreateLessonRequestDTO.CreateLessonGisuRequestDTO gisuDto : createLessonRequestDTO.getLessonGisus()) {
			LessonRoom lessonRoom = lessonRoomRepository.findById(gisuDto.getLessonRoomId())
				.orElseThrow(() -> new GeneralException(ErrorStatus.LESSON_ROOM_NOT_FOUND));

			LessonGisu lessonGisu = LessonGisu.builder()
				.capacity(gisuDto.getCapacity())
				.lessonFee(gisuDto.getLessonFee())
				.duration(gisuDto.getDuration())
				.lessonState(LessonState.PENDING)
				.lesson(savedLesson)
				.lessonRoom(lessonRoom)
				.startedAt(LocalDateTime.now())
				.build();

			LessonGisu savedLessonGisu = lessonGisuRepository.save(lessonGisu);

			for (CreateLessonRequestDTO.CreateCurriculumRequestDTO curriculumDto : gisuDto.getCurriculums()) {
				Curriculum curriculum = Curriculum.builder()
					.content(curriculumDto.getContent())
					.lessonGisu(savedLessonGisu)
					.startedAt(LocalDateTime.now())
					.build();

				curriculumRepository.save(curriculum);
			}
		}
	}
}
