package com.ithanlei.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ithanlei.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * User返回给前端的展示对象  仅包含基本信息
 */
//TOOD  确认数据库生日字段类型
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResVO {
    private Integer userId;
    //用户名
    private String username;
    //性别 1-男   0-女
    private Integer sex;
    //生日 入参出参需要格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime birthday;
    //手机号
    private String phone;
    //邮箱
    private String email;
    //用户头像
    private String avatar;
    //用户昵称
    private String nickName;
    //上次登录时间   需要出参格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime preLoginTime;

    /**
     * 接收User 赋值给VO  这些属性必填  //TODO 前端表单设置必填项
     * @param user
     */
    public static UserResVO setVOFromUser(User user){
        UserResVO userResVO = new UserResVO();
        userResVO.setUserId(user.getUserId());
        userResVO.setUsername(user.getUsername());
        userResVO.setAvatar(user.getAvatar());
        userResVO.setNickName(user.getNickName());
        userResVO.setSex(user.getSex());
        userResVO.setBirthday(user.getBirthday());
        userResVO.setPhone(user.getPhone());
        userResVO.setEmail(user.getEmail());
        //设置vo的上次登陆时间为user的最后登录时间
        //如果用户第一次登录
        if(null == user.getLastLoginTime()){
            userResVO.setPreLoginTime(LocalDateTime.now());
        }
        userResVO.setPreLoginTime(user.getLastLoginTime());
        return userResVO;
    }

    public static void main(String[] args){
            System.out.println(LocalDateTime.now());
            Date date = new Date();
            System.out.println(date);

        }

}
