package com.ss.hanarowa.admin.service;

import java.util.List;

import com.ss.hanarowa.admin.dto.response.AdminLessonListResponseDTO;

public interface AdminService {

	List<AdminLessonListResponseDTO> getAllLessons();
}
