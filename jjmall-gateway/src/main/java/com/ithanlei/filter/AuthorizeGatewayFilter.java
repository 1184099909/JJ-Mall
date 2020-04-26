package com.ithanlei.filter;

import com.ithanlei.util.SpringUtils;
import com.ithanlei.utils.JwtUtil;
import com.ithanlei.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;

/**
 * 拦截请求  进行token认证
 * 没有token或token不合法都会返回401
 */
@Slf4j
public class AuthorizeGatewayFilter implements GatewayFilter, Ordered {
    private final static java.lang.String Authorize_Token = "token";

    /*@Resource
    private RedisUtil redisUtil;*/


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //容器加载顺序问题 不能自动注入
        RedisUtil redisUtil = SpringUtils.getBean(RedisUtil.class);
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        java.lang.String token = headers.getFirst(Authorize_Token);
        //header中获取不到尝试从请求参数中获取
        if (null == token) {
            token = request.getQueryParams().getFirst(Authorize_Token);
        }
        //取不到token则直接返回错误信息
        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isBlank(token)) {
            log.info("没有取到token");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //取到token则开始校验  还要校验redis中是否存在
        log.info("token:{}", token);
        boolean a = JwtUtil.vertifyToken(token);
        System.out.println(a);
        java.lang.String userId = JwtUtil.getUserIdOfToken(token);
        log.info("userId:{}", userId);
        String key = userId + "_token";
        log.info("key:{}", key);
        String redisToken = null;
        //此处会报空指针异常  因为注入问题
        try {
            redisToken = redisUtil.getKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

        if (a && StringUtils.equals(token, redisToken)) {
            log.info("token认证成功");
            //取出token中的userId加入参数
            //修改uri
            URI uri = request.getURI();
			//TODO 此处不要忽略原有的URI  同时要考虑最后一位是否是&
            URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery("userId=" + userId).build(true).toUri();
            ServerHttpRequest newReq = request.mutate().uri(newUri).build();
            System.out.println("后：" + newReq.getQueryParams());
            return chain.filter(exchange.mutate().request(newReq).build());
        }
        //校验不通过则返回错误信息
        log.info("token不合法,校验失败");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }


    @Override
    public int getOrder() {
        return 0;
    }
	//新借鉴
	String originalQuery = uri.getRawQuery();
                if (StringUtils.isNotBlank(originalQuery)) {
                    query.append(originalQuery);
                    if (originalQuery.charAt(originalQuery.length() - 1) != '&') {
                        query.append('&');
                    }
                }
                query.append("userId");
                query.append("=");
                if (userId.contains("{")) {
                    userId = userId.replaceAll("\\{", "%7B");
                }
                if (userId.contains("}")) {
                    userId = userId.replaceAll("\\}", "%7D");
                }
                query.append(userId);

}
