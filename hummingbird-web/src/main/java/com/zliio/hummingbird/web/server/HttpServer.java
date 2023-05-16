package com.zliio.hummingbird.web.server;

import com.zliio.hummingbird.core.factory.ConfigurationFactory;
import com.zliio.hummingbird.core.logging.Logger;
import com.zliio.hummingbird.core.logging.LoggerFactory;
import com.zliio.hummingbird.web.common.constant.SystemConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.NettyRuntime;


/**
 * 基于Netty的Http服务器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class HttpServer {
    private final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private static final int DEFAULT_PORT = ConfigurationFactory.getDefaultConfig().getInt("hummingbird.web.port");


    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors());
        EventLoopGroup workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors() * 2);
        try {
            HummingbirdChannelInitializer HummingbirdChannelInitializer = new HummingbirdChannelInitializer();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否开启 TCP 底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(HummingbirdChannelInitializer);
            Channel ch = b.bind(DEFAULT_PORT).sync().channel();
            logger.info(SystemConstants.LOG_PORT_BANNER, DEFAULT_PORT);
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("Hummingbird service startup abnormal", e);
        } finally {
            logger.error("Hummingbird service shutdown bossGroup and workerGroup");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
