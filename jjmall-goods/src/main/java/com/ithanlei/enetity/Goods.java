package com.ithanlei.enetity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jjmall_goods")
//TODO 数据库需要修改
public class Goods {
    private Integer goodsId;

    private Integer categoryId;
    //一个卖家对应多个商品
    private Integer userId;

    private String goodsName;

    private String subtitle;

    private String mainImag;

    private String subImages;

    private String detail;

    private BigDecimal price;
    //库存
    private Integer stock;
    //状态  1正常   2下架  3删除
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime creatTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;



}
