package com.ss.hanarowa.global.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.hanarowa.global.response.code.ReasonDTO;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json;charset=UTF-8");

		ReasonDTO reason = ErrorStatus.MEMBER_NOT_AUTHORITY.getReason();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(reason);

		response.getWriter().write(json);
	}
}

