package com.ss.hanarowa.domain.ai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "AI 추천 직업 상세 정보")
public class JobRecommendation {

	@Schema(description = "추천된 직업명")
	private String jobName;

	@Schema(description = "직업에 대한 설명")
	private String description;

	@Schema(description = "해당 직업이 시니어에게 적합한 이유")
	private String reason;
}