package com.ithanlei.exception;

public class UnknowAccountException extends RuntimeException{
    public UnknowAccountException(String message){
        super(message);
    }
}
