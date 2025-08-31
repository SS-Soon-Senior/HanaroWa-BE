package com.ss.hanarowa.global.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ss.hanarowa.domain.member.dto.response.LoginResponseDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final MemberService memberService;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		String email = authentication.getName();
		Member member = memberService.getMemberByEmail(email);
		String redirectUrl;
		if (member.getPhoneNumber() == null || member.getBirth() == null) {
			redirectUrl = "http://localhost:3000/auth/signup/info";
		} else {
			redirectUrl = "http://localhost:3000";
		}


		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}