package com.ss.hanarowa.domain.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.security.JwtUtil;
import com.ss.hanarowa.global.security.exception.CustomJwtException;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
public class JwtRefreshController {
	@PostMapping("/member/refresh")
	@Tag(name = "RefreshToken", description = "리프레시 토큰 확인")
	public ResponseEntity<ApiResponse<Map<String, Object>>> refresh(
		@RequestHeader("Authorization") String authHeader,
		@RequestParam String refreshToken) {
		if (refreshToken == null)
			throw new GeneralException(ErrorStatus.TOKEN_NOT_FOUND);
		System.out.println(authHeader);

		if (authHeader == null || authHeader.length() < 7) {
			throw new GeneralException(ErrorStatus.TOKEN_INVALID);
		}
		String accessToken = authHeader.substring(7);
		if (!didExpireToken(accessToken)) {
			return ResponseEntity.ok(ApiResponse.onSuccess(Map.of("accessToken", accessToken, "refreshToken", refreshToken)));
		}

		Map<String, Object> claim = JwtUtil.validateToken(refreshToken);
		String newAccessToken = JwtUtil.generateToken(claim, 10);
		Number expNumber = (Number) claim.get("exp");
		String newRefreshToken = isSomeLeftTime(expNumber.longValue())
			? JwtUtil.generateToken(claim, 60 * 24)
			: refreshToken;

		//newRefreshToken DB에 저장?

		return ResponseEntity.ok(ApiResponse.onSuccess(Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken)));
	}

	private boolean isSomeLeftTime(long exp) {
		long nowSec = System.currentTimeMillis() / 1000;
		return (exp - nowSec) < 60 * 60;
	}

	private boolean didExpireToken(String accessToken) {
		try {
			JwtUtil.validateToken(accessToken);
		} catch (CustomJwtException e) {
			return true;
		}
		return false;
	}
}
