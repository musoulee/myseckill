package com.musoulee.myseckill.dao;

import com.musoulee.myseckill.entity.Promotion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Promotion row);

    int insertSelective(Promotion row);

    Promotion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Promotion row);

    int updateByPrimaryKey(Promotion row);

    Promotion selectByItemId(String id);
}