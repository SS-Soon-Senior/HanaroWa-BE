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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@Tag(name = "[사용자] 인증", description = "회원가입 및 로그인 관련 API")
public class JwtRefreshController {
	private final MemberService memberService;

	public JwtRefreshController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/reissue")
	@Operation(summary = "토큰 재발급")
	public ResponseEntity<ApiResponse<Void>> refresh(
		@CookieValue(value = "refreshToken", required = false) String refreshToken,
		HttpServletResponse response
	) {
		if (refreshToken == null) {
			throw new GeneralException(ErrorStatus.TOKEN_NOT_FOUND);
		}
		Map<String, Object> refreshClaim = JwtUtil.validateToken(refreshToken);
		String email = (String) refreshClaim.get("email");
		Member member = memberService.getMemberByEmail(email);

		Map<String, Object> claims = Map.of("email", email, "role", member.getRole().name());

		String newAccessToken = JwtUtil.generateToken(claims, 30);
		String newRefreshToken = JwtUtil.generateToken(claims, 7 * 24 * 60);
		ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
			.httpOnly(true)
			.secure(false)
			.path("/")
			.maxAge(Duration.ofMinutes(30))
			.sameSite("Lax")
			.build();
		response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", newRefreshToken)
			.httpOnly(true)
			.secure(false)
			.path("/")
			.maxAge(Duration.ofDays(7))
			.sameSite("Lax")
			.build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}
}
