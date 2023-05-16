package com.zliio.hummingbird.web.factory;

import com.zliio.hummingbird.web.annotation.PathVariable;
import com.zliio.hummingbird.web.annotation.ProtobufBody;
import com.zliio.hummingbird.web.annotation.RequestBody;
import com.zliio.hummingbird.web.annotation.RequestParam;
import com.zliio.hummingbird.web.resolver.ParameterResolver;
import com.zliio.hummingbird.web.resolver.impl.PathVariableParameterResolver;
import com.zliio.hummingbird.web.resolver.impl.ProtobufBodyParameterResolver;
import com.zliio.hummingbird.web.resolver.impl.RequestBodyParameterResolver;
import com.zliio.hummingbird.web.resolver.impl.RequestParamParameterResolver;

import java.lang.reflect.Parameter;

/**
 * 请求参数的解析处理器工厂
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class ParameterResolverFactory {
    private static final ParameterResolver REQUEST_PARAM_PARAMETER_RESOLVER;
    private static final ParameterResolver PATH_VARIABLE_PARAMETER_RESOLVER;
    private static final ParameterResolver REQUEST_BODY_PARAMETER_RESOLVER;
    private static final ParameterResolver PROTOBUF_BODY_PARAMETER_RESOLVER;

    static {
        REQUEST_PARAM_PARAMETER_RESOLVER =  new RequestParamParameterResolver();
        PATH_VARIABLE_PARAMETER_RESOLVER = new PathVariableParameterResolver();
        REQUEST_BODY_PARAMETER_RESOLVER = new RequestBodyParameterResolver();
        PROTOBUF_BODY_PARAMETER_RESOLVER = new ProtobufBodyParameterResolver();
    }

    public static ParameterResolver get(Parameter parameter) {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
            return REQUEST_PARAM_PARAMETER_RESOLVER;
        }
        if (parameter.isAnnotationPresent(PathVariable.class)) {
            return PATH_VARIABLE_PARAMETER_RESOLVER;
        }
        if (parameter.isAnnotationPresent(RequestBody.class)) {
            return REQUEST_BODY_PARAMETER_RESOLVER;
        }
        if(parameter.isAnnotationPresent(ProtobufBody.class)){
            return PROTOBUF_BODY_PARAMETER_RESOLVER;
        }
        return null;
    }
}
