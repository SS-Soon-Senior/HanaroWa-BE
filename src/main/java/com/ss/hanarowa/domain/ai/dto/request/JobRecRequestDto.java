package com.ss.hanarowa.domain.ai.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "AI 직업 추천 요청 DTO")
public class JobRecRequestDto {

	@Schema(description = "사용자의 이전 경험 또는 경력", example = "사무직으로 20년 근무")
	private String experience;
}