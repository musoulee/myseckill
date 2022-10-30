package com.musoulee.myseckill.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.common.util.concurrent.RateLimiter;
import com.musoulee.myseckill.common.BusinessException;
import com.musoulee.myseckill.common.CommonErrorEnum;
import com.musoulee.myseckill.common.ResponseModel;
import com.musoulee.myseckill.entity.User;
import com.musoulee.myseckill.service.OrderService;
import com.musoulee.myseckill.service.PromotionService;
import com.pig4cloud.captcha.SpecCaptcha;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


/**
 * @description: 订单Controller
 * @author: musou
 * @Date: 2022/10/24 08:59
 */
@Controller
@RequestMapping("/order")
@CrossOrigin(origins = "${myseckill.web.path}", allowedHeaders = "*", allowCredentials = "true")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    // 限制每秒最多处理10个请求
    private RateLimiter rateLimiter = RateLimiter.create(10);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 获取验证码
     *
     * @param token 用户的登录凭证
     */
    @ResponseBody
    @RequestMapping(path = "/captcha", method = RequestMethod.GET)
    public ResponseModel getCaptcha(String token) {
        // 新生成验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 50);
        // 将验证码和用户绑定，并存入Redis中，大小写不敏感
        String key = "captcha:" + token;
        redisTemplate.opsForValue().set(key, specCaptcha.text().toLowerCase(), 5, TimeUnit.MINUTES);
        // 将验证码写给前端
        return ResponseModel.createSuccess(specCaptcha.toBase64());
    }

    /**
     * 生成令牌
     *
     * @param token   用户的登录凭证
     * @param itemId  商品ID
     * @param captcha 验证码
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/token", method = RequestMethod.POST)
    public ResponseModel generateToken(String token,
                                       String itemId,
                                       @NotEmpty(message = "验证码不能为空") String captcha) {
        if (!rateLimiter.tryAcquire()) {
            throw new BusinessException(CommonErrorEnum.SERVER_BUSY);
        }
        String key = "captcha:" + token;
        String realCaptcha = (String) redisTemplate.opsForValue().get(key);
        if (!captcha.toLowerCase().equals(realCaptcha)) {
            throw new BusinessException(CommonErrorEnum.PARAMETER_ERROR, "验证码不正确");
        }
        // 查找用户
        User user = (User) redisTemplate.opsForValue().get(token);
        // 请求令牌
        String promotionToken = promotionService.generateToken(user.getId(), itemId);
        if (StringUtils.isEmpty(promotionToken)) {
            throw new BusinessException(CommonErrorEnum.CREATE_ORDER_FAIL);
        }
        return ResponseModel.createSuccess(promotionToken);
    }

    /**
     * 下单
     *
     * @param token          用户登录凭证
     * @param itemId         商品ID
     * @param promotionToken 给用户发放的令牌
     * @param amount         下单购买数量
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseModel create(String token, @NotEmpty String itemId, String promotionToken, @Min(1) int amount) {
        if (!rateLimiter.tryAcquire()) {
            throw new BusinessException(CommonErrorEnum.SERVER_BUSY);
        }
        User user = (User) redisTemplate.opsForValue().get(token);
        // 判断当前用户有无令牌
        String key = "promotion:token:" + user.getId() + ":" + itemId;
        String realPromotionToken = (String) redisTemplate.opsForValue().get(key);
        // 如果没有令牌或者令牌不一致，那么就下单失败
        if (realPromotionToken == null || !realPromotionToken.equals(promotionToken)) {
            throw new BusinessException(CommonErrorEnum.CREATE_ORDER_FAIL);
        }
        // 线程池中取出一个线程异步执行下订单的方法
        Future future = taskExecutor.submit(() -> {
            orderService.createAsync(user.getId(), itemId, amount);
            return null;
        });
        try {
            future.get();
        } catch (Exception e) {
            throw new BusinessException(CommonErrorEnum.CREATE_ORDER_FAIL);
        }
        return ResponseModel.create();
    }
}

