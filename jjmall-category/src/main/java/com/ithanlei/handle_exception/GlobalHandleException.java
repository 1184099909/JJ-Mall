package com.ithanlei.handle_exception;

import com.ithanlei.constant.CoreConstant;
import com.ithanlei.exception.RequestParameterIsNullException;
import com.ithanlei.result.APIResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局捕获异常
 */
@ControllerAdvice(basePackages = {"com.ithanlei.controller", "com.ithanlei.service"})
public class GlobalHandleException {
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public APIResult handleRuntimeException(){
        return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
    }

    @ResponseBody
    @ExceptionHandler(RequestParameterIsNullException.class)
    public APIResult handleRequestParameterIsNullException(){
        return APIResult.failture(CoreConstant.PARAM_ERR, CoreConstant.PARAM_NULL);
    }

}
