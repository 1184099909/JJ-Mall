package com.ithanlei.mapper;

import com.ithanlei.enetity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface GoodsMapper {
    @Select("SELECT * FROM jjmall_goods WHERE goods_id=#{goods_id}")
    Goods selectGoodsById(@Param("goods_id") Integer goodsId);
    /**
     * 模糊查询
     */
    @Select("SELECT * FROM jjmall_goods WHERE status=1 AND goods_name LIKE #{goods_name} AND category_id IN #{cateIdList}")
    List<Goods> vagueSelectByNameAndId(@Param("goods_name") String goodsName, @Param("cateIdList") List<Integer> cateIdList);
}
