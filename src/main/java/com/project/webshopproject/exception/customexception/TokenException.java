package com.project.webshopproject.exception.customexception;


import com.project.webshopproject.exception.errorcode.ErrorCode;
import com.project.webshopproject.exception.globalexception.GlobalStatusException;


public class TokenException extends GlobalStatusException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}