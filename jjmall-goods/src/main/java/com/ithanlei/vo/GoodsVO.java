package com.ithanlei.vo;

import com.ithanlei.enetity.Goods;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用于展示商品详细信息
 */
@Data
public class GoodsVO {
    //前端需要
    private Integer goodsId;

    private Integer categoryId;

    private String goodsName;

    private String subtitle;

    private String mainImag;

    private String subImages;

    private String detail;

    private BigDecimal price;
    //库存
    private Integer stock;

    private Integer status;

    public static GoodsVO setGoodsVOFromGoods(Goods goods){
        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setGoodsId(goods.getGoodsId());
        goodsVO.setCategoryId(goods.getCategoryId());
        goodsVO.setGoodsName(goods.getGoodsName());
        goodsVO.setSubtitle(goods.getSubtitle());
        goodsVO.setMainImag(goods.getMainImag());
        goodsVO.setSubImages(goods.getSubImages());
        goodsVO.setDetail(goods.getDetail());
        goodsVO.setPrice(goods.getPrice());
        goodsVO.setStock(goods.getStock());
        goodsVO.setStatus(goods.getStatus());
        return goodsVO;
    }


}
