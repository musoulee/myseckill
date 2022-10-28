package com.musoulee.myseckill.config;

import com.musoulee.myseckill.common.FastJsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @description: Redis的配置文件
 * @author: musou
 * @Date: 2022/10/24 12:25
 */
@Configuration
public class RedisConfig {
    /**
     * 重新写这个方法，生成一个名为"redisTemplate"的Bean，来代替默认的配置
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(factory);
        // 设置key的序列化器
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置value的序列化器
        FastJsonSerializer fastJsonSerializer = new FastJsonSerializer();
        template.setValueSerializer(fastJsonSerializer);
        template.setHashValueSerializer(fastJsonSerializer);
        // 调用一些后置方法
        template.afterPropertiesSet();
        return template;
    }
}
