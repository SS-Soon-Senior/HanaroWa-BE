package com.ss.hanarowa.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.member.dto.response.MemberListResponseDTO;
import com.ss.hanarowa.domain.member.service.AdminMemberService;
import com.ss.hanarowa.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "[관리자] 회원", description = "관리자 관련 API")
public class AdminMemberController {
	private final AdminMemberService adminMemberService;
	@GetMapping("/members")
	@Operation(summary ="관리자 회원 목록")
	public ResponseEntity<ApiResponse<List<MemberListResponseDTO>>> getAdminAllMembers() {
		List<MemberListResponseDTO> result = adminMemberService.getAllMembers();
		return ResponseEntity.ok(ApiResponse.onSuccess(result));
	}

}
