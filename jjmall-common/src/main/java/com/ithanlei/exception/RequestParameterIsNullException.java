package com.ithanlei.exception;

/**
 * 请求参数为空
 */
public class RequestParameterIsNullException extends RuntimeException{

    public RequestParameterIsNullException(String message){
        super(message);
    }
}
