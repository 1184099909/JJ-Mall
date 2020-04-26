package com.ithanlei.constant;

/**
 * 定义本系统公用的核心常量
 * */
public class CoreConstant {
    public final static String CART_SERVICE = "jjmall-cart";
    public final static String GATEWAY_SERVICE = "jjmall-gateway";
    public final static String GOODS_SERVICE = "jjmall-goods";
    public final static String ORDER_SERVICE = "jjmall-order";
    public final static String USER_SERVICE = "jjmall-user";
    public final static String CATEGORY_SERVICE = "jjmall-category";
    public final static Integer LOCK = 9;
    public final static Integer UNLOCK = 0;
    //成功码  统一200
    public final static String SUCCESS_CODE = "200";
    //笼统错误码
    public final static String ERR_CODE = "500";
    //查找不存在提示码
    public final static String NON_THIS = "114";
    //用户已锁定错误码
    public final static String USER_LOCK = "100";
    //用户名不存在错误码
    public final static String NO_USER = "101";
    //账号密码不匹配错误码
    public final static String NO_MATCH = "102";
    //用户已存在错误码
    public final static String USER_IS_EXITS = "103";
    //未知错误码
    public final static String UNKONW_ERR = "104";
    //超时错误码
    public final static String TIME_OUT_ERR_CODE = "501";
    //网络错误
    public final static String WEB_ERR = "460";
    //传入参数有误
    public final static String PARAM_ERR = "470";
    //数据格式有误
    public final static String DATA_FORMAT_ERR =  "580";
    //没有此信息
    public final static String NONE_MSG =   "591";
    //token不合法
    public final static String TOKEN_ERR = "401";
    /*
    返回值
     */
    public final static String UNKONW_ERR_MSG = "未知错误，请稍后重试！";
    public final static String TIME_OUT_ERR_MSG = "连接超时，请稍后重试 ！";
    public final static  String PARAM_NULL = "参数为空！";
    public final static String SUCCESS_VALUE = "success";
    public final static String FAILTURE_VALUE = "failture";
    //登录过期
    public final static String LOGIN_TIME_OUT = "登录过期，请重新登录！";


}
