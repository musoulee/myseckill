package com.musoulee.myseckill.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/24 12:26
 */
public class RedisUtils {
    @Autowired
    private static RedisTemplate<String, Object> redisTemplate;

    /**
     * 向Redis中放入一个键-值对
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }
}
