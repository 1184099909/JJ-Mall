package com.ithanlei.mapper;

import com.ithanlei.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Mapper
@Component
public interface UserMapper {
    //测试
    @Select("SELECT * FROM jjmall_user WHERE user_id=#{id}")
    User findAll(Integer id);

    /**
     * 通过用户名查找
     *
     * @param account
     * @return
     */
    @Select("SELECT * FROM jjmall_user WHERE username=#{account}")
    User selectByUsername(@Param("account") String account);
    /**
     * 通过手机号查找
     */
    @Select("SELECT * FROM jjmall_user WHERE phone=#{account}")
    User selectByPhone(@Param("account") String account);
    /**
     * 通过用户id查找
     *
     * @param userId
     * @return
     */
    @Select("SELECT * FROM jjmall_user WHERE user_id=#{userId}")
    User selectByUserId(Integer userId);

    /**
     * 根据用户id  更改最后登录时间
     *
     * @param id
     * @return
     */
    @Update("UPDATE jjmall_user SET last_login_time=SYSDATE() WHERE user_id=#{id}")
    int updateLastLoginTimeById(Integer id);

    /**
     * 新增一名用户
     */
    //TODO creat_time updte_time处理可能有问题
    @Insert("INSERT INTO jjmall_user (user_id,username,password,email,phone,question,answer," +
            "user_type,sex,age,birthday,lock_flag,avatar,nickname,alipay_code,last_login_time," +
            "create_time,update_time) VALUES(#{user_id},#{username},#{password},#{email},#{phone}," +
            "#{question},#{answer},#{user_type},#{sex},#{age},#{birthday},#{lock_flag},#{avatar}," +
            "#{nickname},#{alipay_code},#{last_login_time},SYSDATE(),SYSDATE())")
    int insertUser(User user);

    /**
     * 通过用户名更改用户密码
     */
    @Update("UPDATE jjmall_user SET password=#{newPsd} WHERE username=#{username}")
    int updatePsdByUsername(@Param("username") String username, @Param("newPsd") String newPsd);

    /**
     * 通过用户id更改用户密码
     */
    @Update("UPDATE jjmall_user SET password=#{newPsd} WHERE user_id=#{userId}")
    int updatePsdByUserId(@Param("userId") Integer userId, @Param("newPsd") String newPsd);

    /**
     * 通过用户id修改个人资料
     */
    @Update("UPDATE jjmall_user SET avatar=#{avatar},nickname=#{nickname},sex=#{sex},birthday=#{birthday},phone=#{phone}," +
            "email=#{email} WHERE  user_id=#{userId}")
    int modifyUserInfo(@Param("avatar") String avatar, @Param("nickname") String nickname, @Param("sex") int sex,
                       @Param("birthday") LocalDateTime birthday, @Param("phone")String phone, @Param("email")String email,
                       @Param("userId")int userId);
    /**
     * 删除一条用户记录
     */
    @Delete("DELETE FROM jjmall_user WHERE user_id=#{userId}")
    int deleteUser(@Param("userId") int userId);
    /**
     * 更改用户的锁定状态
     */
    @Update("UPDATE jjmall_user SET lock_flag=#{flag} WHERE username=#{username}")
    int updateLockByUsername(@Param("username") String username, @Param("flag") int flag);


}
