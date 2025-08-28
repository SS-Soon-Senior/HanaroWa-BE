package com.ss.hanarowa.domain.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.ai.dto.request.CourseRecRequestDto;
import com.ss.hanarowa.domain.ai.dto.request.JobRecRequestDto;
import com.ss.hanarowa.domain.ai.dto.response.CourseRecResponseDto;
import com.ss.hanarowa.domain.ai.dto.response.JobRecResponseDto;
import com.ss.hanarowa.domain.ai.service.AIRecService;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

	private final AIRecService aiRecService;

	@Operation(summary = "AI 강좌 추천 받기")
	@PostMapping("/recommend-courses")
	public ResponseEntity<ApiResponse<CourseRecResponseDto>> getCourseRecommendations(
		@RequestBody CourseRecRequestDto request) {

		CourseRecResponseDto result = aiRecService.recommendCourses(request.getInterest());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	@Operation(summary = "AI 직업 추천 받기")
	@PostMapping("/recommend-jobs")
	public ResponseEntity<ApiResponse<JobRecResponseDto>> getJobRecommendations(
		@RequestBody JobRecRequestDto request) {

		JobRecResponseDto result = aiRecService.recommendJobs(request.getExperience());
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}
}