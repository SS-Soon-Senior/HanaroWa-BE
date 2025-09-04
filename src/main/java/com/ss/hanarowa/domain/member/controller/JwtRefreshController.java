package com.ss.hanarowa.domain.member.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.security.JwtUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class JwtRefreshController {

	private final MemberService memberService;

	public JwtRefreshController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/reissue")
	@Tag(name = "RefreshToken", description = "리프레시 토큰 재발급")
	public ResponseEntity<ApiResponse<Map<String, Object>>> refresh(
		@CookieValue(value = "refreshToken", required = false) String refreshToken,
		HttpServletResponse response
	) {
		if (refreshToken == null) {
			throw new GeneralException(ErrorStatus.TOKEN_NOT_FOUND);
		}

		Map<String, Object> refreshClaim = JwtUtil.validateToken(refreshToken);
		String email = (String) refreshClaim.get("email");
		Member member = memberService.getMemberByEmail(email);

		// 새 AccessToken
		Map<String, Object> claims = Map.of("email", email, "role", member.getRole().name());
		String newAccessToken = JwtUtil.generateToken(claims, 1);

		// 새 쿠키 세팅
		ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
													.httpOnly(true)
													.secure(false)
													.path("/")
													.maxAge(Duration.ofMinutes(30))
													.sameSite("Lax")
													.build();
		response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

		return ResponseEntity.ok(ApiResponse.onSuccess(Map.of("accessToken", newAccessToken)));
	}
}


