package com.ithanlei.fallback;

import com.ithanlei.constant.CoreConstant;
import com.ithanlei.feign.FeignToGateway;
import org.springframework.stereotype.Component;

/**
 * 服务降级会走到这里来
 */
@Component
public class FeignToGatewayFallbackImpl implements FeignToGateway {
    @Override
    public String testrun(){
        return CoreConstant.UNKONW_ERR_MSG;
    }

}
