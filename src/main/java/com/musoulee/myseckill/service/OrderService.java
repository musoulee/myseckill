package com.musoulee.myseckill.service;

import com.musoulee.myseckill.entity.Order;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/30 21:56
 */
public interface OrderService {
    Order create(String userId, String itemId, String itemStockLogId, int amount);
    void createAsync(String userId, String itemId, int amount);
}
