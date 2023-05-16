package com.zliio.hummingbird.web.factory;

import com.zliio.hummingbird.web.common.http.InternalServerErrorRsp;
import com.zliio.hummingbird.web.common.http.ResultCodeEnum;
import com.zliio.hummingbird.web.common.http.ServiceResponseMessage;
import com.zliio.hummingbird.web.common.lang.HttpMediaType;
import com.zliio.hummingbird.web.serializer.Serializer;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;


import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 回包返回处理工厂
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class FullHttpResponseFactory {


    public static FullHttpResponse buildDefaultFullHttpResponseWithResult(String[] produces, Object responseObject) {
        Serializer serializer = SerializerFactory.getSerializerWithContentType(produces);
        byte[] content = serializer.serializeToByteArray(responseObject);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(content));
        StringBuilder productBuilder = new StringBuilder();
        for (String produce : produces) {
            productBuilder.append(String.format("%s;",produce));
        }
        response.headers().set(HttpMediaType.CONTENT_TYPE, productBuilder.toString());
        response.headers().setInt(HttpMediaType.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }


    public static FullHttpResponse buildDefaultFullHttpResponseNoResult(String[] produces) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
        if(null == produces || produces.length == 0){
            produces = new String[1];
            produces[0] = HttpMediaType.ALL_VALUE;
        }
        response.headers().set(HttpMediaType.CONTENT_TYPE, produces);
        response.headers().setInt(HttpMediaType.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    public static FullHttpResponse internalServerError(Throwable throwable) {
        ServiceResponseMessage<InternalServerErrorRsp> serverErrorRspServiceResponseMessage = ServiceResponseMessage.internalServerError(throwable);
        Serializer serializer = SerializerFactory.getDefaultSerializer();
        byte[] content = serializer.serializeToByteArray(serverErrorRspServiceResponseMessage);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR, Unpooled.wrappedBuffer(content));
        response.headers().set(HttpMediaType.CONTENT_TYPE, HttpMediaType.APPLICATION_JSON_UTF8_VALUE);
        response.headers().setInt(HttpMediaType.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    public static FullHttpResponse internalServerUrlNotFindError(String message) {
        Serializer serializer = SerializerFactory.getDefaultSerializer();
        ServiceResponseMessage<InternalServerErrorRsp>  serverErrorRspServiceResponseMessage
                = ServiceResponseMessage.createByFailCodeMessage(ResultCodeEnum.NOT_FIND,message);
        byte[] content = serializer.serializeToByteArray(serverErrorRspServiceResponseMessage);
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.NOT_FOUND, Unpooled.wrappedBuffer(content));
        response.headers().setInt(HttpMediaType.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpMediaType.CONTENT_TYPE, HttpMediaType.APPLICATION_JSON_UTF8_VALUE);
        return response;
    }
}
