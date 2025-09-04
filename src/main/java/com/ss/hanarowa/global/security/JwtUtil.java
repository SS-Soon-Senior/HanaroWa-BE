package com.ss.hanarowa.global.security;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import com.ss.hanarowa.domain.member.dto.response.MemberAuthResponseDTO;
import com.ss.hanarowa.domain.member.dto.response.TokenResponseDTO;
import com.ss.hanarowa.global.security.exception.CustomJwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;

public class JwtUtil {
	private static final SecretKey K = Keys.hmacShaKeyFor(
		"asdlkjsa232sa;ljdsf$#$asdfdsaf!@!asdfdsafsdfsdaf".getBytes(StandardCharsets.UTF_8));

	public static String generateToken(Map<String, Object> valueMap, int min) {
		String jwtStr = Jwts.builder().setHeader(Map.of("typ", "JWT"))
			.setClaims(valueMap)
			.setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
			.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
			.signWith(K).compact();
		System.out.println("jwtStr = " + jwtStr);
		return jwtStr;
	}

	public static TokenResponseDTO createTokens(Authentication authentication) {
		Object principal = authentication.getPrincipal();

		String email;
		String role;

		if (principal instanceof MemberAuthResponseDTO user) {
			email = user.getEmail();
			role = user.getRole().name();
		} else if (principal instanceof CustomOAuth2User oAuth2User) {
			email = oAuth2User.getMember().getEmail();
			role = oAuth2User.getMember().getRole().name();
		} else {
			throw new IllegalArgumentException("지원하지 않는 principal 타입: " + principal.getClass());
		}

		Map<String, Object> claims = Map.of(
			"email", email,
			"role", role
		);

		String accessToken = generateToken(claims, 1);
		String refreshToken = generateToken(Map.of("email", email), 60 * 24);

		return TokenResponseDTO.builder()
			.email(email)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public static Map<String, Object> validateToken(String token) {
		Map<String, Object> claim = null;
		SecretKey key = null;

		try {
			claim = Jwts.parserBuilder()
				.setSigningKey(K)
				.build()
				.parseClaimsJws(token).getBody();
		} catch (WeakKeyException e) {
			throw new CustomJwtException("WeakException");
		} catch (MalformedJwtException e) {
			throw new CustomJwtException("MalFormed");
		} catch (ExpiredJwtException e) {
			throw new CustomJwtException("Expired");
		} catch (InvalidClaimException e) {
			throw new CustomJwtException("Invalid");
		} catch (JwtException e) {
			throw new CustomJwtException("JwtError");
		} catch (Exception e) {
			throw new CustomJwtException("UnknownError");
		}

		return claim;
	}

	public static Map<String, Object> getClaimsEvenIfExpired(String token) {
		try {
			return Jwts.parserBuilder()
					   .setSigningKey(K)
					   .build()
					   .parseClaimsJws(token)
					   .getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
