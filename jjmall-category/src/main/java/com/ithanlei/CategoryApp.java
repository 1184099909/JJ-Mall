package com.ithanlei;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.ithanlei.mapper")
@RefreshScope
public class CategoryApp {
    public static void main(String[] args) {
        SpringApplication.run(CategoryApp.class, args);
        log.info("category service started on 8082...");
    }

}
