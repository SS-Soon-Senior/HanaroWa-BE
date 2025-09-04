package com.ss.hanarowa.domain.lesson.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.lesson.dto.request.CreateLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.request.TimeAvailabilityRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.MyOpenLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.TimeAvailabilityResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.entity.RoomTime;
import com.ss.hanarowa.domain.lesson.entity.LessonState;
import com.ss.hanarowa.domain.lesson.entity.Curriculum;
import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.lesson.dto.response.LessonInfoResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListByBranchIdResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListSearchResponseDTO;
import com.ss.hanarowa.domain.lesson.event.ReservationChangedEvent;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRoomRepository;
import com.ss.hanarowa.domain.lesson.repository.RoomTimeRepository;
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
	private final RoomTimeRepository roomTimeRepository;
	private final CurriculumRepository curriculumRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

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
					.lessonRoom(lessonGisu.getLessonRoom().getName())
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

			boolean isnotopened = isNotOpened(gisu);
			String branchname = gisu.getLesson().getBranch().getName();

			return LessonListResponseDTO.builder()
										.lessonId(lesson.getId())
										.lessonGisuId(gisu.getId())
										.lessonState(gisu.getLessonState())
										.startedAt(formattedStartedAt)
										.lessonName(lesson.getLessonName())
										.instructorName(lesson.getInstructor())
										.duration(formattedLessonFirstDate)
										.reservedAt(formattedLessonFirstDate)
										.lessonRoomName(branchname + " " +room.getName())
										.isInProgress(isprogress)
										.isReviewed(reviewed)
										.isNotStarted(isnotopened)
										.build();
		}).toList();
	}

	private static String getFormattedStartedAt(LocalDateTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		return time.format(formatter);

	}



	// 개설 강좌 목록
	@Override
	public List<MyOpenLessonListResponseDTO> getAllOfferedLessons(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		List<Lesson> offeredLessons = lessonRepository.findAllByMemberId(member.getId());


		return offeredLessons.stream()
							 .flatMap(lesson -> lesson.getLessonGisus().stream().map(gisu -> {
								 LessonRoom room = gisu.getLessonRoom();
								 String formattedStartedAt = getFormattedLessonFirstDate(gisu);

								 boolean isprogress = isprogress(gisu);
								 String lessonOpenedAt = getFormattedStartedAt(lesson.getOpenedAt());

								 String branchname = gisu.getLesson().getBranch().getName();
								 return MyOpenLessonListResponseDTO.builder()
															 .lessonId(lesson.getId())
															 .lessonGisuId(gisu.getId())
															 .lessonState(gisu.getLessonState())
															 .startedAt(formattedStartedAt)
															 .lessonName(lesson.getLessonName())
															 .instructorName(lesson.getInstructor())
															 .lessonRoomName(branchname + " " + room.getName())
															 .openedAt(lessonOpenedAt)
									  						 .isInProgress(isprogress)
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
		applicationEventPublisher.publishEvent(new ReservationChangedEvent(lessonGisuId));

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
			LessonRoom assignedRoom = assignAvailableRoom(branch.getId(), gisuDto.getDuration());
			if (assignedRoom == null) {
				throw new GeneralException(ErrorStatus.LESSON_ROOM_NOT_AVAILABLE);
			}

			LessonGisu lessonGisu = LessonGisu.builder()
				.capacity(gisuDto.getCapacity())
				.lessonFee(gisuDto.getLessonFee())
				.duration(gisuDto.getDuration())
				.lessonState(LessonState.PENDING)
				.lesson(savedLesson)
				.lessonRoom(assignedRoom)
				.startedAt(LocalDateTime.now())
				.build();

			LessonGisu savedLessonGisu = lessonGisuRepository.save(lessonGisu);

			createRoomTimeSlots(assignedRoom, savedLessonGisu, gisuDto.getDuration());

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

	// ✅ [수정] 시작 날짜를 안전하게 추출하는 헬퍼 메소드
	public LocalDate getStartDate(String duration) {
		try {
			String dateRangePart = duration.split(" ")[0]; // "2025-01-01~2025-02-28"
			String startDateString = dateRangePart.split("~")[0]; // "2025-01-01"
			return LocalDate.parse(startDateString);
		} catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
			// log.error("강의 기간 문자열에서 시작 날짜를 파싱할 수 없습니다: {}", duration, e);
			// 기본값이나 예외 처리가 필요하다면 여기에 추가
			return null;
		}
	}

	// ✅ [수정] 종료 날짜를 안전하게 추출하는 헬퍼 메소드
	public LocalDate getEndDate(String duration) {
		try {
			String dateRangePart = duration.split(" ")[0]; // "2025-01-01~2025-02-28"
			String endDateString = dateRangePart.split("~")[1]; // "2025-02-28"
			return LocalDate.parse(endDateString);
		} catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
			// log.error("강의 기간 문자열에서 종료 날짜를 파싱할 수 없습니다: {}", duration, e);
			return null;
		}
	}

	// ✅ [수정] isNotOpened 메소드
	public boolean isNotOpened(LessonGisu gisu) {
		LocalDate startDate = getStartDate(gisu.getDuration());
		if (startDate == null) return false; // 파싱 실패 시 처리

		LocalDate today = LocalDate.now();
		return today.isBefore(startDate);
	}

	// ✅ [수정] isprogress 메소드
	public boolean isprogress(LessonGisu gisu) {
		LocalDate endDate = getEndDate(gisu.getDuration());
		if (endDate == null) return false; // 파싱 실패 시 처리

		LocalDate today = LocalDate.now();
		return !today.isAfter(endDate);
	}

	// ✅ [수정] getFormattedLessonFirstDate 메소드
	public String getFormattedLessonFirstDate(LessonGisu gisu) {
		try {
			LocalDate startDate = getStartDate(gisu.getDuration());
			if (startDate == null) return "날짜 정보 없음";

			DateTimeFormatter dateFormatter = DateTimeFormatter
				.ofPattern("M월 d일 (E)")
				.withLocale(Locale.KOREAN);
			String formattedDate = startDate.format(dateFormatter);

			String[] parts = gisu.getDuration().split(" ");
			String timePart = parts[parts.length - 1];
			String startTimeString;

			if (timePart.contains("-")) {
				startTimeString = timePart.split("-")[0];
			} else {
				startTimeString = timePart;
			}

			LocalTime startTime = LocalTime.parse(startTimeString);
			DateTimeFormatter timeFormatter = DateTimeFormatter
				.ofPattern("a h:mm")
				.withLocale(Locale.KOREAN);
			String formattedTime = startTime.format(timeFormatter);

			return formattedDate + " " + formattedTime;

		} catch (Exception e) {
			// log.error("날짜/시간 포맷팅 중 오류 발생: {}", gisu.getDuration(), e);
			return "날짜/시간 형식 오류";
		}
	}

	private LessonRoom assignAvailableRoom(Long branchId, String duration) {
		List<LessonRoom> availableRooms = lessonRoomRepository.findByBranchId(branchId);

		for (LessonRoom room : availableRooms) {
			if (isRoomAvailable(room.getId(), duration)) {
				return room;
			}
		}

		return null;
	}

	private boolean isRoomAvailable(Long roomId, String duration) {
		List<LocalDateTime[]> timeSlots = parseDuration(duration);

		for (LocalDateTime[] timeSlot : timeSlots) {
			List<RoomTime> conflicts = roomTimeRepository.findConflictingRoomTimes(
				roomId, timeSlot[0], timeSlot[1]
			);

			if (!conflicts.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	private List<LocalDateTime[]> parseDuration(String duration) {
		List<LocalDateTime[]> timeSlots = new ArrayList<>();

		// 패턴: 2025-09-02 ~ 2025-09-29 tue-thu 17:00-18:00
		Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})\\s*~\\s*(\\d{4}-\\d{2}-\\d{2})\\s+(\\w+(?:-\\w+)?)\\s+(\\d{2}:\\d{2})-(\\d{2}:\\d{2})");
		Matcher matcher = pattern.matcher(duration);

		if (matcher.find()) {
			LocalDate startDate = LocalDate.parse(matcher.group(1));
			LocalDate endDate = LocalDate.parse(matcher.group(2));
			String daysStr = matcher.group(3);
			LocalTime startTime = LocalTime.parse(matcher.group(4));
			LocalTime endTime = LocalTime.parse(matcher.group(5));

			List<DayOfWeek> lessonDays = parseDays(daysStr);

			LocalDate currentDate = startDate;
			while (!currentDate.isAfter(endDate)) {
				if (lessonDays.contains(currentDate.getDayOfWeek())) {
					LocalDateTime slotStart = currentDate.atTime(startTime);
					LocalDateTime slotEnd = currentDate.atTime(endTime);
					timeSlots.add(new LocalDateTime[]{slotStart, slotEnd});
				}
				currentDate = currentDate.plusDays(1);
			}
		}

		return timeSlots;
	}

	private List<DayOfWeek> parseDays(String daysStr) {
		List<DayOfWeek> days = new ArrayList<>();
		String[] dayRanges = daysStr.split("-");

		if (dayRanges.length == 2) {
			DayOfWeek startDay = parseDayOfWeek(dayRanges[0]);
			DayOfWeek endDay = parseDayOfWeek(dayRanges[1]);

			DayOfWeek currentDay = startDay;
			while (true) {
				days.add(currentDay);
				if (currentDay == endDay) break;
				currentDay = currentDay.plus(1);
			}
		} else {
			days.add(parseDayOfWeek(daysStr));
		}

		return days;
	}

	private DayOfWeek parseDayOfWeek(String dayStr) {
		return switch (dayStr.toLowerCase()) {
			case "mon" -> DayOfWeek.MONDAY;
			case "tue" -> DayOfWeek.TUESDAY;
			case "wed" -> DayOfWeek.WEDNESDAY;
			case "thu" -> DayOfWeek.THURSDAY;
			case "fri" -> DayOfWeek.FRIDAY;
			case "sat" -> DayOfWeek.SATURDAY;
			case "sun" -> DayOfWeek.SUNDAY;
			default -> DayOfWeek.MONDAY;
		};
	}

	private void createRoomTimeSlots(LessonRoom room, LessonGisu lessonGisu, String duration) {
		List<LocalDateTime[]> timeSlots = parseDuration(duration);

		for (LocalDateTime[] timeSlot : timeSlots) {
			RoomTime roomTime = RoomTime.builder()
				.lessonRoom(room)
				.startedAt(timeSlot[0])
				.endedAt(timeSlot[1])
				.build();

			roomTimeRepository.save(roomTime);
		}
	}

	@Override
	public TimeAvailabilityResponseDTO checkTimeAvailability(TimeAvailabilityRequestDTO requestDTO) {
		List<LessonRoom> availableRooms = lessonRoomRepository.findByBranchId(requestDTO.getBranchId());

		// duration이 시간 정보를 포함하지 않는 경우 (예: "2025-09-04 ~ 2025-09-30 mon-wed")
		// 모든 가능한 시간대의 가용성을 체크
		if (!requestDTO.getDuration().matches(".*\\d{2}:\\d{2}-\\d{2}:\\d{2}.*")) {
			return checkAllTimeSlotsAvailability(availableRooms, requestDTO.getDuration());
		}

		// 기존 로직: 특정 시간이 포함된 경우
		List<LocalDateTime[]> timeSlots = parseDuration(requestDTO.getDuration());

		List<TimeAvailabilityResponseDTO.TimeSlotAvailability> timeSlotAvailabilities = new ArrayList<>();
		int totalAvailableSlots = 0;

		for (LocalDateTime[] timeSlot : timeSlots) {
			int availableRoomsForSlot = 0;

			for (LessonRoom room : availableRooms) {
				List<RoomTime> conflicts = roomTimeRepository.findConflictingRoomTimes(
					room.getId(), timeSlot[0], timeSlot[1]
				);

				if (conflicts.isEmpty()) {
					availableRoomsForSlot++;
				}
			}

			TimeAvailabilityResponseDTO.TimeSlotAvailability slotAvailability =
				TimeAvailabilityResponseDTO.TimeSlotAvailability.builder()
					.startTime(timeSlot[0])
					.endTime(timeSlot[1])
					.available(availableRoomsForSlot > 0)
					.availableRoomsCount(availableRoomsForSlot)
					.build();

			timeSlotAvailabilities.add(slotAvailability);

			if (availableRoomsForSlot > 0) {
				totalAvailableSlots++;
			}
		}

		// 개별 시간대별로 판단하도록 변경 - 일부 시간대가 사용 가능하면 전체적으로도 사용 가능
		boolean overallAvailable = totalAvailableSlots > 0;

		return TimeAvailabilityResponseDTO.builder()
			.available(overallAvailable)
			.availableRoomsCount(availableRooms.size()) // 전체 방 수 반환
			.timeSlots(timeSlotAvailabilities)
			.build();
	}

	@Override
	@Transactional
	public void deleteLessonReservation(Long lessonGisuId, String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		myLessonRepository.deleteByMemberAndLessonGisuId(member, lessonGisuId);
		applicationEventPublisher.publishEvent(new ReservationChangedEvent(lessonGisuId));
	}

	private TimeAvailabilityResponseDTO checkAllTimeSlotsAvailability(List<LessonRoom> availableRooms, String duration) {
		// 가능한 모든 시간대 정의 (9시부터 22시까지 1시간씩)
		List<LocalTime> possibleStartTimes = new ArrayList<>();
		for (int hour = 9; hour <= 21; hour++) {
			possibleStartTimes.add(LocalTime.of(hour, 0));
		}

		// duration에서 날짜와 요일 정보 추출
		List<LocalDate> targetDates = parseDurationWithoutTime(duration);

		List<TimeAvailabilityResponseDTO.TimeSlotAvailability> timeSlotAvailabilities = new ArrayList<>();
		int totalAvailableSlots = 0;

		// 각 날짜의 각 시간대 체크
		for (LocalDate date : targetDates) {
			for (LocalTime startTime : possibleStartTimes) {
				LocalTime endTime = startTime.plusHours(1);
				LocalDateTime slotStart = date.atTime(startTime);
				LocalDateTime slotEnd = date.atTime(endTime);

				int availableRoomsForSlot = 0;

				for (LessonRoom room : availableRooms) {
					List<RoomTime> conflicts = roomTimeRepository.findConflictingRoomTimes(
						room.getId(), slotStart, slotEnd
					);

					if (conflicts.isEmpty()) {
						availableRoomsForSlot++;
					}
				}

				TimeAvailabilityResponseDTO.TimeSlotAvailability slotAvailability =
					TimeAvailabilityResponseDTO.TimeSlotAvailability.builder()
						.startTime(slotStart)
						.endTime(slotEnd)
						.available(availableRoomsForSlot > 0)
						.availableRoomsCount(availableRoomsForSlot)
						.build();

				timeSlotAvailabilities.add(slotAvailability);

				if (availableRoomsForSlot > 0) {
					totalAvailableSlots++;
				}
			}
		}

		boolean overallAvailable = totalAvailableSlots > 0;

		return TimeAvailabilityResponseDTO.builder()
			.available(overallAvailable)
			.availableRoomsCount(availableRooms.size())
			.timeSlots(timeSlotAvailabilities)
			.build();
	}

	private List<LocalDate> parseDurationWithoutTime(String duration) {
		List<LocalDate> dates = new ArrayList<>();

		// 패턴: 2025-09-04 ~ 2025-09-30 mon-wed
		Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})\\s*~\\s*(\\d{4}-\\d{2}-\\d{2})\\s+(\\w+(?:-\\w+)?)");
		Matcher matcher = pattern.matcher(duration);

		if (matcher.find()) {
			LocalDate startDate = LocalDate.parse(matcher.group(1));
			LocalDate endDate = LocalDate.parse(matcher.group(2));
			String daysStr = matcher.group(3);

			List<DayOfWeek> lessonDays = parseDays(daysStr);

			LocalDate currentDate = startDate;
			while (!currentDate.isAfter(endDate)) {
				if (lessonDays.contains(currentDate.getDayOfWeek())) {
					dates.add(currentDate);
				}
				currentDate = currentDate.plusDays(1);
			}
		}

		return dates;
	}
}
