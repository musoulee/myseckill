package com.musoulee.myseckill.common;

import com.musoulee.myseckill.controller.UserController;
import com.musoulee.myseckill.entity.Item;
import com.musoulee.myseckill.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @description: 初始化缓存
 * @author: musou
 * @Date: 2022/10/30 21:03
 */
@Component
public class CacheInit {
    private Logger logger = LoggerFactory.getLogger(CacheInit.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemService itemService;

    @PostConstruct
    public void init() {
        List<Item> items = itemService.findItemsOnPromotion();
        // 缓存商品详情
        items.stream().map(
                item -> {
                    item = itemService.getDetailInCache(item.getId());
                    return item;
                }
        ).forEach(item -> logger.info("加载" + item.getId() + "完毕"));
        // 秒杀大闸, 1000个令牌
        redisTemplate.opsForValue().set("promotion:gate", 1000);
        logger.info("秒杀大闸设置完毕");
    }
}
