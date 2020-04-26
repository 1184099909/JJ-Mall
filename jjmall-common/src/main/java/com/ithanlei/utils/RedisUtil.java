package com.ithanlei.utils;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 封装redis操作类
 */
@Component
public class RedisUtil {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * set缓存   无失效时间
     * @param key
     * @param value
     * @return
     */
    public boolean setKey(String key, String value){
        if(StringUtils.isBlank(key)  || StringUtils.isBlank(value)){
            return false;
        }
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 带有失效时间的set
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setKey(String key, String value, long time, TimeUnit timeUnit){
        if(time < 0){
            return setKey(key, value);
        }
        try {
            stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断有无这个key
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        if(StringUtils.isBlank(key)){
            return false;
        }
       String v = stringRedisTemplate.opsForValue().get(key);
       if(StringUtils.isEmpty(v)){
           return false;
       }
       return true;
    }

    /**
     * get缓存
     * @param key
     * @return
     */
    public String getKey(String key){
        if(!hasKey(key)){
            return null;
        }
        return stringRedisTemplate.opsForValue().get(key);

    }

    /**
     * 删除一个key
     * @param k
     * @return
     */
    public boolean delKey(String k){
        if(!hasKey(k)){
            return false;
        }
        try {
            stringRedisTemplate.delete(k);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新一个key的失效时间 单位为秒 小于0则无限期
     * @param k
     * @param time
     * @return
     */
    public boolean updateExpire(String k, long time){
        try {
            if(time > 0){
                stringRedisTemplate.expire(k, time, TimeUnit.SECONDS);
            }else {
                stringRedisTemplate.expire(k, -1, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }




}
