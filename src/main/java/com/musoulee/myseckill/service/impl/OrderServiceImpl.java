package com.musoulee.myseckill.service.impl;

import com.musoulee.myseckill.common.BusinessException;
import com.musoulee.myseckill.common.CommonErrorEnum;
import com.musoulee.myseckill.dao.ItemStockLogMapper;
import com.musoulee.myseckill.entity.ItemStockLog;
import com.musoulee.myseckill.entity.Order;
import com.musoulee.myseckill.service.ItemService;
import com.musoulee.myseckill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description: 下单服务实现
 * @author: musou
 * @Date: 2022/10/30 21:58
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemService itemService;
    @Override
    public Order create(String userId, String itemId, String itemStockLogId, int amount) {
        return null;
    }

    @Override
    public void createAsync(String userId, String itemId, int amount) {
        // 查看是否售罄
        String key = "item:stock:over:" + itemId;
        if(redisTemplate.hasKey(key)){
            throw new BusinessException(CommonErrorEnum.STOCK_NOT_ENOUGH);
        }
        // 生成库存流水
        itemService.createItemStockLog(itemId, amount);
    }
}
