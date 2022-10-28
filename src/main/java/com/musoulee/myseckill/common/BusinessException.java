package com.musoulee.myseckill.common;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/24 12:20
 */
public class BusinessException extends RuntimeException implements CommonError {
    private String code;
    private String message;

    public BusinessException(CommonError error) {
        super();
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BusinessException(CommonError error, String message) {
        this(error);
        this.message = message;
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
