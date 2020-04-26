package com.ithanlei.vo;

import com.ithanlei.enetity.Goods;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 返回商品列表返回这些信息即可
 */
@Data
public class GoodsListVO {
    private Integer goodsId;

    private Integer categoryId;

    private String goodsName;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;

    public static GoodsListVO setGoodsListVOFromGoods(Goods goods){
        GoodsListVO goodsVO = new GoodsListVO();
        goodsVO.setGoodsId(goods.getGoodsId());
        goodsVO.setCategoryId(goods.getCategoryId());
        goodsVO.setGoodsName(goods.getGoodsName());
        goodsVO.setSubtitle(goods.getSubtitle());
        goodsVO.setMainImage(goods.getMainImag());
        goodsVO.setPrice(goods.getPrice());
        goodsVO.setStatus(goods.getStatus());
        return goodsVO;
    }
}
