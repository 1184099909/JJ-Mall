package com.ithanlei.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则表达式做一些判断
 */
public class RefexUtil {
    public static boolean isPhone(String phone){
        // "[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3578]\\d{9}";
        if(StringUtils.isBlank(phone)){
            return false;
        }
        return phone.matches(telRegex);
    }
}
