package com.ss.hanarowa.domain.lesson.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.hanarowa.domain.lesson.dto.request.LessonGisuStateUpdateRequestDto;
import com.ss.hanarowa.domain.lesson.dto.response.AdminLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuStateUpdateResponseDto;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonState;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.service.AdminService;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
	private final LessonRepository lessonRepository;
	private final LessonGisuRepository lessonGisuRepository;

	@Override
	public List<AdminLessonListResponseDTO> getAllLessons() {
		return lessonRepository.findAll().stream()
			.map(l -> new AdminLessonListResponseDTO(
				l.getLessonName(),
				l.getInstructor(),
				l.getInstruction(),
				l.getLessonImg()
			))
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
}