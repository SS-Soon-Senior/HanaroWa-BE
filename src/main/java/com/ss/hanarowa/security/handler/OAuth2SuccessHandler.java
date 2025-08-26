package com.ss.hanarowa.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ss.hanarowa.member.entity.Member;
import com.ss.hanarowa.member.repository.MemberRepository;

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

		String redirectUrl;
		if (member.getPhone() == null || member.getBirth() == null) {
			redirectUrl = "http://localhost:3000/auth/signup/info";
		} else {
			redirectUrl = "http://localhost:3000";
		}

		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}

