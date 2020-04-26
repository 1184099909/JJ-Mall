package com.ithanlei.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ithanlei.common.Constance;
import com.ithanlei.entity.Category;
import com.ithanlei.feign.CategoryClient;
import com.ithanlei.constant.CoreConstant;
import com.ithanlei.enetity.Goods;
import com.ithanlei.mapper.GoodsMapper;
import com.ithanlei.result.APIResult;
import com.ithanlei.utils.JsonUtil;
import com.ithanlei.utils.RedisUtil;
import com.ithanlei.vo.GoodsListVO;
import com.ithanlei.vo.GoodsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GoodsService {
    private final String NO_GOODS = "此商品不存在";
    private final String NO_GOODS_LIST = "没有查询到结果，请更换搜索条件继续查找！";
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CategoryClient categoryClient;
    //redis方法为非静态的
    @Autowired
    RedisUtil redisUtil;

    public APIResult getGoodsDetail(Integer goodsId){
         Goods goods = goodsMapper.selectGoodsById(goodsId);
        if(null == goods){
            return APIResult.failture(CoreConstant.NON_THIS, NO_GOODS);
        }
        //返回VO
        GoodsVO goodsVO = GoodsVO.setGoodsVOFromGoods(goods);
        return APIResult.success(goodsVO);
    }

    public APIResult serachGoodsList(String keyword, Integer cateId, int pageSize, int pageNum, String orderBy){
        //存放供模糊查询的品类id
        List<Integer> cateIdList = Lists.newArrayList();
        if(null != cateId){
            //调用品类服务获取品类详情
            APIResult result = categoryClient.getCategoryDetail(cateId);
//            Category category = (Category) result.getData();
            //还是将结果序列化处理
            Object obj = result.getData();
            String str = JsonUtil.obj2str(obj);
            Category category = JsonUtil.str2obj(str, Category.class);
            if(null == category && StringUtils.isEmpty(keyword)){
                //直接返回空页
                PageHelper.startPage(pageNum, pageSize);
                List<GoodsListVO> goodsVOlist = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(goodsVOlist);
                return APIResult.success(pageInfo);
            }
            //品类存在   获取全部子节点
            APIResult deepResult = categoryClient.getDeepCategory(cateId);
            cateIdList = (List<Integer>) deepResult.getData();
        }
        //如果keyword不为空
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        //如果orderBy不为空
        if(StringUtils.isNotBlank(orderBy)){
            if(Constance.GoodsListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                //"_"转为" "
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        //拿到pagehelper  模糊查询
        List<Goods> goodsList = goodsMapper.vagueSelectByNameAndId(StringUtils.isEmpty(keyword)?null:keyword,
                CollectionUtils.isEmpty(cateIdList)?null:cateIdList);
        //查询完毕 封装返回对象
        if(!CollectionUtils.isEmpty(goodsList)) {
            List<GoodsListVO> goodsListVOList = Lists.newArrayList();
            for(Goods goods : goodsList){
                GoodsListVO goodsListVO = GoodsListVO.setGoodsListVOFromGoods(goods);
                goodsListVOList.add(goodsListVO);
            }
            //返回
            PageInfo pageInfo = new PageInfo(goodsList);
            pageInfo.setList(goodsListVOList);
            return APIResult.success(pageInfo);
        }
        //没有查询到相应列表
        return APIResult.failture(CoreConstant.NO_MATCH, NO_GOODS_LIST);
    }

    public APIResult queryGoods(Integer goodsId){
        //去redis中查询  不存在则加入
        String redisKey = Constance.GOODS_REDIS_PREFIX + goodsId.toString();
        String redisGoods = redisUtil.getKey(redisKey);
        if(StringUtils.isBlank(redisGoods)){
            Goods goods = goodsMapper.selectGoodsById(goodsId);
            //判断商品状态
            if(null == goods){
                return APIResult.failture(CoreConstant.NON_THIS, NO_GOODS);
            }
            if(Constance.GoodsStatus.GOODS_ON != goods.getStatus()){
                return APIResult.failture(CoreConstant.ERR_CODE, "商品已下架或删除！");
            }
            String goodsStr = JsonUtil.obj2str(goods);
            //设置为30day
            redisUtil.setKey(redisKey, goodsStr, 30, TimeUnit.DAYS);
        }
        //取出返回
        Goods goods = JsonUtil.str2obj(redisGoods, Goods.class);
        return APIResult.success(goods);

    }


}
