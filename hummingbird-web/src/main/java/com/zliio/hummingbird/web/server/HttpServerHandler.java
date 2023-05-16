package com.zliio.hummingbird.web.server;

import com.zliio.hummingbird.core.logging.Logger;
import com.zliio.hummingbird.core.logging.LoggerFactory;
import com.zliio.hummingbird.web.common.lang.HttpMediaType;
import com.zliio.hummingbird.web.common.util.UrlUtil;
import com.zliio.hummingbird.web.exception.RequestMappingNotFindException;
import com.zliio.hummingbird.web.factory.FullHttpResponseFactory;
import com.zliio.hummingbird.web.factory.RequestHandlerFactory;
import com.zliio.hummingbird.web.handler.RequestHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

;

/**
 * Http Server Handler
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        long startTime = System.currentTimeMillis();
        String uri = fullHttpRequest.uri();
        if (uri.equals(HttpMediaType.FAVICON_ICO)) {
            return;
        }
        RequestHandler requestHandler = RequestHandlerFactory.get(fullHttpRequest.method());
        FullHttpResponse response;
        try {
            response = requestHandler.handle(fullHttpRequest);
        } catch (RequestMappingNotFindException e) {
            response = FullHttpResponseFactory.internalServerUrlNotFindError(e.getMessage());
            logger.error("handler request uri not find {}",e.getMessage());
        }catch (Exception e){
            response = FullHttpResponseFactory.internalServerError(e);
            logger.error("handler request error {}",e.getMessage(),e);
        }
        boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
        if (!keepAlive) {
            channelHandlerContext.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(HttpMediaType.CONNECTION,HttpMediaType.KEEP_ALIVE);
            channelHandlerContext.write(response);
        }
        if(logger.isDebugEnabled()){
            logger.debug("handler request finished uri [{}] use [{}]ms",UrlUtil.getRequestPath(uri),System.currentTimeMillis() - startTime);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("handler request exception caught {}",cause.getMessage(),cause);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

}
