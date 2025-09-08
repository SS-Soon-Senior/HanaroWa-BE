package com.ss.hanarowa.domain.ai.client;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ss.hanarowa.domain.ai.dto.request.GeminiRequestDto;
import com.ss.hanarowa.domain.ai.dto.response.GeminiResponseDto;

@Slf4j
@Component // Spring의 Bean으로 등록
public class GeminiApiClient {

	private final WebClient webClient;
	private final String apiKey;
	private final String apiUrl;

	// 생성자를 통해 application.yml의 값을 주입받고 WebClient를 초기화
	public GeminiApiClient(WebClient.Builder webClientBuilder,
		@Value("${gemini.api.key}") String apiKey,
		@Value("${gemini.api.url}") String apiUrl) {
		this.webClient = webClientBuilder.build();
		this.apiKey = apiKey;
		this.apiUrl = apiUrl;
	}

	/**
	 * Gemini API를 호출하여 콘텐츠를 생성하는 메서드
	 * @param prompt 프롬프트 문자열
	 * @return 생성된 콘텐츠 문자열
	 */
	public String generateContent(String prompt) {
		// 1. 요청 DTO 생성
		GeminiRequestDto requestDto = new GeminiRequestDto(prompt);

		// 2. WebClient를 사용하여 API 호출 (비동기)
		GeminiResponseDto responseDto = webClient.post()
			.uri(apiUrl + "?key=" + apiKey) // URI에 API 키 추가
			.contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just(requestDto), GeminiRequestDto.class)
			.retrieve() // 응답을 받아옴
			.bodyToMono(GeminiResponseDto.class) // 응답 본문을 GeminiResponseDto로 변환
			.block(); // 비동기 작업이 완료될 때까지 기다림 (동기식으로 변환)

		// 3. 응답에서 텍스트를 추출하여 반환
		if (responseDto == null || responseDto.getGeneratedText().isEmpty()) {
			throw new RuntimeException("Failed to get a valid response from Gemini API.");
		}

		return responseDto.getGeneratedText();
	}
}
