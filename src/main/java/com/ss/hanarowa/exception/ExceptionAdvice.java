package com.ss.hanarowa.exception;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ss.hanarowa.response.ApiResponse;
import com.ss.hanarowa.response.code.status.ErrorStatus;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	// validation 에러
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request) {

		Map<String, String> errors = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			String field = fieldError.getField();
			String message = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
			errors.merge(field, message, (exist, newMsg) -> exist + ", " + newMsg);
		});

		return buildResponseEntity(ErrorStatus.MEMBER_BAD_REQUEST, errors);
	}

	// 어노테이션 제약 조건 에러
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
		String errorMessage = ex.getConstraintViolations().stream()
								.map(v -> v.getMessage())
								.findFirst()
								.orElse("유효성 검증 실패");
		return buildResponseEntity(ErrorStatus.MEMBER_BAD_REQUEST, errorMessage);
	}

	// 인증, 인가 에러
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
		return buildResponseEntity(ErrorStatus.MEMBER_NOT_AUTHORITY, null);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> handleAuthentication(AuthenticationException ex) {
		return buildResponseEntity(ErrorStatus.MEMBER_NOT_AUTHORITY, null);
	}

	// 서버 에러
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		log.error("Unexpected Exception", ex);
		return buildResponseEntity(ErrorStatus._INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	private ResponseEntity<Object> buildResponseEntity(ErrorStatus status, Object data) {
		ApiResponse<Object> body = ApiResponse.onFailure(
			status.getCode(),
			status.getMessage(),
			data
		);
		return ResponseEntity
			.status(status.getHttpStatusCode())
			.body(body);
	}
}

