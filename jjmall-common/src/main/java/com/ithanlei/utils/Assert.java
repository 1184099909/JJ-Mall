package com.ithanlei.utils;

import com.ithanlei.constant.CoreConstant;
import com.ithanlei.exception.BusinessException;
import com.ithanlei.exception.RequestParameterIsNullException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Collection;

/**
 * 断言包装类   对参数进行判断满足条件则抛出异常
 */
public class Assert {
    //判断string是否为空(只有空格也为空)
    public static void isBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(msg);
        }
    }

    public static void isBlank(String str) {
        if (StringUtils.isBlank(str)) {
            throw new RequestParameterIsNullException(CoreConstant.PARAM_NULL);
        }
    }

    //判断传入的object是否为null
    public static void isNull(Object obj, String msg) {
        if (obj == null) {
            throw new BusinessException(msg);
        }
    }
    public static void isNull(Object obj) {
        if (obj == null) {
            throw new RequestParameterIsNullException(CoreConstant.PARAM_NULL);
        }
    }

    /**
     * 判断传入的集合是否为空
     */
    public static void isEmpty(Collection collection, String msg) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(msg);
        }
    }

    /**
     * 一个boolean为否则抛出异常
     */
    public static void isNotTrue(boolean b, String msg) {
        if (!b) {
            throw new BusinessException(msg);
        }
    }
}
