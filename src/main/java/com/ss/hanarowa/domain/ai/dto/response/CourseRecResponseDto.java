package com.ss.hanarowa.domain.ai.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ss.hanarowa.domain.ai.dto.CourseRecommendation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "AI 강좌 추천 응답 DTO")
public class CourseRecResponseDto {

	@Schema(description = "AI가 추천한 강좌 목록")
	private List<CourseRecommendation> recommendations;

	// Gemini API의 JSON 응답을 파싱하기 위해 모든 필드를 받는 생성자가 필요할 수 있습니다.
	public CourseRecResponseDto(List<CourseRecommendation> recommendations) {
		this.recommendations = recommendations;
	}
}