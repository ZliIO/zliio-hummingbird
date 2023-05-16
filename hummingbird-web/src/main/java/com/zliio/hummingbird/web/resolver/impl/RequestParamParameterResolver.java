package com.zliio.hummingbird.web.resolver.impl;

import com.zliio.hummingbird.web.annotation.RequestParam;
import com.zliio.hummingbird.web.common.entity.RequestParamManager;
import com.zliio.hummingbird.web.common.util.ObjectUtil;
import com.zliio.hummingbird.web.resolver.ParameterResolver;

import java.lang.reflect.Parameter;

/**
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class RequestParamParameterResolver implements ParameterResolver {
    @Override
    public Object resolve(Parameter parameter, RequestParamManager requestParamManager){
        RequestParam requestParam = parameter.getDeclaredAnnotation(RequestParam.class);
        String requestParameter = requestParam.value();
        String requestParameterValue = requestParamManager.getParamParameterMap().get(requestParameter);
        // convert the parameter to the specified type
        return ObjectUtil.convert(parameter.getType(), requestParameterValue);
    }

}
