package com.ss.hanarowa.domain.lesson.service.impl;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.lesson.repository.LessonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl {
	private final LessonRepository lessonRepository;


}
