package com.musoulee.myseckill.util;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @description: 工具箱，包括UUID，MD5等等
 * @author: musou
 * @Date: 2022/10/23 19:48
 */
public class ToolBox {
    private static final String MD5_SALT = "自强不息";

    /**
     * 生成随机且唯一的UUID
     *
     * @return
     */
    public static String getRandomUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    /**
     * 两次使用MD5编码
     *
     * @param raw
     * @return
     */
    public static String MD5Encode(String raw) {
        String md5 = encodeByMD5(raw);
        return encodeByMD5(md5);
    }

    /**
     * 使用MD5编码，在原字符串末尾拼上"盐"
     *
     * @param s
     * @return
     */
    private static String encodeByMD5(String s) {
        return DigestUtils.md5DigestAsHex((MD5_SALT + s).getBytes());
    }
}

