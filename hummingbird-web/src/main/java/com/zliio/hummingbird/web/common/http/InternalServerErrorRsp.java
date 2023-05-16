package com.zliio.hummingbird.web.common.http;

/**
 * 服务器异常回包
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class InternalServerErrorRsp implements HttpResponse{
    private String message;
    private Throwable throwable;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
