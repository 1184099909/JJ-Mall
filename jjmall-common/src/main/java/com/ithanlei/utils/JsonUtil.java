package com.ithanlei.utils;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

/**
 * json的序列化和反序列化
 */
@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //所有字段都列入转换
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        //取消默认转换timestamp形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //忽略json存在属性而bean不存在属性的错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * bean转为string
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2str(T obj) {
        if (null == obj) {
            return null;
        }
        String str = null;
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("parse obj to string error!", e.getMessage());
            return null;
        }
    }

    /**
     * str转为obj
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T str2obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || null == clazz) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("parse str to obj error!", e.getMessage());
            return null;
        }
    }

    /**
     * 复杂对象的反序列化
     *
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T str2obj(String str, TypeReference typeReference) {
        if (StringUtils.isEmpty(str) || null == typeReference) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("parse str to obj error!", e.getMessage());
        }
        return null;

    }


}
