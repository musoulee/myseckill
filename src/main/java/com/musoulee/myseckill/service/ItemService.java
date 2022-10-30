package com.musoulee.myseckill.service;

import com.musoulee.myseckill.entity.Item;
import com.musoulee.myseckill.entity.ItemStock;
import com.musoulee.myseckill.entity.ItemStockLog;
import com.musoulee.myseckill.entity.Promotion;

import java.util.List;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/27 12:39
 */
public interface ItemService {
    int addItem(Item item);
    int addItemStock(ItemStock itemStock);
    int addPromotion(Promotion promotion);
    List<Item> findItemsOnPromotion();
    Item getDetailByID(String id);
    Item getDetailInCache(String id);
    Item selectByPrimaryKey(String itemId);
    int insertPromotion(Promotion promotion);
    ItemStockLog createItemStockLog(String itemId, int amount);
    ItemStockLog findItemStockLogById(Integer id);
    void updateItemStockStatusById(Integer id, Integer status);
}
