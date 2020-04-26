package com.ithanlei.controller;

import com.ithanlei.entity.Category;
import com.ithanlei.result.APIResult;
import com.ithanlei.service.CategoryService;
import com.ithanlei.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 品类操作相关
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    //test
    @RequestMapping("/testCate")
    public String testCate(){
        return "category_service is running";
    }


    /**
     * 获取品类的单个子节点   树的一层
     */
    @GetMapping("/getAllChildCategory.do")
    public APIResult getAllChildCategory(@RequestParam(defaultValue = "0") Integer categoryId){
        Assert.isNull(categoryId);
        return categoryService.getAllChildCategory(categoryId);
    }

    /**
     *供feign调用
     * 递归获取自身和所有的子节点  不止一层
     */
    @GetMapping("/getDeepCategory.do")
    public APIResult getDeepCategory(Integer categoryId){
        Assert.isNull(categoryId);
        return categoryService.getCategoryAndDeepChildren(categoryId);
    }

    /**
     * 获取品类详情
     */
    @GetMapping("/getCategoryDetail")
    public APIResult getCategoryDetail(Integer categoryId){
        Assert.isNull(categoryId);
        return categoryService.getCategoryDetail(categoryId);
    }
    //----------以下操作需要管理员权限---------
    /**
     * 增加一个节点
     */
    @PostMapping("/manage/addCategory.do")
    public APIResult addCategory(@RequestBody Category category){
        Integer parentId = category.getParentId();
        String categoryName = category.getCategoryName();
        Assert.isNull(parentId);
        Assert.isBlank(categoryName);
        return categoryService.addCategory(parentId, categoryName);
    }
    /**
     * 删除一个节点
     */
    @PostMapping("/manage/addCategory.do")
    public APIResult delCategory(String cateName){
        Assert.isBlank(cateName);
        return categoryService.delCategory(cateName);
    }
    /**
     * 修改品类名称
     */
    @PostMapping("/manage/updateCateName")
    public APIResult updateCateName(String cateName, String newName){
        Assert.isBlank(cateName);
        Assert.isBlank(newName);
        return categoryService.updateCateName(cateName, newName);
    }

}
