package com.ss.hanarowa.domain.ai.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ss.hanarowa.domain.ai.dto.Recommendation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "AI 추천 응답 DTO")
public class RecResponseDto {

	@Schema(description = "AI가 추천한 강좌 목록")
	private List<Recommendation> recommendations;

	public RecResponseDto(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}
}