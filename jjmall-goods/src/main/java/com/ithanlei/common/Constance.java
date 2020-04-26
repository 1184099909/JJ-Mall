package com.ithanlei.common;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Constance {
    public static final  String GOODS_REDIS_PREFIX = "goods_";
    /**
     * 排序
     */
    public interface GoodsListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }
    /**
     * 产品的状态
     */
    public interface GoodsStatus{
        int GOODS_ON = 1;
        int GOODS_OFF = 2;
        int GOODS_DELETE = 3;
    }

}
