package com.ss.hanarowa.domain.ai.dto.request;

import java.util.List;

import lombok.Getter;

// Gemini API에 보낼 요청 DTO
@Getter
public class GeminiRequestDto {
	private final List<Content> contents;

	public GeminiRequestDto(String prompt) {
		this.contents = List.of(new Content(List.of(new Part(prompt))));
	}

	@Getter
	private static class Content {
		private final List<Part> parts;

		public Content(List<Part> parts) {
			this.parts = parts;
		}
	}

	@Getter
	private static class Part {
		private final String text;

		public Part(String text) {
			this.text = text;
		}
	}
}