package com.musoulee.myseckill.rocketmq;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/30 23:38
 */
@Controller
@RequestMapping("/mqtest")
public class SpringController {
    @Resource
    private SpringProducer springProducer;

    @GetMapping("/sendmessage")
    public String sendMessage(String message) {
        springProducer.sendMessage("TestTopic", message);
        return "消息发送成功";
    }
}
