package com.ss.hanarowa.domain.lesson.service;

import java.util.List;

import com.ss.hanarowa.domain.lesson.dto.response.LessonListByBranchIdResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListSearchResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.request.AppliedLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.request.OfferedLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMoreDetailResponseDTO;

public interface LessonService {
	List<LessonListResponseDTO> getAllAppliedLessons(Long memberId, AppliedLessonRequestDTO req); // 신청 강좌

	List<LessonListResponseDTO> getAllOfferedLessons(Long memberId, OfferedLessonRequestDTO req); //개설 강좌

	LessonMoreDetailResponseDTO getLessonMoreDetail(Long lessonId);

	LessonListByBranchIdResponseDTO getLessonListByBranchId(Long branchId);

	List<LessonListSearchResponseDTO> getLessonListSearch(String query);

	void enrollForLesson(Long lessonGisuId, String email); // 강좌 신청
}