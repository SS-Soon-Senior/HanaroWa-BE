package com.ss.hanarowa.domain.lesson.service;

import java.util.List;

import com.ss.hanarowa.domain.lesson.dto.response.LessonListByBranchIdResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListSearchResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMoreDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.entity.Lesson;
import com.ss.hanarowa.domain.member.entity.MyLesson;

public interface LessonService {
	List<MyLesson> getAllAppliedLessons(); // 신청 강좌

	List<Lesson> getAllOfferedLessons(); //개설 강좌

	LessonMoreDetailResponseDTO getLessonMoreDetail(Long lessonId);
	
	LessonListByBranchIdResponseDTO getLessonListByBranchId(Long branchId);
	
	List<LessonListSearchResponseDTO> getLessonListSearch(String query);
}
