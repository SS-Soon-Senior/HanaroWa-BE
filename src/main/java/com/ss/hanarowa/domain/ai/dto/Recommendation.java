package com.ss.hanarowa.domain.ai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "AI 추천 상세 정보")
public class Recommendation {

	@Schema(description = "추천 타이틀")
	private String name;

	@Schema(description = "해당되는 설명")
	private String description;

	@Schema(description = "추천하는 이유")
	private String reason;
}