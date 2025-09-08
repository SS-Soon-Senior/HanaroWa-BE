package com.ss.hanarowa.domain.lesson.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.lesson.dto.request.LessonGisuStateUpdateRequestDto;
import com.ss.hanarowa.domain.lesson.dto.request.UpdateLessonDetailRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.request.UpdateLessonGisuRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.AdminLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.AdminManageLessonResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.CurriculumResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuStateUpdateResponseDto;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMemberResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Curriculum;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonState;
import com.ss.hanarowa.domain.lesson.repository.CurriculumRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.service.AdminLessonService;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.member.entity.MyLesson;
import com.ss.hanarowa.domain.member.repository.MyLessonRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminLessonServiceImpl implements AdminLessonService {
	private final LessonRepository lessonRepository;
	private final LessonGisuRepository lessonGisuRepository;
	private final CurriculumRepository curriculumRepository;
	private final MyLessonRepository myLessonRepository;

	@Override
	public List<AdminLessonListResponseDTO> getAllLessons() {
		List<Lesson> lessons = lessonRepository.findAll();

		return lessons.stream()
			.flatMap(lesson -> lesson.getLessonGisus().stream()
				.filter(gisu -> gisu.getLessonState() == LessonState.APPROVED)
				.filter(this::isActiveGisu)
				.map(gisu -> {
					int participantCount = myLessonRepository.countByLessonGisuId(gisu.getId());
					return AdminLessonListResponseDTO.builder()
						.id(lesson.getId())
						.lessonGisuId(gisu.getId())
						.lessonName(lesson.getLessonName())
						.instructor(lesson.getInstructor())
						.lessonImg(lesson.getLessonImg())
						.duration(gisu.getDuration())
						.participants(participantCount)
						.capacity(gisu.getCapacity())
						.lessonFee(gisu.getLessonFee())
						.build();
				}))
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
				.lessonRoom(lessonGisu.getLessonRoom().getName())
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


	@Override
	public List<LessonMemberResponseDTO> findAllByLessonGisuId(long lessonGisuId) {
		LessonGisu lessonGisu = lessonGisuRepository.findById(lessonGisuId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LESSONGISU_NOT_FOUND));
		
		if (!isActiveGisu(lessonGisu)) {
			return List.of();
		}
		
		return myLessonRepository.findAllByLessonGisuId(lessonGisuId).stream()
			.map(MyLesson::getMember)               // 바로 member 추출
			.map(m -> new LessonMemberResponseDTO(
				m.getName(),
				String.format("%s %s", m.getBranch().getLocation().getName(), m.getBranch().getName()), // null-safe
				m.getPhoneNumber(),
				m.getEmail(),
				m.getBirth()
			))
			.toList();
	}

	@Override
	@Transactional
	public LessonDetailResponseDTO updateLessonDetail(Long lessonId, UpdateLessonDetailRequestDTO requestDTO) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LESSON_NOT_FOUND));

		lesson.setLessonName(requestDTO.getLessonName());
		lesson.setInstructor(requestDTO.getInstructor());
		lesson.setInstruction(requestDTO.getInstruction());
		lesson.setDescription(requestDTO.getDescription());
		lesson.setCategory(requestDTO.getCategory());
		lesson.setLessonImg(requestDTO.getLessonImg());

		lessonRepository.save(lesson);

		for (UpdateLessonDetailRequestDTO.UpdateLessonGisuDTO gisuDTO : requestDTO.getLessonGisus()) {
			LessonGisu lessonGisu = lessonGisuRepository.findById(gisuDTO.getId())
				.orElseThrow(() -> new GeneralException(ErrorStatus.LESSONGISU_NOT_FOUND));

			lessonGisu.setCapacity(gisuDTO.getCapacity());
			lessonGisu.setLessonFee(gisuDTO.getLessonFee());
			lessonGisu.setDuration(gisuDTO.getDuration());
			lessonGisu.setLessonState(gisuDTO.getLessonState());

			lessonGisuRepository.save(lessonGisu);

			for (UpdateLessonDetailRequestDTO.UpdateCurriculumDTO curriculumDTO : gisuDTO.getCurriculums()) {
				Curriculum curriculum = curriculumRepository.findById(curriculumDTO.getId())
					.orElseThrow(() -> new GeneralException(ErrorStatus.CURRICULUM_NOT_FOUND));

				curriculum.setContent(curriculumDTO.getContent());
				curriculumRepository.save(curriculum);
			}
		}

		return getLessonDetail(lessonId);
	}

	@Override
	public List<AdminManageLessonResponseDTO> getManageLessons() {
		List<Lesson> lessons = lessonRepository.findAll();

		return lessons.stream()
			.flatMap(lesson -> lesson.getLessonGisus().stream()
				.map(gisu -> AdminManageLessonResponseDTO.builder()
					.id(gisu.getId())
					.lessonName(lesson.getLessonName())
					.instructor(lesson.getInstructor())
					.description(lesson.getDescription())
					.duration(gisu.getDuration())
					.state(gisu.getLessonState())
					.build()))
			.sorted(Comparator.comparing(AdminManageLessonResponseDTO::getId).reversed())
			.toList();
	}

	@Override
	public LessonGisuDetailResponseDTO getLessonGisuDetail(Long lessonGisuId) {
		LessonGisu lessonGisu = lessonGisuRepository.findById(lessonGisuId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LESSONGISU_NOT_FOUND));

		Lesson lesson = lessonGisu.getLesson();
		
		List<CurriculumResponseDTO> curriculums = lessonGisu.getCurriculums().stream()
			.map(curriculum -> CurriculumResponseDTO.builder()
				.id(curriculum.getId())
				.content(curriculum.getContent())
				.build())
			.toList();

		return LessonGisuDetailResponseDTO.builder()
			.lessonName(lesson.getLessonName())
			.instructor(lesson.getInstructor())
			.instruction(lesson.getInstruction())
			.description(lesson.getDescription())
			.category(lesson.getCategory().name())
			.lessonImg(lesson.getLessonImg())
			.capacity(lessonGisu.getCapacity())
			.lessonFee(lessonGisu.getLessonFee())
			.duration(lessonGisu.getDuration())
			.lessonState(lessonGisu.getLessonState())
			.curriculums(curriculums)
			.build();
	}

	@Override
	@Transactional
	public LessonGisuDetailResponseDTO updateLessonGisu(Long lessonGisuId, UpdateLessonGisuRequestDTO requestDTO) {
		LessonGisu lessonGisu = lessonGisuRepository.findById(lessonGisuId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LESSONGISU_NOT_FOUND));

		Lesson lesson = lessonGisu.getLesson();

		// Lesson 정보 업데이트
		lesson.setLessonName(requestDTO.getLessonName());
		lesson.setInstructor(requestDTO.getInstructor());
		lesson.setInstruction(requestDTO.getInstruction());
		lesson.setDescription(requestDTO.getDescription());
		lesson.setCategory(requestDTO.getCategory());
		lesson.setLessonImg(requestDTO.getLessonImg());
		
		// LessonGisu 정보 업데이트
		lessonGisu.setCapacity(requestDTO.getCapacity());
		lessonGisu.setLessonFee(requestDTO.getLessonFee());
		lessonGisu.setDuration(requestDTO.getDuration());
		lessonGisu.setLessonState(requestDTO.getLessonState());

		// 커리큘럼 업데이트
		if (requestDTO.getCurriculums() != null) {
			for (int i = 0; i < requestDTO.getCurriculums().size(); i++) {
				UpdateLessonGisuRequestDTO.UpdateCurriculumDTO curriculumDTO = requestDTO.getCurriculums().get(i);
				
				if (curriculumDTO.getId() != null && curriculumDTO.getId() > 0) {
					Curriculum curriculum = curriculumRepository.findById(curriculumDTO.getId())
						.orElseThrow(() -> new GeneralException(ErrorStatus.CURRICULUM_NOT_FOUND));
					
					curriculum.setContent(curriculumDTO.getContent());
					curriculumRepository.save(curriculum);
				} else {
					
					Curriculum newCurriculum = Curriculum.builder()
						.content(curriculumDTO.getContent())
						.lessonGisu(lessonGisu)
						.startedAt(LocalDateTime.now())
						.endedAt(LocalDateTime.now())
						.build();

					curriculumRepository.save(newCurriculum);
				}
			}
		}

		lessonRepository.save(lesson);
		lessonGisuRepository.save(lessonGisu);
		return getLessonGisuDetail(lessonGisuId);
	}
	
	private boolean isActiveGisu(LessonGisu gisu) {
		LocalDate endDate = getEndDate(gisu.getDuration());
		if (endDate == null) return false;
		
		LocalDate today = LocalDate.now();
		return !today.isAfter(endDate);
	}

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
}
