package com.ithanlei.feign;

import com.ithanlei.constant.CoreConstant;
import com.ithanlei.result.APIResult;

public class CategoryClientImpl implements CategoryClient {
    @Override
    public APIResult getCategoryDetail(Integer cateId){
        return APIResult.failture(CoreConstant.TIME_OUT_ERR_CODE, CoreConstant.TIME_OUT_ERR_MSG);
    }

}
