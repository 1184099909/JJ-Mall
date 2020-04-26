package com.ithanlei.exception;

public class VerifyTokenException extends RuntimeException {
    public VerifyTokenException(String message){
        super(message);
    }
}
