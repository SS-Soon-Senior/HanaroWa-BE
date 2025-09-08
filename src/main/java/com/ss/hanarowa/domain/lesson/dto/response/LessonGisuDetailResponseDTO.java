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
public class LessonGisuDetailResponseDTO {
	private String lessonName;
	private String instructor;
	private String instruction;
	private String description;
	private String category;
	private String lessonImg;
	private Integer capacity;
	private Integer lessonFee;
	private String duration;
	private LessonState lessonState;
	private List<CurriculumResponseDTO> curriculums;
}