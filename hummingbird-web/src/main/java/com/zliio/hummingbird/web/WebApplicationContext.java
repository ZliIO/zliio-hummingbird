package com.zliio.hummingbird.web;

import com.zliio.hummingbird.core.ApplicationContext;
import com.zliio.hummingbird.core.exception.SingleBeanCreateException;
import com.zliio.hummingbird.core.factory.BeanFactory;
import com.zliio.hummingbird.core.factory.ClassFactory;
import com.zliio.hummingbird.web.annotation.RestController;
import com.zliio.hummingbird.web.factory.RouteMethodMapper;
import com.zliio.hummingbird.web.server.HttpServer;

/**
 * Web的服务应用上下文
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public final class WebApplicationContext {
    private static final WebApplicationContext WEB_APPLICATION_CONTEXT = new WebApplicationContext();

    private WebApplicationContext() {
        if (null != WEB_APPLICATION_CONTEXT) {
            throw new SingleBeanCreateException();
        }
    }

    public void run(Class<?> bootstrapClazz,String... args){
        ClassFactory.addScanAnnotation(RestController.class);
        BeanFactory.addLoadBeanAnnotation(RestController.class);
        ApplicationContext.getApplicationContext().run(bootstrapClazz,args);
        RouteMethodMapper.loadRoutes();
        new HttpServer().start();
    }


    public static WebApplicationContext getWebApplicationContext() {
        return WEB_APPLICATION_CONTEXT;
    }

}
