package com.ss.hanarowa.domain.lesson.dto.request;

import java.util.List;

import com.ss.hanarowa.domain.lesson.dto.response.LessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.MyOpenLessonListResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyReservationPageResponseDTO {
	private List<MyOpenLessonListResponseDTO>  myOpenLessonList;

	private List<LessonListResponseDTO>  lessonList;
}
