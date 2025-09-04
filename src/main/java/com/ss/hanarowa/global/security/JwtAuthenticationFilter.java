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
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.response.code.ReasonDTO;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.security.exception.CustomJwtException;

import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenBlacklistService tokenBlacklistService;

	private static final List<String> PERMIT_ALL_URLS = Arrays.asList(
		"/auth/signup",
		"/auth/signin",
		"/member/refresh",
		"/oauth2/authorization/kakao",
		"/oauth2/authorization/google",
		"/oauth2/authorization/naver",
		"/login/oauth2/code/kakao",
		"/login/oauth2/code/google",
		"/login/oauth2/code/naver"
	);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String path = request.getRequestURI();
		if (PERMIT_ALL_URLS.contains(path) || path.equals("/auth/reissue")) {
			filterChain.doFilter(request, response);
			return;
		}

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);

		try {
			if (tokenBlacklistService.isBlacklisted(token)) {
				throw new CustomJwtException("Blacklisted");
			}

			Map<String, Object> claims = JwtUtil.validateToken(token);
			setAuthenticationFromClaims(claims);
		} catch (CustomJwtException e) {
			if ("Expired".equals(e.getMessage())) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setHeader("Token-Expired", "true");
				return;
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthenticationFromClaims(Map<String, Object> claims) {
		String email = (String)claims.get("email");
		String roleStr = (String)claims.get("role");
		Role role = Role.valueOf(roleStr);

		MemberAuthResponseDTO dto = new MemberAuthResponseDTO(email, "", role);

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(dto, null, dto.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
}
