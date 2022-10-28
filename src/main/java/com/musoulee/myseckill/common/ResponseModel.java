package com.musoulee.myseckill.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/24 11:02
 */
public class ResponseModel<T> {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    private String status;
    private T data;

    /**
     * 生成一个空的返回对象
     */
    private ResponseModel() {
        this.status = SUCCESS;
    }

    private ResponseModel(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static ResponseModel create() {
        return new ResponseModel();
    }

    /**
     * 创建正确的返回对象
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseModel createSuccess(T data) {
        return new ResponseModel(SUCCESS, data);
    }

    /**
     * 创建错误的返回对象
     *
     * @param data
     * @return
     */
    public static <T> ResponseModel createFailure(T data) {
        return new ResponseModel(FAILURE, data);
    }

    public static ResponseModel createFailure(CommonError error, String message) {
        error.setMessage(message);
        return ResponseModel.createFailure(error);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
