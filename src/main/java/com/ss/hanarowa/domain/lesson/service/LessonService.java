package com.ss.hanarowa.domain.lesson.service;

import java.util.List;

import com.ss.hanarowa.domain.lesson.dto.request.CreateLessonRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.request.TimeAvailabilityRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListByBranchIdResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListSearchResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMoreDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.MyOpenLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.TimeAvailabilityResponseDTO;

public interface LessonService {
	List<LessonListResponseDTO> getAllAppliedLessons(String email); // 신청 강좌

	List<MyOpenLessonListResponseDTO> getAllOfferedLessons(String email); //개설 강좌

	LessonMoreDetailResponseDTO getLessonMoreDetail(Long lessonId);

	LessonListByBranchIdResponseDTO getLessonListByBranchId(Long branchId, List<String> categories);

	List<LessonListSearchResponseDTO> getLessonListSearch(String query);

	void applyForLesson(Long lessonGisuId, String email); // 강좌 신청
	
	void createLesson(CreateLessonRequestDTO createLessonRequestDTO, String email,String imageUrl); // 강좌 개설

	TimeAvailabilityResponseDTO checkTimeAvailability(TimeAvailabilityRequestDTO requestDTO); // 시간대 사용 가능 여부 확인

	void deleteLessonReservation(Long lessonGisuId, String email);
}
