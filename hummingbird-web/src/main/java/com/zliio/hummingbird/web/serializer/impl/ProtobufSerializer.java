package com.zliio.hummingbird.web.serializer.impl;

import com.zliio.hummingbird.web.serializer.Serializer;

/**
 * Protobuf的序列化器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class ProtobufSerializer implements Serializer {
    @Override
    public <T> T serializeToObject(byte[] bytes, Class<T> clazz) {
        return null;
    }

    @Override
    public byte[] serializeToByteArray(Object object) {
        return new byte[0];
    }
}
