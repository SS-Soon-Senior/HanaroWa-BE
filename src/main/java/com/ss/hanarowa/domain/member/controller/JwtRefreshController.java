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

@RestController
@RequestMapping("/auth")
public class JwtRefreshController {

	private final MemberService memberService;

	public JwtRefreshController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/reissue")
	@Tag(name = "RefreshToken", description = "리프레시 토큰 재발급")
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

		// 2. 새로운 AccessToken과 RefreshToken을 모두 생성 (Refresh Token Rotation)
		String newAccessToken = JwtUtil.generateToken(claims, 30);
		String newRefreshToken = JwtUtil.generateToken(claims, 7 * 24 * 60);

		// 3. 새 AccessToken 쿠키 설정
		ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
			.httpOnly(true)
			.secure(false) // 프로덕션에서는 true로 변경
			.path("/")
			.maxAge(Duration.ofMinutes(1))
			.sameSite("Lax")
			.build();
		response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

		// 4. 새 RefreshToken 쿠키 설정
		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", newRefreshToken)
			.httpOnly(true)
			.secure(false) // 프로덕션에서는 true로 변경
			.path("/")
			.maxAge(Duration.ofDays(7))
			.sameSite("Strict")
			.build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

		// 5. 본문 없이 성공 응답만 반환
		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}
}
