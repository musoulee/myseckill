package com.musoulee.myseckill.dao;

import com.musoulee.myseckill.entity.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Order row);

    int insertSelective(Order row);

    Order selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Order row);

    int updateByPrimaryKey(Order row);
}