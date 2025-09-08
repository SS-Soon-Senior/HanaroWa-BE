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
import com.ss.hanarowa.domain.member.entity.Role;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.security.CustomOAuth2User;
import com.ss.hanarowa.global.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final MemberRepository memberRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		Member member;

		if (authentication.getPrincipal() instanceof CustomOAuth2User principal) {
			member = principal.getMember();
		} else {
			String email = authentication.getName();
			member = memberRepository.findByEmail(email)
									 .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		}

		// JWT 발급
		TokenResponseDTO tokenDto = JwtUtil.createTokens(authentication);
		member.updateRefreshToken(tokenDto.getRefreshToken());
		memberRepository.save(member);

		// 공통 쿠키 세팅
		addTokenCookies(response, tokenDto);

		// 공통 redirect 처리
		String redirectUrl = getRedirectUrl(member);
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

	private void addTokenCookies(HttpServletResponse response, TokenResponseDTO tokenDto) {
		ResponseCookie accessCookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
													.httpOnly(true)
													.secure(false)
													.path("/")
													.maxAge(Duration.ofMinutes(30))
													.sameSite("Lax")
													.build();
		response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
													 .httpOnly(true)
													 .secure(false)
													 .path("/")
													 .maxAge(Duration.ofDays(7))
													 .sameSite("Lax")
													 .build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
	}

	private String getRedirectUrl(Member member) {
		if (member.getRole() == Role.ADMIN) {
			return "http://localhost:3000/admin";
		} else if (member.getPhoneNumber() == null || member.getBirth() == null) {
			return "http://localhost:3000/auth/signup/info";
		} else {
			return "http://localhost:3000";
		}
	}
}