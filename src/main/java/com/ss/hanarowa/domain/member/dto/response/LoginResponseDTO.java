package com.ss.hanarowa.domain.member.dto.response;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {
	private String url;
	private TokenResponseDTO tokens;
}