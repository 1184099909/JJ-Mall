package com.ithanlei.mapper;

import com.ithanlei.entity.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CategoryMapper {
    /**
     * 获取所有的子节点
     * @param categoryId
     * @return
     */
    @Select("SELECT * FROM jjmall_category WHERE parent_id=#{category_id}")
    List<Category> selectCategoryChildrenByParentId(@Param("category_id") Integer categoryId);

    /**
     *根据 id获取品类
     */
    @Select("SELECT * FROM jjmall_category WHERE category_id=#{cateId}")
    Category selectCategoryById(Integer cateId);
    /**
     * 根据name获取品类
     */
    @Select("SELECT * FROM jjmall_category WHERE category_name=#{cateName}")
    Category selectCategoryByName(String cateName);


    @Insert("INSERT INTO jjmall_category (parent_id,category_name,status,sort_order,creat_time,update_time)"+
            " VALUES(#{parentId}, #{categoryName}, #{status},null,SYSDATE(),SYSDATE())")    //TODO sort_order待定
    int insertCategory(@Param("parentId") int parentId, @Param("categoryName")String categoryName, @Param("status")int status);

    /**
     * 删除一个节点
     * @param cateName
     * @return
     */
    @Delete("DELETE FROM jjmall_category WHERE category_name=#{cateName}")
    int deleteCategoryName(String cateName);
    @Update("UPDATE jjmall_category SET category_name=#{newName} WHERE category_name=#{category_name}")
    int updateCategoryName(@Param("category_name") String cateName, @Param("newName") String newName);

}
