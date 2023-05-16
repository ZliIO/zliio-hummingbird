package com.zliio.hummingbird.web.common.http;

/**
 * 消息返回码的统一定义
 *
 * @author ZLiIO
 * @version 1.0
 * @since 1.0 (2023-05-16)
 */
public enum ResultCodeEnum {


    //请求成功,伴随返回值
    SUCCESS(1000),
    //请求成功，没有返回值
    SUCCESS_NO_CONTENT(1001),
    //请求成功，重置内容
    SUCCESS_RESET_CONTENT(1002),

    //客户端请求的语法错误
    BAD_REQUEST(2000),
    //用户名或密码错误
    USERNAME_OR_PASSWORD_ERROR(2002),
    // 账号已经存在
    ACCOUNT_ALWAYS_EXISTS(2004),
    // 未登录
    NOT_LOGIN(2005),
    // 错误的密钥
    ERROR_TOKEN(2006),
    // 账户不存在
    ERROR_ACCOUNT(2007),
    //没有权限
    UNAUTHORIZED(2008),
    //权限变更
    UNAUTHORIZED_CHANGE(2009),
    //图片为空
    IMAGE_IS_EMPTY(2010),
    //参数为空
    PARAMETER_IS_EMPTY(2011),
    // 商品不存在或者兑换结束
    GOODS_NO_FIND_OR_DISABLE(2020),
    // 商品不存在或者兑换结束
    ADDR_NO_FIND_OR_DISABLE(2020),

    // 未知错误
    UNKNOWN(2100),

    NOT_FIND(404);



    private final int code;
    ResultCodeEnum(int code){
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
}
