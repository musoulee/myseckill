package com.musoulee.myseckill.service.impl;

import com.musoulee.myseckill.common.BusinessException;
import com.musoulee.myseckill.dao.ItemMapper;
import com.musoulee.myseckill.dao.PromotionMapper;
import com.musoulee.myseckill.entity.Item;
import com.musoulee.myseckill.entity.Promotion;
import com.musoulee.myseckill.service.PromotionService;
import com.musoulee.myseckill.util.ToolBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/30 17:21
 */
@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 根据传入的id生成令牌
     *
     * @param userId
     * @param itemId
     * @return 如果申请失败，就返回null，否则返回令牌
     */
    @Override
    public String generateToken(String userId, String itemId) {
        // 判断是否售罄
        if (redisTemplate.hasKey("item:stock:over:" + itemId)) {
            return null;
        }

        // 校验商品是否正在促销
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if (item == null || item.getPromotion() == null || item.getItemStock().getStock() == 0) {
            return null;
        }
        // 校验用户是否已经申请过令牌
        String key = "promotion:token:" + userId + ":" + itemId;
        if(redisTemplate.hasKey(key)){
            // 重复申请不可取
            return null;
        }
        // 秒杀大闸
        ValueOperations vos = redisTemplate.opsForValue();
        // 如果令牌数量不够，那就不发了
        if (vos.decrement("promotion:gate", 1) < 0) {
            return null;
        }
        // 绑定当前用户，当前商品，给他们一个令牌
        String token = ToolBox.getRandomUUID();
        // 放到Redis里
        redisTemplate.opsForValue().set(key, token, 10, TimeUnit.MINUTES);
        return token;
    }
}
