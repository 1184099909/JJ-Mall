package com.ithanlei;

import com.ithanlei.filter.AuthorizeAdminGatewayFilter;
import com.ithanlei.filter.AuthorizeGatewayFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@RefreshScope
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
        log.info("gateway-service started on 8088...");
        }

     @Bean
    public RouteLocator routeLocatorUser(RouteLocatorBuilder builder){
        return builder.routes().route(r ->
                r.path("/user/auth/**")
                .uri("lb://jjmall-user")
                .filters(new AuthorizeGatewayFilter())
                .id("user-route")).build();
     }
    /**
     * 用于管理员封停解封账户
     */

    /**
     * 新增一条路由规则  需要管理员权限  用于管理员管理品类
     * 新定义一个过滤器 区别在于redis取token的前缀不同  //TODO 管理员登录存放token的前缀为admin_token
     */
    @Bean
    public RouteLocator routeLocatorCategory(RouteLocatorBuilder builder){
        return builder.routes().route(r ->
                r.path("/category/manage/**")
                .uri("lb://jjmall-category")
                .filters(new AuthorizeAdminGatewayFilter())
                .id("category-route")).build();
    }





}
