package com.ss.hanarowa.domain.lesson.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.lesson.dto.request.LessonGisuStateUpdateRequestDto;
import com.ss.hanarowa.domain.lesson.dto.request.UpdateLessonDetailRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.AdminLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.CurriculumResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuStateUpdateResponseDto;
import com.ss.hanarowa.domain.branch.entity.Branch;
import com.ss.hanarowa.domain.branch.repository.BranchRepository;
import com.ss.hanarowa.domain.lesson.entity.Curriculum;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.entity.LessonState;
import com.ss.hanarowa.domain.lesson.repository.CurriculumRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRoomRepository;
import com.ss.hanarowa.domain.lesson.service.AdminService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
	private final LessonRepository lessonRepository;
	private final LessonGisuRepository lessonGisuRepository;
	private final CurriculumRepository curriculumRepository;
	private final BranchRepository branchRepository;
	private final LessonRoomRepository lessonRoomRepository;

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

	@Override
	@Transactional
	public LessonDetailResponseDTO updateLessonDetail(Long lessonId, UpdateLessonDetailRequestDTO requestDTO) {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LESSON_NOT_FOUND));
		
		Branch branch = null;
		if (requestDTO.getBranchId() != null) {
			branch = branchRepository.findById(requestDTO.getBranchId())
				.orElseThrow(() -> new GeneralException(ErrorStatus.BRANCH_NOT_FOUND));
		}
		
		if (requestDTO.getLessonName() != null) lesson.setLessonName(requestDTO.getLessonName());
		if (requestDTO.getInstructor() != null) lesson.setInstructor(requestDTO.getInstructor());
		if (requestDTO.getInstruction() != null) lesson.setInstruction(requestDTO.getInstruction());
		if (requestDTO.getDescription() != null) lesson.setDescription(requestDTO.getDescription());
		if (requestDTO.getCategory() != null) lesson.setCategory(requestDTO.getCategory());
		if (requestDTO.getLessonImg() != null) lesson.setLessonImg(requestDTO.getLessonImg());
		if (branch != null) lesson.setBranch(branch);
		
		lessonRepository.save(lesson);
		
		if (requestDTO.getLessonGisus() != null) {
			for (UpdateLessonDetailRequestDTO.UpdateLessonGisuDTO gisuDTO : requestDTO.getLessonGisus()) {
				LessonGisu lessonGisu = lessonGisuRepository.findById(gisuDTO.getId())
					.orElseThrow(() -> new GeneralException(ErrorStatus.LESSONGISU_NOT_FOUND));
				
				LessonRoom lessonRoom = null;
				if (gisuDTO.getLessonRoomId() != null) {
					lessonRoom = lessonRoomRepository.findById(gisuDTO.getLessonRoomId())
						.orElseThrow(() -> new GeneralException(ErrorStatus.LESSONROOM_NOT_FOUND));
				}
				
				if (gisuDTO.getCapacity() != null) lessonGisu.setCapacity(gisuDTO.getCapacity());
				if (gisuDTO.getLessonFee() != null) lessonGisu.setLessonFee(gisuDTO.getLessonFee());
				if (gisuDTO.getDuration() != null) lessonGisu.setDuration(gisuDTO.getDuration());
				if (gisuDTO.getLessonState() != null) lessonGisu.setLessonState(gisuDTO.getLessonState());
				if (lessonRoom != null) lessonGisu.setLessonRoom(lessonRoom);
				
				lessonGisuRepository.save(lessonGisu);
				
				if (gisuDTO.getCurriculums() != null) {
					for (UpdateLessonDetailRequestDTO.UpdateCurriculumDTO curriculumDTO : gisuDTO.getCurriculums()) {
						Curriculum curriculum = curriculumRepository.findById(curriculumDTO.getId())
							.orElseThrow(() -> new GeneralException(ErrorStatus.CURRICULUM_NOT_FOUND));
						
						if (curriculumDTO.getContent() != null) {
							curriculum.setContent(curriculumDTO.getContent());
						}
						curriculumRepository.save(curriculum);
					}
				}
			}
		}
		
		return getLessonDetail(lessonId);
	}
}
