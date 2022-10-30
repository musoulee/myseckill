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
@RocketMQMessageListener(consumerGroup = "MyConsumerGroup2", topic = "TestTopic", selectorExpression = "tag2")
public class SpringConsumer2 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("Consumer2 receive message: " + message);
    }
}
