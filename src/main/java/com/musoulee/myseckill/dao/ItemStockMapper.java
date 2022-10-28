package com.musoulee.myseckill.dao;

import com.musoulee.myseckill.entity.ItemStock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStock row);

    int insertSelective(ItemStock row);

    ItemStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStock row);

    int updateByPrimaryKey(ItemStock row);

    ItemStock selectByItemId(String id);
}