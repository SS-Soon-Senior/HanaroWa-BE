package com.ss.hanarowa.global.security.exception;

public class CustomJwtException extends RuntimeException {
	public CustomJwtException(String msg) {
		super("JwtErr:" + msg);
	}
}