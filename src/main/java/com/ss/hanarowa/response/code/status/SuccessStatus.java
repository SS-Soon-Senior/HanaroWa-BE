package com.ss.hanarowa.response.code.status;

import org.springframework.http.HttpStatus;

import com.ss.hanarowa.response.code.BaseCode;
import com.ss.hanarowa.response.code.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

	SUCCESS(HttpStatus.OK, "COMMON 200", "성공입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReasonHttpStatus() {
		return ReasonDTO.builder()
						.message(message)
						.code(code)
						.isSuccess(true)
						.httpStatus(status)
						.build();
	}
}
