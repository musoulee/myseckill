package com.musoulee.myseckill.common;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.alibaba.druid.util.StringUtils;
import org.apache.ibatis.binding.BindingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MethodInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: 全局异常处理器
 * @author: musou
 * @Date: 2022/10/26 17:07
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理用户自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseModel handleBusinessException(BusinessException e) {
        Map<String, String> data = new HashMap<>();
        data.put("code", e.getCode());
        data.put("message", e.getMessage());
        return ResponseModel.createFailure(data);
    }

    /**
     * 直接参数校验
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseModel handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        String message = CommonErrorEnum.PARAMETER_ERROR.getMessage();
        for (ConstraintViolation<?> cv : cvs) {
            message = cv.getMessage();
            if (!StringUtils.isEmpty(message)) {
                break;
            }
        }
        return ResponseModel.createFailure(CommonErrorEnum.PARAMETER_ERROR, message);
    }

    /**
     * 处理实体类校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseModel handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = CommonErrorEnum.UNKNOW_ERROR.getMessage();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            message = fieldError.getField();
            if (!StringUtils.isEmpty(message)) {
//                message += ":";
                message = fieldError.getDefaultMessage();
                break;
            }
        }
        return ResponseModel.createFailure(CommonErrorEnum.PARAMETER_ERROR, message);
    }
//
//    /**
//     * 处理参数绑定异常
//     *
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(BindingException.class)
//    @ResponseBody
//    public ResponseModel handleBindingException(BindingException e) {
//
//        return ResponseModel.createFailure(CommonErrorEnum.PARAMETER_ERROR, e.getMessage());
//    }

}
