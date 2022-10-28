package com.musoulee.myseckill.common;


import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @description:
 * @author: musou
 * @Date: 2022/10/24 11:06
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonErrorEnum implements CommonError {
    // 错误码由三位组成，第一位标识类型，后两位标识子类型
    // 000 未知错误及通用错误
    UNKNOW_ERROR("000", "未知错误"),
    PARAMETER_ERROR("001", "参数错误"),
    // 100 用户错误
    USER_NOT_LOGIN("101", "用户未登录"),
    USER_NOT_EXISTS("102", "用户名或密码错误"),

    // 200 业务错误
    STOCK_NOT_ENOUGH("201", "商品库存不足"),
    STOCK_NOT_EXISTS("202", "该商品不存在"),
    ITEM_ADD_FAIL("203", "商品添加失败"),
    PROMOTION_ADD_FAIL("204", "活动添加失败"),
    ;
    private String code;
    private String message;

    private CommonErrorEnum(String code, String messge) {
        this.code = code;
        this.message = messge;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
