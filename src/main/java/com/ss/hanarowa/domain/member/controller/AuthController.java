package com.ss.hanarowa.domain.member.controller;

import java.io.IOException;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.hanarowa.domain.branch.dto.response.BranchResponseDTO;
import com.ss.hanarowa.domain.member.dto.request.MemberRegistRequestDTO;
import com.ss.hanarowa.domain.member.dto.response.LoginResponseDTO;
import com.ss.hanarowa.domain.member.dto.request.LoginRequestDTO;
import com.ss.hanarowa.domain.member.dto.response.TokenResponseDTO;
import com.ss.hanarowa.domain.member.entity.Member;
import com.ss.hanarowa.domain.member.entity.Role;
import com.ss.hanarowa.domain.member.service.MemberService;
import com.ss.hanarowa.global.exception.GeneralException;
import com.ss.hanarowa.global.response.code.status.ErrorStatus;
import com.ss.hanarowa.global.response.ApiResponse;
import com.ss.hanarowa.global.security.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "[사용자] 인증", description = "회원가입 및 로그인 관련 API")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final MemberService memberService;

	@PostMapping("/signup")
	@Operation(summary = "일반 회원가입")
	public ResponseEntity<ApiResponse<LoginResponseDTO>> signup(@Valid @RequestBody MemberRegistRequestDTO memberRegistRequestDTO, HttpServletResponse response) {
		memberService.credentialRegist(memberRegistRequestDTO);

		Authentication authenticate = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				memberRegistRequestDTO.getEmail(), memberRegistRequestDTO.getPassword()
			));

		TokenResponseDTO tokenDto = JwtUtil.createTokens(authenticate);

		Member member = memberService.getMemberByEmail(memberRegistRequestDTO.getEmail());

		if(member.getDeletedAt() != null) {
			throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
		}
		member.updateRefreshToken(tokenDto.getRefreshToken());

		ResponseCookie accessCookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
			.httpOnly(true)
			.secure(false)
			.path("/")
			.maxAge(Duration.ofMinutes(30))
			.sameSite("Lax")
			.build();
		response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
			.httpOnly(true)
			.secure(false)
			.path("/")
			.maxAge(Duration.ofDays(7))
			.sameSite("Lax")
			.build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

		//이부분 로컬로 돌릴때는 수정해야함
		// String redirectUrl = "http://localhost:3000/auth/signup/info";
		String redirectUrl = "https://hanarowa.topician.com/signup/info";
		LoginResponseDTO responseDto = LoginResponseDTO.builder()
			.url(redirectUrl)
			.tokens(TokenResponseDTO.builder()
				.email(tokenDto.getEmail())
				.accessToken(tokenDto.getAccessToken())
				.refreshToken(tokenDto.getRefreshToken())
				.build())
			.name(member.getName())
			.build();

		return ResponseEntity.ok(ApiResponse.onSuccess(responseDto));
	}

	@PostMapping("/signin")
	@Operation(summary = "일반 로그인")
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

			Member member = memberService.getMemberByEmail(loginRequest.getEmail());

			if(member.getDeletedAt() != null) {
				throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
			}
			member.updateRefreshToken(tokenDto.getRefreshToken());

			ResponseCookie accessCookie = ResponseCookie.from("accessToken", tokenDto.getAccessToken())
				.httpOnly(true)
				.secure(false)
				.path("/")
				.maxAge(Duration.ofMinutes(30))
				.sameSite("Lax")
				.build();
			response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

			ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
				.httpOnly(true)
				.secure(false)
				.path("/")
				.maxAge(Duration.ofDays(7))
				.sameSite("Lax")
				.build();
			response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

			String redirectUrl;
			if(member.getRole() == Role.ADMIN) {
				// redirectUrl = "http://localhost:3000/admin";
				redirectUrl = "https://hanarowa.topician.com/admin";
			}
			else if(member.getPhoneNumber() == null || member.getBirth() == null) {
				// redirectUrl = "http://localhost:3000/auth/signup/info";
				redirectUrl = "https://hanarowa.topician.com/auth/signup/info";
			}
			else {
				// redirectUrl = "http://localhost:3000";
				redirectUrl = "https://hanarowa.topician.com";
			}

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
															   						.refreshToken(tokenDto.getRefreshToken())
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

	@PostMapping("/signout")
	@Operation(summary = "로그아웃", description = "사용자 로그아웃 처리 및 쿠키 삭제")
	// @Transactional 제거!
	public ResponseEntity<ApiResponse<String>> logout(
		@CookieValue(name = "accessToken", required = false) String accessToken,
		@CookieValue(name = "refreshToken", required = false) String refreshToken,
		HttpServletResponse response
	) {
		memberService.logout(accessToken, refreshToken);

		ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "").maxAge(0).path("/").build();
		response.setHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());

		ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "").maxAge(0).path("/").build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

		return ResponseEntity.ok(ApiResponse.onSuccess("로그아웃이 완료되었습니다."));
	}

	@GetMapping("/signin/google")
	@Operation(summary = "Google 소셜 로그인", description = "Google 계정으로 로그인")
	public void googleLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/google");
	}

	@GetMapping("/signin/naver")
	@Operation(summary = "Naver 소셜 로그인", description = "Naver 계정으로 로그인")
	public void naverLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/naver");
	}

	@GetMapping("/signin/kakao")
	@Operation(summary = "Kakao 소셜 로그인", description = "Kakao 계정으로 로그인")
	public void kakaoLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/kakao");
	}
}
