package com.musoulee.myseckill.dao;

import com.musoulee.myseckill.entity.SerialNumber;

public interface SerialNumberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SerialNumber row);

    int insertSelective(SerialNumber row);

    SerialNumber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SerialNumber row);

    int updateByPrimaryKey(SerialNumber row);
}