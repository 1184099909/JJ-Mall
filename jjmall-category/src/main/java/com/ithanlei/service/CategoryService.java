package com.ithanlei.service;

import com.ithanlei.constant.CoreConstant;
import com.ithanlei.entity.Category;
import com.ithanlei.mapper.CategoryMapper;
import com.ithanlei.result.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    private final String NO_CATEGORY = "该品类不存在!";

    @Autowired
    CategoryMapper categoryMapper;

    public APIResult getAllChildCategory(Integer categoryId) {
        //根据id获取它下面所有的子id
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            return APIResult.failture("该节点下没有任何子节点！");
        }
        return APIResult.success(CoreConstant.SUCCESS_VALUE, categoryList);
    }

    public APIResult addCategory(Integer parentId, String categoryName) {
        //1为正常
        int status = 1;
        int count = categoryMapper.insertCategory(parentId, categoryName, status);
        if (count > 0) {
            return APIResult.success(CoreConstant.SUCCESS_VALUE);
        }
        return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
    }
    /**
     * 删除一个节点
     */
    public APIResult delCategory(String cateName){
        Category category = categoryMapper.selectCategoryByName(cateName);
        if(null == category){
            return APIResult.failture(CoreConstant.ERR_CODE, NO_CATEGORY);
        }
        int count = categoryMapper.deleteCategoryName(cateName);
        if(count > 0){
            return APIResult.success(CoreConstant.SUCCESS_VALUE);
        }
        return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
    }
    /**
     * 修改品类名称
     * @param cateName
     * @param newName
     * @return
     */
    public APIResult updateCateName(String cateName, String newName){
        Category category = categoryMapper.selectCategoryByName(cateName);
        if(null == category){
            return APIResult.failture(CoreConstant.ERR_CODE, "该品类不存在！");
        }
        int count = categoryMapper.updateCategoryName(cateName, newName);
        if(count > 0){
            return APIResult.success(CoreConstant.SUCCESS_VALUE);
        }
        return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
    }

    /**
     * 获取所有节点
     * @param categoryId
     * @return
     */
    public APIResult getCategoryAndDeepChildren(Integer categoryId){
        //如果该节点不存在则不必继续查找
        Category parent = categoryMapper.selectCategoryById(categoryId);
        if(null == parent){
            return APIResult.failture(CoreConstant.ERR_CODE, NO_CATEGORY);
        }
        //递归方法中使用set存储去重
        Set<Category> allCateSet = new HashSet<>();
        findChildCate(allCateSet, parent);
        //将set中的所有品类id取出放在list
        List<Integer> allChilIdList = new ArrayList<>();
        for(Category category : allCateSet){
            allChilIdList.add(category.getCategoryId());
        }
        if(CollectionUtils.isEmpty(allChilIdList)){
            return APIResult.failture(CoreConstant.ERR_CODE, NO_CATEGORY);
        }
        return APIResult.success(CoreConstant.SUCCESS_VALUE, allChilIdList);
    }
    private Set<Category> findChildCate(Set<Category> allCateSet, Category parent){
        //不为空就把自己放进去   是一个停止条件
        if(null != parent){
            allCateSet.add(parent);
        }
        //获取它的所有子节点
        List<Category> allChil = categoryMapper.selectCategoryChildrenByParentId(parent.getCategoryId());
        //遍历接着递归
        for(Category categoryItem : allChil){
            findChildCate(allCateSet, categoryItem);
        }
        return allCateSet;
    }

    /**
     * 获取品类详情
     */
    public APIResult getCategoryDetail(Integer categoryId){
        Category category = categoryMapper.selectCategoryById(categoryId);
        if(null != category){
            return APIResult.success(CoreConstant.SUCCESS_VALUE, category);
        }
        return APIResult.failture(CoreConstant.ERR_CODE, NO_CATEGORY);

    }

}
