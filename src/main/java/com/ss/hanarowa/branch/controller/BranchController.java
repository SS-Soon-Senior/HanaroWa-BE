package com.ss.hanarowa.branch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.branch.service.BranchService;
import com.ss.hanarowa.member.dto.MemberRegistDTO;

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
	@GetMapping("/")
	@Tag(name = "지점 목록 조회")
	@Operation(summary = "지점 목록 조회 API", description = "하나로와에서 이용할 수 있는 지점을 조회합니다.")
	public ResponseEntity<?> getBranchs(@Valid @RequestBody MemberRegistDTO memberRegistDTO) {

	}






}
