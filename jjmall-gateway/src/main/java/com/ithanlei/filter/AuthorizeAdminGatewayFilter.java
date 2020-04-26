package com.ithanlei.filter;

import com.ithanlei.util.SpringUtils;
import com.ithanlei.utils.JwtUtil;
import com.ithanlei.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 认证管理员的token   略有不同
 */
@Slf4j
public class AuthorizeAdminGatewayFilter implements GatewayFilter, Ordered {
    private final static String Authorize_Token = "admin_token";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //容器加载顺序问题 不能自动注入
        RedisUtil redisUtil = SpringUtils.getBean(RedisUtil.class);
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        //TODO 前端也要改key
        String token = headers.getFirst(Authorize_Token);
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
        String key = Authorize_Token;
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
            return chain.filter(exchange);
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

}
