package com.ithanlei.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ithanlei.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 展示用户资料详情页
 */
@Data
public class UserInfoVO {

    private String username;
    private String nickname;
    private Integer sex;
    //生日   入参出参需要格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime birthday;
    //手机号
    private String phone;
    //邮箱
    private String email;
    //用户头像
    private String avatar;
    /**
     * 接收user，复制给vo
     */
    public static UserInfoVO setVOFromUser(User user){
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setAvatar(user.getAvatar());
        userInfoVO.setNickname(user.getNickName());
        userInfoVO.setSex(user.getSex());
        userInfoVO.setBirthday(user.getBirthday());
        userInfoVO.setPhone(user.getPhone());
        userInfoVO.setEmail(user.getEmail());
        return userInfoVO;
    }




}
