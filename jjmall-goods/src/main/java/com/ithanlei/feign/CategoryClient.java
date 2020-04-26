package com.ithanlei.feign;

import com.ithanlei.constant.CoreConstant;
import com.ithanlei.result.APIResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 调用品类服务
 */
@Component
@FeignClient(value = CoreConstant.CATEGORY_SERVICE, fallback = CategoryClientImpl.class)
public interface CategoryClient {
    @GetMapping("/getCategoryDetail")
    APIResult getCategoryDetail(Integer categoryId);
    //获取全部节点
    @GetMapping("/getDeepCategory.do")
    APIResult getDeepCategory(Integer categoryId);

}
