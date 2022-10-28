package com.musoulee.myseckill.service;

import com.musoulee.myseckill.entity.User;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/24 14:13
 */
public interface UserService {
    boolean register(User user);

    User login(String phone, String password);
}
