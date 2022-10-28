package com.musoulee.myseckill.service.impl;

import com.musoulee.myseckill.dao.UserMapper;
import com.musoulee.myseckill.entity.User;
import com.musoulee.myseckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/24 14:14
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        int result = userMapper.insert(user);
        return result == 1;
    }

    @Override
    public User login(String phone, String password) {
        User user = userMapper.selectByPhone(phone, password);
        return user;
    }
}
