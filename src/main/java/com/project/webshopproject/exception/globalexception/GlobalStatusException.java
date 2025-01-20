package com.project.webshopproject.exception.globalexception;

import com.project.webshopproject.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public abstract class GlobalStatusException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalStatusException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}