package com.ss.hanarowa.domain.lesson.dto.response;

import java.util.List;

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
