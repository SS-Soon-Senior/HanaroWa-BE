package com.ss.hanarowa.domain.lesson.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.lesson.dto.request.LessonGisuStateUpdateRequestDto;
import com.ss.hanarowa.domain.lesson.dto.request.UpdateLessonDetailRequestDTO;
import com.ss.hanarowa.domain.lesson.dto.response.AdminLessonListResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonDetailResponseDTO;
import com.ss.hanarowa.domain.lesson.dto.response.LessonGisuStateUpdateResponseDto;
import com.ss.hanarowa.domain.lesson.dto.response.LessonMemberResponseDTO;
import com.ss.hanarowa.domain.lesson.service.AdminLessonService;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name="[관리자] 강좌")
@RestController
@RequestMapping("/admin/lesson")
@RequiredArgsConstructor
public class AdminLessonController {
	private final AdminLessonService adminLessonService;

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary ="강좌 개설 신청 내역")
	@GetMapping
	public ResponseEntity<ApiResponse<List<AdminLessonListResponseDTO>>> getAdminAllLessons(){
		log.debug("[관리자] Controller : 강좌 목록 전체 가져오기");
		System.out.println("이쪽으로와야함");
		List<AdminLessonListResponseDTO> list = adminLessonService.getAllLessons();
		return ResponseEntity.ok(ApiResponse.onSuccess(list));
	}

	/**
	 * 강좌 개설 신청 승인/거절 API
	 * @param lessonGisuId
	 * @param request
	 * @return
	 */

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "강좌 개설 신청 상태 변경 (승인/거절)")
	@PatchMapping("/{lessonGisuId}/state")
	public ResponseEntity<ApiResponse<LessonGisuStateUpdateResponseDto>> updateLessonState(
		@PathVariable Long lessonGisuId,
		@RequestBody LessonGisuStateUpdateRequestDto request) {

		LessonGisuStateUpdateResponseDto result = adminLessonService.updateLessonGisuState(lessonGisuId, request);

		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary="관리자 강좌 상세 보기")
	@GetMapping("/{lessonId}")
	public ResponseEntity<ApiResponse<LessonDetailResponseDTO>> getLessonDetail(@PathVariable Long lessonId){
		return ResponseEntity.ok(ApiResponse.onSuccess(adminLessonService.getLessonDetail(lessonId)));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "관리자 강좌별 신청 회원 현황")
	@GetMapping("/{lessonGisuId}/member")
	public ResponseEntity<ApiResponse<List<LessonMemberResponseDTO>>> getLessonMembers(@PathVariable Long lessonGisuId){
		List<LessonMemberResponseDTO> result = adminLessonService.findAllByLessonGisuId(lessonGisuId);
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}


	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary="관리자 강좌 상세 수정하기")
	@PatchMapping("/{lessonId}")
	public ResponseEntity<ApiResponse<LessonDetailResponseDTO>> updateLessonDetail(@PathVariable Long lessonId, @Valid @RequestBody UpdateLessonDetailRequestDTO requestDTO){
		log.info("[관리자] Controller : 강좌 상세 수정 - lessonId: {}, requestDTO: {}", lessonId, requestDTO);
		return ResponseEntity.ok(ApiResponse.onSuccess(adminLessonService.updateLessonDetail(lessonId,requestDTO)));
	}
}
