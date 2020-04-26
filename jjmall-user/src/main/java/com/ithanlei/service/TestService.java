package com.ithanlei.service;

import com.ithanlei.mapper.UserMapper;
import com.ithanlei.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 测试数据库是否连通
 *
 */
@Service
public class TestService {
    @Autowired
    private UserMapper userMapper;

    public User testSelect(Integer id){
        return userMapper.findAll(id);
    }
}
