package com.musoulee.myseckill.dao;

import com.musoulee.myseckill.entity.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(Item row);

    int insertSelective(Item row);

    Item selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Item row);

    int updateByPrimaryKey(Item row);

    List<Item> selectAllOnPromotion();
}