package com.ss.hanarowa.global.response.code;

import org.springframework.http.HttpStatusCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReasonDTO {
	private HttpStatusCode httpStatusCode;

	private final boolean isSuccess;
	private final String code;
	private final String message;
}