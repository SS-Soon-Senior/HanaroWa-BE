package com.ss.hanarowa.domain.lesson.service;

import java.util.List;

import com.ss.hanarowa.domain.lesson.dto.request.LessonGisuStateUpdateRequestDto;
import com.ss.hanarowa.domain.lesson.dto.response.AdminLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuStateUpdateResponseDto;
import com.ss.hanarowa.domain.lesson.entity.LessonState;

public interface AdminService {

	List<AdminLessonListResponseDTO> getAllLessons();

	LessonGisuStateUpdateResponseDto updateLessonGisuState(long lessonGisuId, LessonGisuStateUpdateRequestDto request);
}