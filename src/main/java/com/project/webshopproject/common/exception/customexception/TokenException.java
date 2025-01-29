package com.project.webshopproject.common.exception.customexception;


import com.project.webshopproject.common.exception.errorcode.ErrorCode;
import com.project.webshopproject.common.exception.globalexception.GlobalStatusException;


public class TokenException extends GlobalStatusException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}