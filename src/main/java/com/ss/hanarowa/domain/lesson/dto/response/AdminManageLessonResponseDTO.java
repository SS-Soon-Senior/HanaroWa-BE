package com.ss.hanarowa.domain.lesson.dto.response;

import com.ss.hanarowa.domain.lesson.entity.LessonState;
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
public class AdminManageLessonResponseDTO {
	Long id;
	String lessonName;
	String instructor;
	String description;
	String duration;
	LessonState state;
}