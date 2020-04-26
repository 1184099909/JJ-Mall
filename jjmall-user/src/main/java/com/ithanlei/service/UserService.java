package com.ithanlei.service;

import com.ithanlei.constant.CoreConstant;
import com.ithanlei.entity.User;
import com.ithanlei.mapper.UserMapper;
import com.ithanlei.result.APIResult;
import com.ithanlei.util.MD5Util;
import com.ithanlei.utils.JwtUtil;
import com.ithanlei.utils.RedisUtil;
import com.ithanlei.utils.RefexUtil;
import com.ithanlei.vo.UserInfoVO;
import com.ithanlei.vo.UserResVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ithanlei.constant.CoreConstant.LOCK;
import static com.ithanlei.constant.CoreConstant.UNLOCK;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    private final String USER_HASBEEN_LOCKED = "用户已锁定，无法登录！";
    private final String NO_USER = "该用户不存在，请确认您的用户名！";
    private final String NO_MATCH = "账号密码不匹配，请重新输入！";
    private final String USER_IS_EXITS = "该用户已存在。请勿重复注册！";
    private final String CANNOT_MODIFY = "输入密码错误，无法修改,可尝试找回密码";
    /**
     * 不确定是username还是phone 成功则返回该用户对象  失败返回失败信息
     * @param account
     * @param password
     * @return
     */
    public APIResult login(String account, String password){
        User user = null;
        //如果是手机号
        if(RefexUtil.isPhone(account)){
            user = userMapper.selectByPhone(account);
        }
        user = userMapper.selectByUsername(account);
        if(null == user){
            return APIResult.failture(CoreConstant.NO_USER, NO_USER);
        }
        if(9 == user.getLockFlag()){
            return APIResult.failture(CoreConstant.USER_LOCK, USER_HASBEEN_LOCKED);
        }
        if(!user.getPassWord().equals(password)){
            return APIResult.failture(CoreConstant.NO_MATCH, NO_MATCH);
        }
        //登录成功  设置VO的上次登录时间为user最后登录时间
        UserResVO userResVO = UserResVO.setVOFromUser(user);
        // 修改user最后登录时间为当前
        userMapper.updateLastLoginTimeById(user.getUserId());
        //生成token封装返回
        Integer userId = user.getUserId();
        String usernmae = user.getUsername();
        String token = JwtUtil.creatJwt(userId, usernmae);
        //将token存入redis并设置有效期  key为id+token  有效期7天
        redisUtil.setKey(userId.toString() + "_token", token, 60 * 60 * 24 * 7);
        Map<String, Object> userAndToken = new HashMap<>();
        userAndToken.put("user", userResVO);
        userAndToken.put("token", token);
        return APIResult.success(CoreConstant.SUCCESS_VALUE, userAndToken);
    }

    /**
     * 注册
     * @param regisUser
     * @return
     */
    public APIResult register(User regisUser){
        User user = userMapper.selectByUsername(regisUser.getUsername());
        //用户已存在 /判断用户的角色类型，类型相同才注册失败
        if(null != user && regisUser.getUserType() == user.getUserType()){
            return APIResult.failture(CoreConstant.USER_IS_EXITS,USER_IS_EXITS);
            }
            //可以注册   写入数据库  不需要返回给前端对象
        try {
            userMapper.insertUser(regisUser);
        } catch (Exception e) {
            return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
        }
        return  APIResult.success(CoreConstant.SUCCESS_VALUE,null);
        }
    /**
     * 退出登录  删除redis中的对应token即可
      */
    public APIResult logout(String userId){
        //操作redis
        String key = userId + "_token";
        try {
            redisUtil.delKey(key);
        } catch (Exception e) {
            log.info(e.getMessage());
            return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
        }
        return APIResult.success(CoreConstant.SUCCESS_CODE, null);
    }

    /**
     * 登录状态下修改密码   接收id 旧密码 新密码
     */
    public APIResult modifyPsd(Integer userId, String psd, String newPsd){
        User user = userMapper.selectByUserId(userId);
        String oldPsd = user.getPassWord();
        //校验旧密码
        if(!oldPsd.equals(psd)){
            return APIResult.failture(CoreConstant.NO_MATCH, CANNOT_MODIFY);
        }
        //可以修改
        userMapper.updatePsdByUserId(userId, newPsd);
        return APIResult.success(CoreConstant.SUCCESS_VALUE, null);
        }



    /**
     * 忘记密码无法登录使用密保重置密码 返回给密保问题 校验完成前端生成一个flag然后跳转到输入新密码页面
     */
    public APIResult getQuestion(String username){
        User user = userMapper.selectByUsername(username);
        if(user == null){
            return APIResult.failture(CoreConstant.NO_USER, NO_USER);
        }
        String ques = user.getQuestion();
        if(StringUtils.isNotBlank(ques)){
            return APIResult.success(CoreConstant.SUCCESS_VALUE, ques);
        }
        return  APIResult.failture(CoreConstant.ERR_CODE, "您没有设置密保问题！");
    }
    /**
     * 校验密保答案
     */
    public APIResult checkAnswer(String username, String question, String answer){
        String answ = userMapper.selectByUsername(username).getAnswer();
        if(StringUtils.isNoneBlank(answ) && answer.equals(answ)){
            return  APIResult.success(CoreConstant.SUCCESS_VALUE, null);
        }
        return APIResult.failture(CoreConstant.ERR_CODE, "校验密保答案错误！");
    }

    /**
     * 接收新密码  修改数据库记录   接收前端生成的允许修改密码flag
     */
    public APIResult resetModiPsd(String username, String newPsd){
        try {
            userMapper.updatePsdByUsername(username, newPsd);
        } catch (Exception e) {
            return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
        }
        return APIResult.success(CoreConstant.SUCCESS_VALUE, newPsd);
    }
    /**
     * 获取用户资料详情页
     */
    public APIResult getUserInfo(String userid){
        int userId = Integer.parseInt(userid);
        try {
            User user = userMapper.selectByUserId(userId);
            UserInfoVO userInfoVO = UserInfoVO.setVOFromUser(user);
        } catch (Exception e) {
            return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
        }
        return APIResult.success(CoreConstant.SUCCESS_CODE, CoreConstant.SUCCESS_VALUE);
    }

    /**
     * 修改用户个人信息
     */
    public APIResult modifyUserInfo(String userid, UserInfoVO userInfoVO){
        try {
            Integer userId = Integer.parseInt(userid);
            String avatar = userInfoVO.getAvatar();
            String nickname = userInfoVO.getNickname();
            Integer sex = userInfoVO.getSex();
            LocalDateTime birthday = userInfoVO.getBirthday();
            String email = userInfoVO.getEmail();
            String phone = userInfoVO.getPhone();
            userMapper.modifyUserInfo(avatar, nickname, sex, birthday, email, phone, userId);
        } catch (NumberFormatException e) {
            log.info(e.getMessage());
            return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
        }
        return APIResult.success(CoreConstant.SUCCESS_CODE, CoreConstant.SUCCESS_VALUE);
    }

    /**
     * 注销账号
     */
    public APIResult destroyUser(String userid){
        int userId = Integer.parseInt(userid);
        try {
            userMapper.deleteUser(userId);
        } catch (Exception e) {
            log.info(e.getMessage());
            return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
        }
        return APIResult.success(CoreConstant.SUCCESS_CODE, CoreConstant.SUCCESS_VALUE);
    }
    /**
     * 锁定用户 为管理员权限
     */
    public APIResult lockAccount(String username){
        User user = userMapper.selectByUsername(username);
        int flag = user.getLockFlag();
        if(LOCK == flag){
            return APIResult.failture("此用户已锁定！");
        }
        try {
            userMapper.updateLockByUsername(username, LOCK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
        }
        return APIResult.success(CoreConstant.SUCCESS_CODE);
    }
    /**
     * 解封用户 为管理员权限
     */
    public APIResult unLockAccount(String username){
        User user = userMapper.selectByUsername(username);
        int flag = user.getLockFlag();
        if(CoreConstant.UNLOCK == flag){
            return APIResult.failture("此用户未封停！");
        }
        try {
            userMapper.updateLockByUsername(username, UNLOCK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return APIResult.failture(CoreConstant.UNKONW_ERR, CoreConstant.UNKONW_ERR_MSG);
        }
        return APIResult.success(CoreConstant.SUCCESS_CODE);
    }


}



