package com.ss.hanarowa.domain.lesson.dto.response;

import java.util.List;

import com.ss.hanarowa.domain.lesson.entity.Category;

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
public class LessonDetailResponseDTO {
	private String lessonName;
	private String instructor;
	private String instruction;
	private String description;
	private Category category;
	private String lessonImg;
	
	// LessonGisu 정보 (기간, 상태, 커리큘럼 포함)
	private List<LessonGisuResponseDTO> lessonGisus;
}
