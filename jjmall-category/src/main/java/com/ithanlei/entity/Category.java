package com.ithanlei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "jjmall_category")    //TODO  待确认  数据库字段重新改
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private Integer categoryId;

    private Integer parentId;

    private String categoryName;
    //数据库中为tinyint  考虑设置为bit类型
    private Integer status;
    //不明白
    private Integer sortOrder;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime creatTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;



}
