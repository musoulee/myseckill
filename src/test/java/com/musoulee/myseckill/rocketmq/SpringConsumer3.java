package com.musoulee.myseckill.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/30 23:34
 */
@Component
@RocketMQMessageListener(consumerGroup = "MyConsumerGroup3", topic = "TestTopic", selectorExpression = "tag3")
public class SpringConsumer3 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("Consumer3 receive message: " + message);
    }
}
