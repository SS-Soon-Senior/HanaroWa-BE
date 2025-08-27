package com.ss.hanarowa.exception;

import com.ss.hanarowa.response.code.BaseErrorCode;
import com.ss.hanarowa.response.code.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ReasonDTO getReason() {
      return this.code.getReason();
    }

    public ReasonDTO getError() {
      return this.code.getReason();
    }
}
