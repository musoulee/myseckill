package com.musoulee.myseckill.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/24 13:10
 */
public class FastJsonSerializer implements RedisSerializer<Object> {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 序列化
     *
     * @param o
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return null;
        }
        String json = JSON.toJSONString(o, SerializerFeature.WriteClassName);
        return json.getBytes(DEFAULT_CHARSET);
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     * @throws SerializationException
     */
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, Object.class, Feature.SupportAutoType);
    }
}
