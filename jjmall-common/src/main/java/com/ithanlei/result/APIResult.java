package com.ithanlei.result;

import com.ithanlei.constant.CoreConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * 封装统一的返回结果API
 */
@Setter
@Getter
public class APIResult extends Result {
    private static final long serialVersionUID = -8158734906933781946L;
    private Object data;

    APIResult() {
    }
    APIResult(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    APIResult(int code, String msg, Object data) {
        this.code = code + "";
        this.msg = msg;
        this.data = data;
    }
    public static APIResult success(Object data) {
        return new APIResult(CoreConstant.SUCCESS_CODE, CoreConstant.SUCCESS_VALUE, data);
    }
    public static APIResult success(String successMsg, Object data) {
        return new APIResult(CoreConstant.SUCCESS_CODE, successMsg, data);
    }
    public static APIResult success(String successMsg) {
        return new APIResult(CoreConstant.SUCCESS_CODE, successMsg, null);
    }

    /**
     * 比较笼统的统一状态码500
     *
     * @param errMsg
     * @return
     */
    public static APIResult failture(String errMsg) {
        return new APIResult(500, errMsg, null);
    }
    public static APIResult failture(String errCode, String errMsg) {
        return new APIResult(errCode, errMsg, null);
    }
    public static APIResult failture(int errCode, String errMsg) {
        return new APIResult(errCode, errMsg, null);
    }

    //校验错误  code不等于0则有错误
    public static boolean hasError(APIResult result) {
        return null != result.getCode() && !"200".equals(result.getCode());
    }
}
