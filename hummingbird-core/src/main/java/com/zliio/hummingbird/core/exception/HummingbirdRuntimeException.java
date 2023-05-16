package com.zliio.hummingbird.core.exception;

/**
 * @author ZLiIO
 */
public abstract class HummingbirdRuntimeException extends RuntimeException{
    public HummingbirdRuntimeException() {
        super();
    }

    public HummingbirdRuntimeException(String message) {
        super(message);
    }

    public HummingbirdRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HummingbirdRuntimeException(Throwable cause) {
        super(cause);
    }

    protected HummingbirdRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
