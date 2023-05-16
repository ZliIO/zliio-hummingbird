package com.zliio.hummingbird.web.common.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 消息的统一回复类,提供消息回复统一模板
 * 项目使用JSON,通过这种方式，构造通用的JSON返回包装类
 *
 * @author ZLiIO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponseMessage<T extends HttpResponse> implements Serializable {
    private static final String SUCCESS = "SUCCESS";

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Meta meta;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 隐藏构造器,规范化项目操作，通过内部的静态方法统一创建管理
     * @author ZLiIO
     */
    private ServiceResponseMessage(){
    }

    public static ServiceResponseMessage<DefaultHttpRsp> createBySuccessCodeMessage(){
        ServiceResponseMessage<DefaultHttpRsp> serviceResponseMessage = new ServiceResponseMessage<>();
        serviceResponseMessage.meta = new Meta(ResultCodeEnum.SUCCESS_NO_CONTENT.getCode(),SUCCESS);
        return serviceResponseMessage;
    }

    public static ServiceResponseMessage<DefaultHttpRsp> createBySuccessCodeMessage(String msg){
        ServiceResponseMessage<DefaultHttpRsp> serviceResponseMessage = new ServiceResponseMessage<>();
        serviceResponseMessage.meta = new Meta(ResultCodeEnum.SUCCESS_NO_CONTENT.getCode(),msg);
        return serviceResponseMessage;
    }

    public static <T extends HttpResponse> ServiceResponseMessage<T> createBySuccessCodeMessage(ResultCodeEnum resultCode, T data){
        ServiceResponseMessage<T> serviceResponseMessage = new ServiceResponseMessage<>();
        serviceResponseMessage.meta = new Meta(resultCode.getCode(),SUCCESS);
        serviceResponseMessage.data = data;
        return serviceResponseMessage;
    }

    public static  <T extends HttpResponse> ServiceResponseMessage<T>  createBySuccessCodeMessage(T data){
        ServiceResponseMessage<T> serviceResponseMessage = new ServiceResponseMessage<>();
        serviceResponseMessage.meta = new Meta(ResultCodeEnum.SUCCESS.getCode(),SUCCESS);
        serviceResponseMessage.data = data;
        return serviceResponseMessage;
    }


    public static  <T extends HttpResponse> ServiceResponseMessage<T> createBySuccessCodeMessage(String msg, T data){
        ServiceResponseMessage<T> serviceResponseMessage = new ServiceResponseMessage<>();
        serviceResponseMessage.meta = new Meta(ResultCodeEnum.SUCCESS.getCode(),msg);
        serviceResponseMessage.data = data;
        return serviceResponseMessage;
    }


    public static <T extends HttpResponse> ServiceResponseMessage<T> createByFailCodeMessage(String msg){
        ServiceResponseMessage<T> serviceResponseMessage = new ServiceResponseMessage<>();
        serviceResponseMessage.meta = new Meta(ResultCodeEnum.BAD_REQUEST.getCode(),msg);
        return serviceResponseMessage;
    }

    public static <T extends HttpResponse>ServiceResponseMessage<T> createByFailCodeMessage(ResultCodeEnum resultCode, String msg){
        ServiceResponseMessage<T> serviceResponseMessage = new ServiceResponseMessage<>();
        serviceResponseMessage.meta = new Meta(resultCode.getCode(),msg);
        return serviceResponseMessage;
    }


    public static <T extends HttpResponse> ServiceResponseMessage<T> createCommonMessage(ResultCodeEnum resultCode, String msg, T data){
        ServiceResponseMessage<T> serviceResponseMessage = new ServiceResponseMessage<>();
        serviceResponseMessage.meta = new Meta(resultCode.getCode(),msg);
        serviceResponseMessage.data = data;
        return serviceResponseMessage;
    }
    public static ServiceResponseMessage<InternalServerErrorRsp> internalServerError(Throwable throwable) {
        ServiceResponseMessage<InternalServerErrorRsp> serviceResponseMessage = new ServiceResponseMessage<>();
        InternalServerErrorRsp internalServerErrorRsp = new InternalServerErrorRsp();
        internalServerErrorRsp.setMessage(throwable.getMessage());
        internalServerErrorRsp.setThrowable(throwable);
        serviceResponseMessage.meta = new Meta(ResultCodeEnum.UNKNOWN.getCode(),throwable.getMessage());
        serviceResponseMessage.data = internalServerErrorRsp;
        return serviceResponseMessage;
    }


    /**
     * 消息返回的元数据
     * 包含消息的返回码和提示信息
     *
     * @author ZLiIO
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Meta{
        @JsonProperty
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final int code;
        @JsonProperty
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String msg;

        @JsonProperty
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final Long timestamp;

        private Meta(int resultCode, String message) {
            this.code = resultCode;
            this.msg = message;
            timestamp = System.currentTimeMillis();
        }
    }
}
