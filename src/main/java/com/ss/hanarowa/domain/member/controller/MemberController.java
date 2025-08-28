package com.ss.hanarowa.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.member.dto.MemberRegistDTO;
import com.ss.hanarowa.domain.member.dto.ModifyPasswdRequestDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.domain.member.dto.MemberInfoDTO;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 관련 API")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/regist")
	@Operation(summary = "일반 회원가입")
	public ResponseEntity<ApiResponse<String>> regist(@Valid @RequestBody MemberRegistDTO memberRegistDTO) {
		memberService.credentialRegist(memberRegistDTO);
		return ResponseEntity.ok(ApiResponse.onSuccess("회원가입 완료"));
	}

	@PostMapping("/info")
	@Operation(summary = "전화번호, 생일등록")
	public ResponseEntity<ApiResponse<Void>> info(@Valid @RequestBody MemberInfoDTO memberInfoDTO, Authentication authentication) {
		String email = authentication.getName();

		memberService.infoRegist(memberInfoDTO, email);

		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

	@PatchMapping("/withdraw")
	@Operation(summary = "회원탈퇴")
	public void withDraw(Authentication authentication) {
		String email = authentication.getName();

		memberService.withdraw(email);
	}

	@PatchMapping
	@Operation(summary = "회원 정보 수정")
	public ResponseEntity<ApiResponse<MemberInfoDTO>> modifyInfo(@Valid @RequestBody MemberInfoDTO memberInfoDTO, Authentication authentication) {
		String email = authentication.getName();

		memberService.modifyInfo(memberInfoDTO, email);

		return ResponseEntity.ok(ApiResponse.onSuccess(memberInfoDTO));
	}

	@PatchMapping("/password")
	@Operation(summary = "비밀번호 수정")
	public ResponseEntity<ApiResponse<String>> modifyPassword(@Valid @RequestBody ModifyPasswdRequestDTO modifyDTO, Authentication authentication) {

		String email = authentication.getName();


		memberService.modifyPassword(modifyDTO, email);
		return ResponseEntity.ok(ApiResponse.onSuccess("비밀번호 수정 완료"));
	}
	/**
	 * 지점 선택/수정
	 */
	@PostMapping("/branch/{branchId}")
	@Operation(summary = "지점 선택하기/수정 API", description = "하나로와 내 지점을 선택/수정합니다.")
	@Tag(name = "지점", description = "지점 관련 API")
	public ResponseEntity<ApiResponse<Void>> updateBranch(
		@PathVariable Long branchId,
		Authentication authentication) {

		String email = authentication.getName();

		memberService.updateMemberBranch(branchId, email);

		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

}
