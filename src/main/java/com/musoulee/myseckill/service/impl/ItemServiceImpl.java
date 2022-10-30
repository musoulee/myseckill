package com.musoulee.myseckill.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.musoulee.myseckill.dao.ItemMapper;
import com.musoulee.myseckill.dao.ItemStockLogMapper;
import com.musoulee.myseckill.dao.ItemStockMapper;
import com.musoulee.myseckill.dao.PromotionMapper;
import com.musoulee.myseckill.entity.Item;
import com.musoulee.myseckill.entity.ItemStock;
import com.musoulee.myseckill.entity.ItemStockLog;
import com.musoulee.myseckill.entity.Promotion;
import com.musoulee.myseckill.service.ItemService;
import com.musoulee.myseckill.util.ToolBox;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/27 12:40
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemStockMapper itemStockMapper;
    @Autowired
    private ItemStockLogMapper itemStockLogMapper;
    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Cache<String, Object> caffeineCache;

    /**
     * 添加一个商品，还包括添加库存信息
     *
     * @param item
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int addItem(Item item) {
        // 设置商品的主键
        item.setId(ToolBox.getRandomUUID());
        int row = itemMapper.insert(item);
        // 设置商品库存的外键
        item.getItemStock().setItemId(item.getId());
        addItemStock(item.getItemStock());
        return row;
    }

    @Override
    @Transactional
    public int addItemStock(ItemStock itemStock) {
        int row = itemStockMapper.insert(itemStock);
        return row;
    }

    /**
     * 添加商品活动
     *
     * @param promotion
     * @return
     */
    @Override
    public int addPromotion(Promotion promotion) {
        int row = promotionMapper.insert(promotion);
        return row;
    }

    /**
     * 查询商品列表
     *
     * @return
     */
    @Override
    public List<Item> findItemsOnPromotion() {
        List<Item> items = itemMapper.selectAllOnPromotion();
        // 对每一个商品都添加库存和促销信息
        return items.stream().map(item -> {
                    ItemStock itemStock = itemStockMapper.selectByItemId(item.getId());
                    item.setItemStock(itemStock);
                    Promotion promotion = promotionMapper.selectByItemId(item.getId());
                    item.setPromotion(promotion);
                    return item;
                }
        ).collect(Collectors.toList());
    }

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    @Override
    public Item getDetailByID(String id) {
        // 查出来一个商品
        Item item = itemMapper.selectByPrimaryKey(id);
        // 添加库存信息
        ItemStock itemStock = itemStockMapper.selectByItemId(id);
        item.setItemStock(itemStock);
        // 添加促销活动信息
        Promotion promotion = promotionMapper.selectByItemId(id);
        item.setPromotion(promotion);
        return item;
    }

    @Override
    public ItemStockLog createItemStockLog(String itemId, int amount) {
        ItemStockLog log = new ItemStockLog();
        log.setAmount(amount);
        log.setItemId(itemId);
        log.setStatus(0);
        itemStockLogMapper.insert(log);
        return log;
    }

    @Override
    public ItemStockLog findItemStockLogById(Integer id) {
        return null;
    }

    @Override
    public void updateItemStockStatusById(Integer id, Integer status) {

    }

    /**
     * 在缓存中查找商品
     *
     * @param id
     * @return
     */
    @Override
    public Item getDetailInCache(@Length(min = 32, max = 32, message = "参数不合法") String id) {
        Item item = null;
        // Caffeine
        String key = "item:" + id;
        item = (Item) caffeineCache.getIfPresent(key);
        if (item != null) {
            return item;
        }
        // Redis
        item = (Item) redisTemplate.opsForValue().get(key);
        if (item != null) {
            // 将商品其放入本地缓存中
            caffeineCache.put(key, item);
            return item;
        }
        // MySqL
        item = this.getDetailByID(id);
        if (item != null) {
            // 放到Redis中去，3分钟后失效
//            System.out.println("缓存商品" + item.getId());
            redisTemplate.opsForValue().set(key, item, 3, TimeUnit.MINUTES);
        }
        return item;
    }

    @Override
    public Item selectByPrimaryKey(String itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        return item;
    }

    @Override
    public int insertPromotion(Promotion promotion) {
        int row = promotionMapper.insert(promotion);
        return row;
    }
}
