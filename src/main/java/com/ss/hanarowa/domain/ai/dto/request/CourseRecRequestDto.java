package com.ss.hanarowa.domain.ai.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "AI 강좌 추천 요청 DTO")
public class CourseRecRequestDto {

	@Schema(description = "사용자의 관심 분야", example = "스마트폰 활용")
	private String interest;
}