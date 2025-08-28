package com.ss.hanarowa.global.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.hanarowa.domain.member.dto.response.MemberAuthResponseDTO;
import com.ss.hanarowa.domain.member.entity.Role;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final List<String> PERMIT_ALL_URLS = Arrays.asList(
		"/member/regist",
		"/auth/signin"
	);


	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		String path = request.getRequestURI();
		if (PERMIT_ALL_URLS.contains(path)) {
			filterChain.doFilter(request, response);
			return;
		}

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			String token = authHeader.substring(7);
			Map<String, Object> claims = JwtUtil.validateToken(token);

			String email = (String) claims.get("email");
			String roleStr = (String) claims.get("role");
			Role role = Role.valueOf(roleStr);

			MemberAuthResponseDTO dto = new MemberAuthResponseDTO(email, "", role);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				dto, null, dto.getAuthorities()
			);

			SecurityContextHolder.getContext().setAuthentication(authenticationToken);


			filterChain.doFilter(request, response);

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json;charset=UTF-8");

			ObjectMapper objectMapper = new ObjectMapper();
			PrintWriter out = response.getWriter();

			String errorMessage;
			errorMessage = "INVALID_TOKEN";

			out.println(objectMapper.writeValueAsString(Map.of("error", errorMessage, "message", e.getMessage())));
			out.flush();
			out.close();
		}
	}
}