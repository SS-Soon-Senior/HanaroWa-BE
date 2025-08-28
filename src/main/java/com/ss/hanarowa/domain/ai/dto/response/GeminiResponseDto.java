package com.ss.hanarowa.domain.ai.dto.response;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;

// Gemini API로부터 받을 응답 DTO
@Getter
@NoArgsConstructor
public class GeminiResponseDto {
	private List<Candidate> candidates;

	public String getGeneratedText() {
		return Optional.ofNullable(candidates)
			.flatMap(c -> c.stream().findFirst())
			.map(Candidate::getContent)
			.map(Content::getParts)
			.flatMap(p -> p.stream().findFirst())
			.map(Part::getText)
			.orElse(""); // 응답이 비었을 경우 빈 문자열 반환
	}

	@Getter
	@NoArgsConstructor
	private static class Candidate {
		private Content content;
	}

	@Getter
	@NoArgsConstructor
	private static class Content {
		private List<Part> parts;
	}

	@Getter
	@NoArgsConstructor
	private static class Part {
		private String text;
	}
}