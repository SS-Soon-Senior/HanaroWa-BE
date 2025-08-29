package com.ss.hanarowa.global.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

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
		String email = authentication.getName();
		Member member = memberRepository.findByEmail(email).orElseThrow(()-> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
		String redirectUrl;
		if (member.getPhoneNumber() == null || member.getBirth() == null) {
			redirectUrl = "http://localhost:3000/auth/signup/info";
		} else {
			redirectUrl = "http://localhost:3000";
		}

		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}