package com.ss.hanarowa.domain.ai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "AI 추천 강좌 상세 정보")
public class CourseRecommendation {

	@Schema(description = "추천된 강좌명")
	private String courseName;

	@Schema(description = "해당 강좌를 추천하는 이유")
	private String reason;
}