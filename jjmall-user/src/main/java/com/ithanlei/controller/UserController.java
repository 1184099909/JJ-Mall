package com.ithanlei.controller;

import com.ithanlei.bo.UserBo;
import com.ithanlei.constant.CoreConstant;
import com.ithanlei.entity.User;
import com.ithanlei.result.APIResult;
import com.ithanlei.service.UserService;
import com.ithanlei.utils.Assert;
import com.ithanlei.utils.JwtUtil;
import com.ithanlei.utils.RedisUtil;
import com.ithanlei.vo.UserInfoVO;
import com.ithanlei.vo.UserResVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 密码的加密放到前端进行
 * 有两个接口为管理员操作的
 */
//TODO  前端每次传输密码前先加密
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final String USERNAME_IS_NULL = "用户名为空！";
    private final String PASSWORD_IS_NULL = "密码为空！";
    private final String PHONE_IS_NULL = "手机号为空！";
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登录 检查用户名和密码是否正确  body中取参数  登录失败有详尽提示
     */
    @PostMapping("/login.do")
    public APIResult login(@RequestBody UserBo userBo) {
        Assert.isBlank(userBo.getUsername(), USERNAME_IS_NULL);
        Assert.isBlank(userBo.getPassword(), PASSWORD_IS_NULL);
        APIResult loginResult = userService.login(userBo.getUsername(), userBo.getPassword());
        return loginResult;
    }
    /**
     * 通过手机号登录
     */
    @PostMapping("/loginByPhone.do")
    public APIResult loginByPhone(@RequestBody UserBo userBo) {
        Assert.isNull(userBo.getPhone(), PHONE_IS_NULL);
        Assert.isNull(userBo.getPassword(), PASSWORD_IS_NULL);
        APIResult loginResult = userService.login(userBo.getPhone(), userBo.getPassword());
        return loginResult;
    }

    /**
     * 用户注册接口
     */
    @PostMapping("/register.do")
    public APIResult register(@RequestBody User user) {
        Assert.isNull(user);
        APIResult registerResult = userService.register(user);
        //code不等于200 注册失败
        /*if(!StringUtils.equals(register.getMsg(), CoreConstant.SUCCESS_CODE) ){
            APIResult.failture(CoreConstant.ERR_CODE, CoreConstant.FAILTURE_VALUE);
        }*/
        return registerResult;
    }

    /**
     * 登录状态下根据旧密码修改密码  需要认证 网关会传来一个string的userId
     */
    @PostMapping("/auth/modifyPsd")
    public APIResult modifyPsd(@RequestParam(value = "userId", required = true) String userId, @RequestBody Map<String, String> psdMap) {
        Integer userid = Integer.parseInt(userId);
        String psd = MapUtils.getString(psdMap, "psd");
        String newPsd = MapUtils.getString(psdMap, "newPsd");
        Assert.isBlank(psd, CoreConstant.PARAM_NULL);
        Assert.isBlank(newPsd, CoreConstant.PARAM_NULL);
        return userService.modifyPsd(userid, psd, newPsd);
    }

    /**
     * 用户重置密码  返回给前端密保问题  无需网关auth
     */
    @GetMapping("/resetPsd/getAnswer")
    public APIResult getQuestion(String username) {
        Assert.isBlank(username, CoreConstant.PARAM_NULL);
        return userService.getQuestion(username);
    }

    /**
     * 校验用户提交的密保答案
     */
    @PostMapping("/resetPsd/checkAnswer")
    public APIResult checkAnswer(String username, String question, String answer) {
        Assert.isBlank(username, CoreConstant.PARAM_NULL);
        Assert.isBlank(question, CoreConstant.PARAM_NULL);
        Assert.isBlank(answer, CoreConstant.PARAM_NULL);
        return userService.checkAnswer(username, question, answer);
    }

    /**
     * 接收新密码  修改数据库记录
     */
    @PostMapping("/resetPsd/modifyPsd")
    public APIResult resetModiPsd(String username, String newPsd) {
        Assert.isBlank(username, CoreConstant.PARAM_NULL);
        Assert.isBlank(newPsd, CoreConstant.PARAM_NULL);
        return userService.resetModiPsd(username, newPsd);

    }

    //无需token就能访问的
    @RequestMapping("/notoken")
    public String notoken() {
        String token = JwtUtil.creatJwt(1184099909, "hanlei");
        log.info("生成的token为：{}", token);
        return "这里不需要token";
    }

    //需要token才能访问的
    @RequestMapping("/auth/requiretoken")
    public String requiretoken(@RequestParam(value = "userId", required = true) String userId) {
        return "这里需要token,你认证成功啦,网关给的id：" + userId;
    }


    /**
     * 获取用户所有信息 需要auth  接收网关给的id 用做前端查看详细资料用
     */
    @GetMapping("/auth/getUserInfo")
    public APIResult getUserInfo(String userId) {
        Assert.isBlank(userId, CoreConstant.PARAM_NULL);
        return userService.getUserInfo(userId);
    }

    /**
     * 用户修改个人信息  vo接收json表单信息
     */
    @PostMapping("/auth/modifyUserInfo")
    public APIResult modifyUserInfo(@RequestParam(value = "userId") String userId, @RequestBody UserInfoVO userInfoVO) {
        Assert.isBlank(userId, CoreConstant.PARAM_NULL);
        Assert.isNull(userInfoVO, CoreConstant.PARAM_NULL);
        return userService.modifyUserInfo(userId, userInfoVO);
    }

    /**
     * 退出登录
     */
    @GetMapping("/auth/logout.do")
    public APIResult logout(@RequestParam("userId") String userId) {
        Assert.isBlank(userId, CoreConstant.PARAM_NULL);
        return userService.logout(userId);
    }

    /**
     * 用户永久注销账号
     */
    @GetMapping("/auth/destroyUser.do")
    public APIResult destroyUser(@RequestParam("userId") String userId) {
        Assert.isBlank(userId, CoreConstant.PARAM_NULL);
        return userService.destroyUser(userId);
    }

    /**
     * 管理员封停某账号 需要auth
     */
    @PostMapping("/auth/admin/lockAccount")
    public APIResult lockAccount(String username) {
        Assert.isBlank(username);
        return userService.lockAccount(username);
    }

    /**
     * 管理员解封某账号 需要auth
     */
    @PostMapping("/auth/admin/unLockAccount")
    public APIResult unLockAccount(String username) {
        Assert.isBlank(username);
        return userService.unLockAccount(username);
    }


}
