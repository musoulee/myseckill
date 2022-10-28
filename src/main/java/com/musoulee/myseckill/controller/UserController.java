package com.musoulee.myseckill.controller;

import com.alibaba.druid.util.StringUtils;
import com.musoulee.myseckill.common.BusinessException;
import com.musoulee.myseckill.common.CommonErrorEnum;
import com.musoulee.myseckill.common.ResponseModel;
import com.musoulee.myseckill.entity.User;
import com.musoulee.myseckill.service.UserService;
import com.musoulee.myseckill.util.ToolBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * @description: 用户Controller
 * @author: musou
 * @Date: 2022/10/24 08:59
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "${myseckill.web.path}", allowedHeaders = "*", allowCredentials = "true")
@Validated
public class UserController {
    private UserService userService;
    private RedisTemplate redisTemplate;

    // 日志记录器
    @Autowired
    public UserController(UserService userService, RedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 获取验证码的方法
     *
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/otp/{phone}", method = RequestMethod.GET)
    public ResponseModel getOtp(@PathVariable("phone") String phone) {
        // 生成验证码
        String otp = this.generateOtp();
        // 将验证码放入Redis缓存中，并设置验证码五分钟过期
        redisTemplate.opsForValue().set(phone, otp, 5, TimeUnit.MINUTES);
        // 发送验证码
        this.sendOtp(phone, otp);
        // 返回结果
        return ResponseModel.create();
    }

    /**
     * 用户注册方法
     *
     * @param otp  验证码
     * @param user 封装的User对象
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseModel register(String otp,@RequestBody @Validated User user) {
        String realOtp = (String) redisTemplate.opsForValue().get(user.getPhone());
        if (StringUtils.isEmpty(realOtp)) {
            throw new BusinessException(CommonErrorEnum.PARAMETER_ERROR, "验证码已过期！");
        } else if (!StringUtils.equals(otp, realOtp)) {
            throw new BusinessException(CommonErrorEnum.PARAMETER_ERROR, "验证码不正确");
        }
        // 将密码加密处理
        String md5 = ToolBox.MD5Encode(user.getPassword());
        user.setPassword(md5);
        // 生成新的用户ID
        user.setId(ToolBox.getRandomUUID());
        // 用户注册
        userService.register(user);
        return ResponseModel.create();
    }

    /**
     * 用户登录的方法
     *
     * @param phone
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseModel login(@RequestParam("phone") @NotEmpty (message = "手机号不能为空") String phone,
                               @RequestParam("password") @NotEmpty (message = "密码不能为空") String password) {
//        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
//            throw new BusinessException(CommonErrorEnum.PARAMETER_ERROR, "用户名或密码不能为空!");
//        }
        User user = userService.login(phone, ToolBox.MD5Encode(password));
//        logger.info(ToolBox.MD5Encode(password));
        if (user == null) {
            throw new BusinessException(CommonErrorEnum.USER_NOT_EXISTS);
        }
//        logger.info("登录成功！");
        // 这里需要生成一个token放入Redis，以便保存用户的登录状态，并设置token7天过期
        String token = ToolBox.getRandomUUID();
        redisTemplate.opsForValue().set(token, user.getId(), 7, TimeUnit.DAYS);
        return ResponseModel.create();
    }

    /**
     * 随机生成五位数字验证码
     *
     * @return
     */
    private String generateOtp() {
        int code = (int) (Math.random() * 10000) + 10000;
        return String.valueOf(code);
    }

    private void sendOtp(String phone, String otp) {
        String s = String.format("已发送验证码 %s 至 %s ，请注意查收", otp, phone);
        logger.info(s);
    }
}
