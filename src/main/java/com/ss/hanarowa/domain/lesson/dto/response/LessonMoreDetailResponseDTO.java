package com.ss.hanarowa.domain.lesson.dto.response;

import java.util.List;

import com.ss.hanarowa.domain.lesson.entity.Category;
import com.ss.hanarowa.domain.lesson.entity.Review;

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
public class LessonMoreDetailResponseDTO {
	private String lessonName;
	private String instructor;
	private String instruction;
	private String description;
	private Category category;
	private String lessonImg;
	private List<Review> reviews;
	private double averageRating;
	private int totalReviews;

	private List<LessonGisuResponseDTO> lessonGisus;
}
