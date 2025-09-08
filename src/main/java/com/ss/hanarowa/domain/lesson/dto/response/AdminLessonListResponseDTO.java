package com.ss.hanarowa.domain.lesson.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminLessonListResponseDTO {
	Long id;
	String lessonName;
	String instructor;
	String lessonImg;
	String duration;
	int participants;
	int capacity;
	int lessonFee;
}
