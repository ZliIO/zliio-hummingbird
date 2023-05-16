package com.zliio.hummingbird.web.handler.impl;

import com.zliio.hummingbird.web.common.entity.RequestMappingDetail;
import com.zliio.hummingbird.web.common.lang.HttpMethod;
import com.zliio.hummingbird.web.common.util.UrlUtil;
import com.zliio.hummingbird.web.factory.RouteMethodMapper;
import com.zliio.hummingbird.web.handler.RequestHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Get请求的处理器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class PostRequestHandler implements RequestHandler {
    @Override
    public FullHttpResponse handle(FullHttpRequest fullHttpRequest) {
        String requestUri = fullHttpRequest.uri();
        // get http request path
        String requestPath = UrlUtil.getRequestPath(requestUri);
        // get target request Mapping detail
        RequestMappingDetail requestMappingDetail = RouteMethodMapper.getRequestMappingDetail(requestPath, HttpMethod.POST);
        return run(fullHttpRequest,requestMappingDetail);
    }
}
