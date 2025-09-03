package com.ss.hanarowa.domain.lesson.dto.response;

import java.util.List;

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
public class LessonGisuResponseDTO {
	private Long id;
	private int capacity;
	private int lessonFee;
	private String duration;
	private LessonState lessonState;
	private String lessonRoom;
	private int currentEnrollment;
	private List<CurriculumResponseDTO> curriculums;
}