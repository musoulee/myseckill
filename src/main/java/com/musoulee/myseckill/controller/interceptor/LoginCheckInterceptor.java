package com.musoulee.myseckill.controller.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.musoulee.myseckill.common.CommonErrorEnum;
import com.musoulee.myseckill.common.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @description: 用户登录状态的拦截器
 * @author: musou
 * @Date: 2022/10/24 23:09
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        // 说明Redis中没有这个key，那么就需要跳转到登录页面
        if (token == null || !redisTemplate.hasKey(token)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            ResponseModel model = ResponseModel.createFailure(CommonErrorEnum.USER_NOT_LOGIN);
            writer.write(JSONObject.toJSONString(model));
            return false;
        }
        return true;
    }
}
