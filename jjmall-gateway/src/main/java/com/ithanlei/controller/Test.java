package com.ithanlei.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ithanlei.utils.JwtUtil;
import com.ithanlei.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class Test {
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/testrun")
     public String testrun(){
        return "gateway running!";
    }
    /**
     * 生成一个token供测试
     */
    public static void main(String[] args){
        String token = JwtUtil.creatJwt(2, "admin");
        System.out.println(token);
        }
    /**
     * 测试vertify校验不通过
     */
    @RequestMapping("/testVertify")
    public String testVertify(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODM5MDczOTMsInVzZXJJZCI6MTIzLCJpYXQiOjE1ODM5MDU1OTMsInVzZXJuYW1lIjoiaGFuanVuanVuIn0.RhS0Id28dBc0iu6jZGMc5YFBt889YXbysxE2WmyPS5";
        DecodedJWT decodedJWT = null;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("jjmalljwtsecret")).build();
            decodedJWT = jwtVerifier.verify(token);
        } catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return decodedJWT.getToken();
    }

    @RequestMapping("/testSet")
    public String testSet(String key, String value){
        redisUtil.setKey(key, value);
        return "key:" + key + "value:" + value;
    }
    @RequestMapping("/testGet")
    public String testGet(String key){
        String value = redisUtil.getKey(key);
        return "key:" + key + "，value:" + value;
    }
    @RequestMapping("/testSetTime")
    public String testSetTime(String key, String value, long time){
        redisUtil.setKey(key, value, 360);
        return "key:" + key + "value:" + value;
    }
    @RequestMapping("/delKey")
    public String delKey(String key){
        redisUtil.delKey(key);
        return "key:" + key;
    }

}
