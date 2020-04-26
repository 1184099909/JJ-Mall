package com.ithanlei.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
//TODO 适当增加密码字段的长度
/**
 * 用户实体类
 * 本系统用户名不允许重复
 */
@Table(name = "jjmall_user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
    //用户id
    @Id
    private Integer userId;
    //用户名
    private String username;
    //登录密码
    private String passWord;
    //用户角色类型   1-买家  2-卖家  3-都是
    private Integer userType;
    //商家收款二维码  只有当用户角色为商家时此项有效
    private String alipayCode;
    //性别 1-男   0-女
    private Integer sex;
    //生日  需要入参格式化  传来的为json格式  //TODO 前端将表单格式化为相应字符串
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime birthday;
    //手机号
    private String phone;
    //邮箱
    @Email
    private String email;
    //密保问题
    private String question;
    //密保答案
    private String answer;
    //用户头像
    private String avatar;
    //用户状态  0-正常   9-锁定  默认0
    private int lockFlag;
    //用户昵称
    private String nickName;
    //最后登录时间
    private LocalDateTime lastLoginTime;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime creatTime;
    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}

