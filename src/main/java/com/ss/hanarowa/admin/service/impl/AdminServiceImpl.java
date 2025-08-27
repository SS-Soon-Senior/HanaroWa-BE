package com.ss.hanarowa.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.admin.dto.AdminLessonListDTO;
import com.ss.hanarowa.admin.service.AdminService;
import com.ss.hanarowa.lesson.repository.LessonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	private final LessonRepository lessonRepository;

	@Override
	public List<AdminLessonListDTO> getAllLessons() {
		return lessonRepository.findAll().stream()
			.map(l -> new AdminLessonListDTO(
				l.getLessonName(),
				l.getInstructor(),
				l.getInstruction(),
				l.getLessonImg()
			))
			.toList();
	}
}
