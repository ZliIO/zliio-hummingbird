package com.zliio.hummingbird.web.factory;

import com.zliio.hummingbird.web.common.lang.HttpMediaType;
import com.zliio.hummingbird.web.serializer.Serializer;
import com.zliio.hummingbird.web.serializer.impl.JacksonSerializer;
import com.zliio.hummingbird.web.serializer.impl.ProtobufSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器处理工厂
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class SerializerFactory {
    private static final Map<String, Serializer> SERIALIZER_MAP = new HashMap<>(8);
    static {
        SERIALIZER_MAP.put(HttpMediaType.APPLICATION_JSON_UTF8_VALUE,new JacksonSerializer());
        SERIALIZER_MAP.put(HttpMediaType.APPLICATION_JSON_VALUE,new JacksonSerializer());
        SERIALIZER_MAP.put(HttpMediaType.APPLICATION_PROTOBUF_BASE64_UTF8_VALUE,new ProtobufSerializer());
        SERIALIZER_MAP.put(HttpMediaType.APPLICATION_PROTOBUF_BASE64_VALUE,new ProtobufSerializer());
    }

    public static Serializer getSerializerWithContentType(String[] contentTypes){
        for (String contentType: contentTypes) {
            if(SERIALIZER_MAP.containsKey(contentType)){
                return SERIALIZER_MAP.get(contentType);
            }
        }
        return getDefaultSerializer();
    }

    public static Serializer getDefaultSerializer(){
        return SERIALIZER_MAP.get(HttpMediaType.APPLICATION_JSON_VALUE);
    }
}
