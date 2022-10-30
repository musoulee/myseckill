package com.musoulee.myseckill.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/29 18:27
 */
@Configuration
public class CacheConfig {
    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) // 写入10分钟后过期
                .initialCapacity(100) // 初始缓存空间大小
                .maximumSize(1000) // 最大缓存数量
                .build();
    }
}
