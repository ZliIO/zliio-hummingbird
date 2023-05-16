package com.zliio.hummingbird.web.resolver.impl;

import com.zliio.hummingbird.web.common.entity.RequestParamManager;
import com.zliio.hummingbird.web.resolver.ParameterResolver;

import java.lang.reflect.Parameter;

/**
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class ProtobufBodyParameterResolver implements ParameterResolver {


    @Override
    public Object resolve(Parameter parameter, RequestParamManager requestParamManager) {

        return null;
    }
}
