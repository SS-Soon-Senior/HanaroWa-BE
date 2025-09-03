package com.ss.hanarowa.domain.member.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.security.JwtUtil;
import com.ss.hanarowa.global.security.exception.CustomJwtException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class JwtRefreshController {

	@PostMapping("/reissue")
	@Tag(name = "RefreshToken", description = "리프레시 토큰 재발급")
	public ResponseEntity<ApiResponse<Map<String, Object>>> refresh(
		@RequestHeader("Authorization") String authHeader,
		@CookieValue(value = "refreshToken", required = false) String refreshToken,
		HttpServletResponse response
	) {
		if (refreshToken == null) {
			throw new GeneralException(ErrorStatus.TOKEN_NOT_FOUND);
		}

		if (authHeader == null || authHeader.length() < 7) {
			throw new GeneralException(ErrorStatus.TOKEN_INVALID);
		}
		String accessToken = authHeader.substring(7);

		// accessToken 만료 여부 확인
		if (!didExpireToken(accessToken)) {
			return ResponseEntity.ok(ApiResponse.onSuccess(Map.of(
				"accessToken", accessToken
			)));
		}

		// refreshToken 검증
		Map<String, Object> refreshClaim = JwtUtil.validateToken(refreshToken);

		// accessToken claim 복원 (만료여도 꺼낼 수 있도록)
		Map<String, Object> originalClaim = JwtUtil.getClaimsEvenIfExpired(accessToken);

		// 새 accessToken 발급
		Map<String, Object> claims = Map.of(
			"email", originalClaim.get("email"),
			"role", originalClaim.get("role")
		);
		String newAccessToken = JwtUtil.generateToken(claims, 30);


		// refreshToken도 갱신할지 여부 확인
		Number expNumber = (Number) refreshClaim.get("exp");
		String newRefreshToken = isSomeLeftTime(expNumber.longValue())
			? JwtUtil.generateToken(refreshClaim, 60 * 24)
			: refreshToken;

		if (!newRefreshToken.equals(refreshToken)) {
			ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
												  .httpOnly(true)
												  .secure(false)
												  .path("/")
												  .maxAge(Duration.ofDays(7))
												  .sameSite("Strict")
												  .build();
			response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		}

		// 새 accessToken 도 쿠키에 넣어주기
		ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
													.httpOnly(false)
													.secure(false)
													.path("/")
													.maxAge(Duration.ofHours(1))
													.sameSite("Lax")
													.build();
		response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

		return ResponseEntity.ok(ApiResponse.onSuccess(Map.of(
			"accessToken", newAccessToken
		)));
	}

	private boolean isSomeLeftTime(long exp) {
		long nowSec = System.currentTimeMillis() / 1000;
		return (exp - nowSec) < 60 * 60;
	}

	private boolean didExpireToken(String accessToken) {
		try {
			JwtUtil.validateToken(accessToken);
			return false;
		} catch (CustomJwtException e) {
			log.info("didExpireToken error: {}", e.getMessage());
			return e.getMessage() != null && e.getMessage().contains("Expired");
		}
	}
}


