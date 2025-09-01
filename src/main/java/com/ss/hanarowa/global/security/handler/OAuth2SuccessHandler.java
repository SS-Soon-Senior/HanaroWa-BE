package com.ss.hanarowa.global.security.handler;

import java.io.IOException;

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

		// RefreshToken DB 저장
		member.updateRefreshToken(tokenDto.getRefreshToken());
		memberRepository.save(member);

		// 추가 정보 입력 필요 여부 확인
		String baseRedirectUrl = "http://localhost:3000";
		String path = (member.getPhoneNumber() == null || member.getBirth() == null)
			? "/auth/signup/info"
			: "/";

		// 토큰을 쿼리파라미터에 포함시켜 리다이렉트
		String redirectUrl = String.format(
			"%s%s?accessToken=%s&refreshToken=%s",
			baseRedirectUrl, path,
			tokenDto.getAccessToken(),
			tokenDto.getRefreshToken()
		);

		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
