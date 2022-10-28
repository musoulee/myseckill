package com.musoulee.myseckill.common;

/**
 * @description: 通用的返回错误接口
 * @author: musou
 * @Date: 2022/10/24 11:05
 */
public interface CommonError {
    String getCode();
    String getMessage();
    void setMessage(String message);
}
