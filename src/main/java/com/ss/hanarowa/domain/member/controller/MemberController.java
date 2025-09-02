package com.ss.hanarowa.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.member.dto.request.MemberRegistRequestDTO;
import com.ss.hanarowa.domain.member.dto.request.ModifyPasswdRequestDTO;
import com.ss.hanarowa.domain.member.dto.request.MemberInfoRequestDTO;
import com.ss.hanarowa.domain.member.dto.response.MemberInfoResponseDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.response.ApiResponse;

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

	@GetMapping
	@Operation(summary = "멤버 정보 반환")
	public ResponseEntity<ApiResponse<MemberInfoResponseDTO>> getInfo (Authentication authentication) {
		String email =  authentication.getName();
		Member m = memberService.getMemberByEmail(email);
		MemberInfoResponseDTO dto = MemberInfoResponseDTO.builder().
			name(m.getName()).birth(m.getBirth()).phone(m.getPhoneNumber()).build();
		return ResponseEntity.ok(ApiResponse.onSuccess(dto));
	}

	@PostMapping("/regist")
	@Operation(summary = "일반 회원가입")
	public ResponseEntity<ApiResponse<String>> regist(@Valid @RequestBody MemberRegistRequestDTO memberRegistRequestDTO) {
		memberService.credentialRegist(memberRegistRequestDTO);
		return ResponseEntity.ok(ApiResponse.onSuccess("회원가입 완료"));
	}


	@PostMapping("/info")
	@Operation(summary = "전화번호, 생일등록(회원가입 중)")
	public ResponseEntity<ApiResponse<Void>> info(@Valid @RequestBody MemberInfoRequestDTO memberInfoRequestDTO, Authentication authentication) {
		String email = authentication.getName();

		memberService.infoRegist(memberInfoRequestDTO, email);

		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

	@PatchMapping("/withdraw")
	@Operation(summary = "회원탈퇴")
	public void withDraw(Authentication authentication) {
		String email = authentication.getName();

		memberService.withdraw(email);
	}

	@PatchMapping
	@Operation(summary = "회원 정보 수정(회원가입 후)")
	public ResponseEntity<ApiResponse<MemberInfoRequestDTO>> modifyInfo(@Valid @RequestBody MemberInfoRequestDTO memberInfoRequestDTO, Authentication authentication) {
		String email = authentication.getName();

		memberService.modifyInfo(memberInfoRequestDTO, email);

		return ResponseEntity.ok(ApiResponse.onSuccess(memberInfoRequestDTO));
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
