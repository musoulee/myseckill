package com.musoulee.myseckill.common;

import com.musoulee.myseckill.util.ToolBox;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolboxTest {

    @Test
    public void getRandomUUID() {
        for (int i = 0; i < 10; i++) {
            String uuid = ToolBox.getRandomUUID();
            Assert.assertEquals(uuid.length(), 32);
        }
    }
}