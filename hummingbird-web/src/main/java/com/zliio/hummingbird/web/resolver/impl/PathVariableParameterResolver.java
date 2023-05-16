package com.zliio.hummingbird.web.resolver.impl;

import com.zliio.hummingbird.web.annotation.PathVariable;
import com.zliio.hummingbird.web.common.entity.RequestParamManager;
import com.zliio.hummingbird.web.common.util.ObjectUtil;
import com.zliio.hummingbird.web.resolver.ParameterResolver;

import java.lang.reflect.Parameter;

/**
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class PathVariableParameterResolver implements ParameterResolver {
    @Override
    public Object resolve(Parameter parameter, RequestParamManager requestParamManager) {
        PathVariable pathVariable = parameter.getDeclaredAnnotation(PathVariable.class);
        String requestParameterKey = pathVariable.value();
        String requestParameterValue = requestParamManager.getPathVariableParameterMap().get(requestParameterKey);
        // convert the parameter to the specified type
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }
}
