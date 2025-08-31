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
		"/member/regist",
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
			
			// 블랙리스트 검증
			if (tokenBlacklistService.isBlacklisted(token)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json;charset=UTF-8");

				ReasonDTO reason = ErrorStatus.MEMBER_NOT_AUTHORITY.getReason();
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(reason);

				response.getWriter().write(json);
			}
			
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

			ApiResponse<Object> errorResponse = ApiResponse.onFailure(
				ErrorStatus.TOKEN_INVALID.getCode(), 
				ErrorStatus.TOKEN_INVALID.getMessage(), 
				null
			);
			out.println(objectMapper.writeValueAsString(errorResponse));
			out.flush();
			out.close();
		}
		System.out.println(">>> JwtFilter path=" + request.getRequestURI()
			+ " authHeader=" + request.getHeader(HttpHeaders.AUTHORIZATION));
	}
}
