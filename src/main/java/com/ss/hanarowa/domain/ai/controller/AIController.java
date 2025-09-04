package com.ss.hanarowa.domain.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.ai.dto.request.CourseRecRequestDto;
import com.ss.hanarowa.domain.ai.dto.request.JobRecRequestDto;
import com.ss.hanarowa.domain.ai.dto.response.RecResponseDto;
import com.ss.hanarowa.domain.ai.service.AIRecService;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@Tag(name = "AI 추천", description = "AI 기반 강좌 및 직업 추천 API")
public class AIController {

	private final AIRecService aiRecService;

	@Operation(summary = "AI 강좌 추천 받기")
	@PostMapping("/recommend-courses")
	public ResponseEntity<ApiResponse<RecResponseDto>> getCourseRecommendations(
		@RequestBody CourseRecRequestDto request) {

		RecResponseDto result = aiRecService.recommendCourses(request.getInterest());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	@Operation(summary = "AI 직업 추천 받기")
	@PostMapping("/recommend-jobs")
	public ResponseEntity<ApiResponse<RecResponseDto>> getJobRecommendations(
		@RequestBody JobRecRequestDto request) {

		RecResponseDto result = aiRecService.recommendJobs(request.getExperience());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}
