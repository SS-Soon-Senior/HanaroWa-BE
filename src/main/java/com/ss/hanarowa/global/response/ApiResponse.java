package com.ss.hanarowa.global.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ss.hanarowa.global.response.code.status.SuccessStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

	@JsonProperty("isSuccess")
	private final boolean isSuccess;
	
	@JsonIgnore
	public boolean isSuccess() {
		return isSuccess;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public T getResult() {
		return result;
	}
	
	private final String code;
	private final String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T result;
	
	public static <T> ApiResponse<T> onSuccess(T result){
		return new ApiResponse<>(true, SuccessStatus.SUCCESS.getCode(),
			SuccessStatus.SUCCESS.getMessage(), result);
	}

	public static <T> ApiResponse<T> onFailure(String code, String message, T data){
		return new ApiResponse<>(false, code, message, data);
	}


}
