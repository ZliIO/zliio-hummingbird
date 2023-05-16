package com.zliio.hummingbird.web.common.lang;

/**
 * HTTP请求的类型
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public enum HttpMethod {
    /**
     * HTTP请求的类型
     */
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");


    /**
     * 请求方法类型
     */
    private final String methodType;

    HttpMethod(String methodType) {
        this.methodType = methodType;
    }

    public String getMethodType() {
        return methodType;
    }
}
