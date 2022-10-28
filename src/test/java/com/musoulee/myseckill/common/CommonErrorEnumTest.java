package com.musoulee.myseckill.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonErrorEnumTest {
    @Test
    public void testSerialize(){
        CommonError unknow = CommonErrorEnum.UNKNOW_ERROR;
        System.out.println(unknow);
    }

}