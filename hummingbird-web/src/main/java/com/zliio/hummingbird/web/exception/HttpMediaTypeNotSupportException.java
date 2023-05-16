package com.zliio.hummingbird.web.exception;

import com.zliio.hummingbird.core.exception.HummingbirdRuntimeException;

/**
 * 请求携带的正文内容的数据类型不支持
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class HttpMediaTypeNotSupportException extends HummingbirdRuntimeException {
    public HttpMediaTypeNotSupportException(String message) {
        super(message);
    }
}
