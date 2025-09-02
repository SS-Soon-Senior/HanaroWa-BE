package com.ss.hanarowa.global.security.handler;

import java.io.IOException;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ss.hanarowa.domain.member.dto.response.TokenResponseDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.security.CustomOAuth2User;
import com.ss.hanarowa.global.security.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final MemberRepository memberRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
		Member member = principal.getMember();

		// JWT 토큰 발급
		TokenResponseDTO tokenDto = JwtUtil.createTokens(authentication);

		// RefreshToken 저장
		member.updateRefreshToken(tokenDto.getRefreshToken());
		memberRepository.save(member);

		ResponseCookie accessCookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
													.httpOnly(false)
													.secure(false)
													.path("/")
													.maxAge(Duration.ofHours(1))
													.sameSite("Lax")
													.build();
		response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
													 .httpOnly(true)
													 .secure(false)
													 .path("/")
													 .maxAge(Duration.ofDays(7))
													 .sameSite("Strict")
													 .build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

		String baseRedirectUrl = "http://localhost:3000";
		String path = (member.getPhoneNumber() == null || member.getBirth() == null)
			? "/auth/signup/info"
			: "/";
		getRedirectStrategy().sendRedirect(request, response, baseRedirectUrl + path);

	}
}

