package com.ithanlei.controller;

import com.ithanlei.entity.User;
import com.ithanlei.feign.FeignToGateway;
import com.ithanlei.service.TestService;
import com.ithanlei.utils.Assert;
import com.ithanlei.vo.UserResVO;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class TestController {
    @Autowired
    private TestService testService;
    //测试feign
    @Autowired
    private FeignToGateway feignToGateway;
    @GetMapping("/feignToGateway")
    public String feignToGateway(){
        return feignToGateway.testrun();
    }
    @RequestMapping("/test")
    public User testSelect(Integer id){
        Assert.isNull(id, "id为空");
        return testService.testSelect(id);
    }
    @PostMapping("/testDate")
    //测试date类型的出入参
    public UserResVO testDate(@RequestBody User user){
        //是否正常接收参数
        System.out.println(user.getBirthday());
        UserResVO userResVO = new UserResVO();
        userResVO.setPreLoginTime(LocalDateTime.now());
        return userResVO;
    }
}
