package com.ss.hanarowa.response.code.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.ss.hanarowa.response.code.BaseCode;
import com.ss.hanarowa.response.code.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

	SUCCESS(HttpStatus.OK, "COMMON 200", "성공입니다.");

	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReason() {
		return ReasonDTO.builder()
						.httpStatusCode(httpStatusCode)
						.isSuccess(false)
						.code(code)
						.message(message)
						.build();
	}
}
