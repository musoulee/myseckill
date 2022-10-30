package com.musoulee.myseckill.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/30 23:32
 */
@Component
public class SpringProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    // 消息发送方法
    public void sendMessage(String topic, String message) {
        for (int i = 0; i < 10; i++) {
            String destination = topic + ":tag" + (i % 3 + 1);

            Message build = MessageBuilder.withPayload(message + i).build();
            rocketMQTemplate.asyncSend(destination, build, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("SUCCESS: " + sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("FAILURE: " + throwable.getMessage());
                }
            }, 3000);
        }
    }
}
