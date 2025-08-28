package com.ss.hanarowa.domain.lesson.dto.request;

import java.util.List;

import com.ss.hanarowa.domain.lesson.entity.Category;
import com.ss.hanarowa.domain.lesson.entity.LessonState;

import jakarta.validation.Valid;
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
public class UpdateLessonDetailRequestDTO {
	
	// Lesson 정보
	private String lessonName;
	private String instructor;
	private String instruction;
	private String description;
	private Category category;
	private String lessonImg;
	private Long branchId;
	
	// LessonGisu 정보들
	@Valid
	private List<UpdateLessonGisuDTO> lessonGisus;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class UpdateLessonGisuDTO {
		private Long id;
		private Integer capacity;
		private Integer lessonFee;
		private String duration;
		private LessonState lessonState;
		private Long lessonRoomId;
		
		@Valid
		private List<UpdateCurriculumDTO> curriculums;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class UpdateCurriculumDTO {
		private Long id;
		private String content;
	}
}