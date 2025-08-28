package com.ss.hanarowa.domain.ai.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ss.hanarowa.domain.ai.dto.JobRecommendation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "AI 직업 추천 응답 DTO")
public class JobRecResponseDto {

	@Schema(description = "AI가 추천한 직업 목록")
	private List<JobRecommendation> recommendations;

	public JobRecResponseDto(List<JobRecommendation> recommendations) {
		this.recommendations = recommendations;
	}
}