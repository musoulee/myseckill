package com.musoulee.myseckill.dao;

import com.musoulee.myseckill.entity.ItemStockLog;

public interface ItemStockLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockLog row);

    int insertSelective(ItemStockLog row);

    ItemStockLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStockLog row);

    int updateByPrimaryKey(ItemStockLog row);
}