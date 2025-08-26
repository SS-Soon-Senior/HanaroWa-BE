package com.ss.hanarowa.response.code;

import org.springframework.http.HttpStatusCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorReasonDTO {
	private HttpStatusCode httpStatusCode;

	private final boolean isSuccess;
	private final String code;
	private final String message;
}
