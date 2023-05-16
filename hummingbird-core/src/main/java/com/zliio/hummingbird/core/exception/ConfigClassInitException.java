package com.zliio.hummingbird.core.exception;

/**
 * 配置加载异常
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class ConfigClassInitException extends HummingbirdRuntimeException{
    public ConfigClassInitException(String message) {
        super(message);
    }

    public ConfigClassInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
