package com.ss.hanarowa.domain.admin.service;

import java.util.List;

import com.ss.hanarowa.domain.admin.dto.response.AdminLessonListResponseDTO;

public interface AdminService {

	List<AdminLessonListResponseDTO> getAllLessons();
}