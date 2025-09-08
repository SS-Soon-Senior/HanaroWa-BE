package com.ss.hanarowa.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDTO {
	private String email;
	private String accessToken;
	private String refreshToken;
}
