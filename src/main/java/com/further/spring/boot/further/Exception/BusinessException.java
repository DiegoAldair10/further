package com.further.spring.boot.further.Exception;


public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}