package com.zliio.hummingbird.web.serializer.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zliio.hummingbird.web.exception.JsonFormartProcessException;
import com.zliio.hummingbird.web.serializer.Serializer;

import java.io.IOException;


/**
 * Json的序列化器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class JacksonSerializer implements Serializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T serializeToObject(byte[] bytes, Class<T> clazz){
        T object = null;
        try {
            object = objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new JsonFormartProcessException(e);
        }
        return object;
    }

    @Override
    public byte[] serializeToByteArray(Object object){
        byte[] bytes = new byte[0];
        try {
            // Java object to JSON string, default compact-print
            bytes = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            logger.error("JacksonSerializer.serializeToByteArray Error {}",e.getMessage(),e);
        }
        return bytes;
    }
}
