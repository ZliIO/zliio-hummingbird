package com.zliio.hummingbird.web.exception;

import com.zliio.hummingbird.core.exception.HummingbirdRuntimeException;

/**
 * 请求的处理器映射没有找到
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class RequestMappingNotFindException extends HummingbirdRuntimeException {
    public RequestMappingNotFindException(String message) {
        super(message);
    }
}
