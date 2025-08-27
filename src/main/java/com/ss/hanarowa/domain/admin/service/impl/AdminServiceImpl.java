package com.ss.hanarowa.domain.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.admin.dto.response.AdminLessonListResponseDTO;
import com.ss.hanarowa.domain.admin.service.AdminService;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	private final LessonRepository lessonRepository;

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
}