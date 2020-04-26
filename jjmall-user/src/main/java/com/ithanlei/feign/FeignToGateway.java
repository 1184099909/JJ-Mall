package com.ithanlei.feign;

import com.ithanlei.constant.CoreConstant;
import com.ithanlei.fallback.FeignToGatewayFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = CoreConstant.GATEWAY_SERVICE, fallback = FeignToGatewayFallbackImpl.class)
public interface FeignToGateway {
    @RequestMapping("/testrun")
    String testrun();
}




