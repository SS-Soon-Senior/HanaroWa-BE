package com.ss.hanarowa.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.member.dto.MemberAuthDTO;
import com.ss.hanarowa.member.dto.MemberInfoDTO;
import com.ss.hanarowa.member.dto.MemberRegistDTO;
import com.ss.hanarowa.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/regist")
	@Tag(name = "회원가입")
	@Operation(summary = "일반 회원가입")
	public ResponseEntity<?> regist(@Valid @RequestBody MemberRegistDTO memberRegistDTO) {
		memberService.credentialRegist(memberRegistDTO);
		return ResponseEntity.ok("회원가입 성공");
	}

	@PostMapping("/info")
	@Operation(summary = "전화번호, 생일등록")
	@Tag(name = "추가정보등록")
	public  ResponseEntity<?> info(@Valid @RequestBody MemberInfoDTO memberInfoDTO, Authentication authentication) {
		MemberAuthDTO member = (MemberAuthDTO)authentication.getPrincipal();
		Long id = member.getId();

		memberService.infoRegist(memberInfoDTO, id);

		return ResponseEntity.ok("추가정보 등록 성공");
	}

}
