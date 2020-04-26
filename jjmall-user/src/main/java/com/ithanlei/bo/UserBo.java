package com.ithanlei.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于接收前端传来的user数据
 */
@Data
@NoArgsConstructor
public class UserBo {
    private String phone;
    private String username;
    private String password;

}
