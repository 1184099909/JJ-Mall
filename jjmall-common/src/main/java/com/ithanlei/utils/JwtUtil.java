package com.ithanlei.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装jwt的生成以及认证方法
 */
@Slf4j
public class JwtUtil {
    //密钥
    private final static String SECRET = "jjmalljwtsecret";
    //过期时间  单位为ms
    private final static Long EXPIRATION = 1L;
    /**
     * 生成用户token
     */
    public static String creatJwt(Integer userId, String username){
        //不再设过期时间
        //过期时间
//        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000 * 60 * 60 * 24 * 3);
        Map<String,Object> map = new HashMap<>();
        map.put("typ", "JWT");
        map.put("alg", "HS256");
        String token = JWT.create().withHeader(map)
                .withAudience(userId.toString())
                .withClaim("userId", userId)
                .withClaim("username", username)
//                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));
        return token;
        }

    /**
     * 校验token合法性   网关校验
     */
    public static boolean vertifyToken(String token){
//        DecodedJWT decodedJWT = null;
        try {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        jwtVerifier.verify(token);
        } catch (JWTVerificationException e){
            log.info(e.getMessage());
            log.info("校验token失败！");
//            throw new VerifyTokenException("校验token失败！");
            return false;
        }
        return true;
    }

    /**
     * 解析token 取出token中的userId   后端服务在需要的时候取出
     */
    public static String getUserIdOfToken(String token){
        String userId = null;
        try{
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            log.info(e.getMessage());
            log.info("解析token失败");
//            throw new ResolveTokenException("解析token失败！");
            return null;
        }
        //也可取出calim中的username
       /* JWTVerifier jwtVerifier =  JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> userData = decodedJWT.getClaims();
        String username = userData.get("username").asString();*/
        return userId;




    }


}


