package com.ss.hanarowa.domain.lesson.service;

import java.util.List;

import com.ss.hanarowa.domain.lesson.dto.response.AdminLessonListResponseDTO;

public interface AdminService {

	List<AdminLessonListResponseDTO> getAllLessons();
}
