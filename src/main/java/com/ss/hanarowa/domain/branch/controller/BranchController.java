package com.ss.hanarowa.domain.branch.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.branch.dto.response.BranchResponseDTO;
import com.ss.hanarowa.domain.branch.service.BranchService;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
@Tag(name = "지점", description = "지점 관련 API")
public class BranchController {

	private final BranchService branchService;

	/**
	 * 전체 지점 목록 조회
	 */
	@GetMapping("")
	@Operation(summary = "지점 목록 조회 API", description = "하나로와에서 이용할 수 있는 지점을 조회합니다.")
	public ResponseEntity<ApiResponse<List<BranchResponseDTO>>> getBranchs() {
		List<BranchResponseDTO> branchList = branchService.getAllBranches();
		return ResponseEntity.ok(ApiResponse.onSuccess(branchList));
	}
}
