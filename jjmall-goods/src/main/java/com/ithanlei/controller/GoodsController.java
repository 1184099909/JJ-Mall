package com.ithanlei.controller;

import com.ithanlei.result.APIResult;
import com.ithanlei.service.GoodsService;
import com.ithanlei.utils.Assert;
import com.sun.org.apache.xml.internal.utils.StringToIntTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 买家浏览商品信息
 * 会调用品类服务
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    /**
     * 展示单个商品详细信息
     */
    @GetMapping("/getGoodsDetail.do")
    public APIResult getGoodsDetail(Integer goodsId) {
        Assert.isNull(goodsId);
        return goodsService.getGoodsDetail(goodsId);
    }

    /**
     * 搜索商品分页展示
     */
    @GetMapping("/serachGoodsList")
    public APIResult serachGoodsList(@RequestParam(value = "keyword", required = false) String keyWord,
                                     @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy
    ) {
        return goodsService.serachGoodsList(keyWord, categoryId, pageSize, pageNum, orderBy);
    }

    /**
     * redis中找这个商品  没有就塞值
     * @param goodsId
     * @return
     */
    @GetMapping("/queryGoods.do")
    public APIResult queryGoods(@RequestParam Integer goodsId){
        Assert.isNull(goodsId);
        return goodsService.queryGoods(goodsId);
    }

}
