package com.ss.hanarowa.domain.lesson.dto.request;

import java.util.List;

import com.ss.hanarowa.domain.lesson.entity.Category;
import com.ss.hanarowa.domain.lesson.entity.LessonState;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
	@NotBlank(message = "강좌명은 필수입니다")
	@Size(max = 30, message = "강좌명은 30자를 초과할 수 없습니다")
	private String lessonName;
	
	@NotBlank(message = "강사명은 필수입니다")
	@Size(max = 15, message = "강사명은 15자를 초과할 수 없습니다")
	private String instructor;
	
	@NotBlank(message = "강좌 설명은 필수입니다")
	private String instruction;
	
	@NotBlank(message = "상세 설명은 필수입니다")
	private String description;
	
	@NotNull(message = "카테고리는 필수입니다")
	private Category category;
	
	private String lessonImg;
	
	// LessonGisu 정보들
	@Valid
	private List<UpdateLessonGisuDTO> lessonGisus;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class UpdateLessonGisuDTO {
		@NotNull(message = "기수 ID는 필수입니다")
		private Long id;
		
		@NotNull(message = "정원은 필수입니다")
		@Positive(message = "정원은 양수여야 합니다")
		private Integer capacity;
		
		@NotNull(message = "수강료는 필수입니다")
		@Positive(message = "수강료는 양수여야 합니다")
		private Integer lessonFee;
		
		@NotBlank(message = "기간은 필수입니다")
		@Size(max = 50, message = "기간은 50자를 초과할 수 없습니다")
		private String duration;
		
		@NotNull(message = "강좌 상태는 필수입니다")
		private LessonState lessonState;
		
		@Valid
		private List<UpdateCurriculumDTO> curriculums;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class UpdateCurriculumDTO {
		@NotNull(message = "커리큘럼 ID는 필수입니다")
		private Long id;
		
		@NotBlank(message = "커리큘럼 내용은 필수입니다")
		@Size(max = 100, message = "커리큘럼 내용은 100자를 초과할 수 없습니다")
		private String content;
	}
}