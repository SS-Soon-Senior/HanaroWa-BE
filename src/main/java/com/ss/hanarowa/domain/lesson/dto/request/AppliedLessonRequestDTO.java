package com.ss.hanarowa.domain.lesson.dto.request;

import com.ss.hanarowa.domain.lesson.entity.LessonState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppliedLessonRequestDTO {
	private LessonState lessonState;
}
