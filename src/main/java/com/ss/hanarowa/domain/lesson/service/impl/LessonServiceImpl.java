package com.ss.hanarowa.domain.lesson.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.lesson.dto.request.AppliedLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.AppliedLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.member.repository.MyLessonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl {
	private final LessonRepository lessonRepository;
	private final MyLessonRepository myLessonRepository;

	@Override
	public List<LessonListResponseDTO> getAllAppliedLessons(Long memberId, AppliedLessonRequestDTO req){

	}
}
