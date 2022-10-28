package com.musoulee.myseckill.service.impl;

import com.musoulee.myseckill.dao.UserMapper;
import com.musoulee.myseckill.entity.User;
import com.musoulee.myseckill.util.ToolBox;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void register() {
        User user = new User();
        user.setId(ToolBox.getRandomUUID());
        user.setPassword(ToolBox.MD5Encode("123456"));
        user.setPhone("18000000000");
        user.setAge(18);
        user.setNickname("musou");
        user.setGender((byte) 1);
        userMapper.insert(user);
    }
}