package com.zliio.hummingbird.web.resolver.impl;

import com.zliio.hummingbird.web.annotation.RequestBody;
import com.zliio.hummingbird.web.common.entity.RequestParamManager;
import com.zliio.hummingbird.web.factory.SerializerFactory;
import com.zliio.hummingbird.web.resolver.ParameterResolver;
import com.zliio.hummingbird.web.serializer.Serializer;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import java.lang.reflect.Parameter;

/**
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class RequestBodyParameterResolver implements ParameterResolver {


    @Override
    public Object resolve(Parameter parameter, RequestParamManager requestParamManager) {
        String requestJsonBody = requestParamManager.getBodyByteBuf().toString(Charsets.toCharset(CharEncoding.UTF_8));
        if(null != parameter.getAnnotation(RequestBody.class) && null != requestJsonBody && !"".equals(requestJsonBody)){
            Serializer serializer = SerializerFactory.getDefaultSerializer();
            return serializer.serializeToObject(requestJsonBody.getBytes(),parameter.getType());
        }
        return null;
    }
}
