package com.ss.hanarowa.domain.member.controller;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.branch.dto.response.BranchResponseDTO;
import com.ss.hanarowa.domain.member.dto.response.LoginResponseDTO;
import com.ss.hanarowa.domain.member.dto.request.LoginRequestDTO;
import com.ss.hanarowa.domain.member.dto.response.TokenResponseDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.repository.MemberRepository;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.security.JwtUtil;
import com.ss.hanarowa.global.security.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
	
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final MemberRepository memberRepository;
	private final TokenBlacklistService tokenBlacklistService;

	@PostMapping("/signin")
	@Tag(name = "로그인", description = "사용자 로그인")
	@Transactional
	public ResponseEntity<ApiResponse<LoginResponseDTO>> signin(
		@RequestBody LoginRequestDTO loginRequest,
		HttpServletResponse response
	) {
		try {
			Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					loginRequest.getEmail(), loginRequest.getPassword()
				)
			);

			TokenResponseDTO tokenDto = JwtUtil.createTokens(authenticate);

			Member member = memberRepository.findByEmail(loginRequest.getEmail())
											.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

			member.updateRefreshToken(tokenDto.getRefreshToken());

			ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
												  .httpOnly(true)
												  .secure(false)
												  .path("/")
												  .maxAge(Duration.ofDays(7))
												  .sameSite("Strict")
												  .build();
			response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

			String redirectUrl = (member.getPhoneNumber() == null || member.getBirth() == null)
				? "http://localhost:3000/auth/signup/info"
				: "http://localhost:3000";

			BranchResponseDTO branchDto = null;
			if (member.getBranch() != null) {
				branchDto = BranchResponseDTO.builder()
											 .branchId(member.getBranch().getId())
											 .locationName(member.getBranch().getLocation().getName())
											 .branchName(member.getBranch().getName())
											 .build();
			}

			LoginResponseDTO responseDto = LoginResponseDTO.builder()
														   .url(redirectUrl)
														   .tokens(TokenResponseDTO.builder()
																				   .email(tokenDto.getEmail())
																				   .accessToken(tokenDto.getAccessToken())
																				   .build())
														   .branch(branchDto)
														   .name(member.getName())
														   .birth(member.getBirth())
														   .phoneNumber(member.getPhoneNumber())
														   .build();

			return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
		} catch (AuthenticationException e) {
			throw new GeneralException(ErrorStatus.MEMBER_AUTHENTICATION_FAILED);
		}
	}


	@PostMapping("/logout")
	@Operation(summary = "로그아웃", description = "사용자 로그아웃 및 refreshToken 삭제")
	@Transactional
	public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		log.info("로그아웃 요청 - Authorization header: {}", authorizationHeader);
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String accessToken = authorizationHeader.substring(7);
			log.info("액세스 토큰 추출 완료: {}", accessToken.substring(0, Math.min(20, accessToken.length())) + "...");
			
			try {
				// accessToken에서 사용자 정보 추출
				Map<String, Object> claims = JwtUtil.validateToken(accessToken);
				String email = (String) claims.get("email");
				log.info("토큰에서 이메일 추출: {}", email);
				
				// accessToken을 블랙리스트에 추가 (토큰 만료시간 추출)
				long expirationTime = ((Number) claims.get("exp")).longValue() * 1000; // JWT exp는 초 단위
				tokenBlacklistService.blacklistToken(accessToken, expirationTime);
				log.info("accessToken 블랙리스트 추가 완료");
				
				// Member의 refreshToken 삭제
				Member member = memberRepository.findByEmail(email)
					.orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
				log.info("로그아웃 전 refreshToken: {}", member.getRefreshToken() != null ? "존재함" : "없음");
				
				member.clearRefreshToken();
				memberRepository.save(member);
				log.info("refreshToken 삭제 완료");
				
			} catch (Exception e) {
				log.error("로그아웃 처리 중 에러 발생: {}", e.getMessage());
			}
		} else {
			log.warn("Authorization header가 없거나 Bearer 형식이 아님");
		}
		
		return ResponseEntity.ok(ApiResponse.onSuccess("로그아웃이 완료되었습니다."));
	}

	@GetMapping("/signin/google")
	@Operation(summary = "Google 소셜 로그인", description = "Google 계정으로 로그인")
	@Tag(name = "로그인", description = "구글")
	public void googleLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/google");
	}

	@GetMapping("/signin/naver")
	@Operation(summary = "Naver 소셜 로그인", description = "Naver 계정으로 로그인")
	@Tag(name = "로그인", description = "네이버")
	public void naverLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/naver");
	}

	@GetMapping("/signin/kakao")
	@Operation(summary = "Kakao 소셜 로그인", description = "Kakao 계정으로 로그인")
	@Tag(name = "로그인", description = "카카오")
	public void kakaoLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/kakao");
	}
}
