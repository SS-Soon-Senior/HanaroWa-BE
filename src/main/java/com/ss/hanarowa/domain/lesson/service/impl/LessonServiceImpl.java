package com.ss.hanarowa.domain.lesson.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ss.hanarowa.domain.lesson.dto.request.AppliedLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.AppliedLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.lesson.entity.LessonGisu;
import com.ss.hanarowa.domain.lesson.entity.LessonRoom;
import com.ss.hanarowa.domain.lesson.repository.LessonGisuRepository;
import com.ss.hanarowa.domain.lesson.repository.LessonRepository;
import com.ss.hanarowa.domain.lesson.service.LessonService;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.MyLesson;
import com.ss.hanarowa.domain.member.repository.MyLessonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
	private final LessonRepository lessonRepository;
	private final MyLessonRepository myLessonRepository;

	@Override
	public List<LessonListResponseDTO> getAllAppliedLessons(Long memberId, AppliedLessonRequestDTO req){
		List<MyLesson> myLessons = myLessonRepository.findAllByMemberId(memberId);

		return myLessons.stream().map(myLesson -> {
			LessonGisu gisu = myLesson.getLessonGisu();
			Lesson lesson = gisu.getLesson();
			LessonRoom room = gisu.getLessonRoom();

			return LessonListResponseDTO.builder().
		})
	}
}
