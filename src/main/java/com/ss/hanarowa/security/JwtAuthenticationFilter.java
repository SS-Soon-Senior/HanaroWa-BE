package com.ss.hanarowa.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.hanarowa.member.dto.MemberAuthDTO;
import com.ss.hanarowa.member.entity.Role;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	private final String[] excludePatterns = {
		"/api/member/regist", "/api/member/info", "/api/auth/signin", "/api/public/**",
		"/api/auth/**", "/favicon.ico", "/actuator/**", "/*.html", "/swagger-ui/**",
		"/v3/api-docs/**", "/hanarowa/api-docs/**", "/broadcast/**", "/swagger.html"
	};

	@Override
	protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		return Arrays.stream(excludePatterns)
			.anyMatch(pattern -> pathMatcher.match(pattern, path));
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

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

			MemberAuthDTO dto = new MemberAuthDTO(email, "", role);

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