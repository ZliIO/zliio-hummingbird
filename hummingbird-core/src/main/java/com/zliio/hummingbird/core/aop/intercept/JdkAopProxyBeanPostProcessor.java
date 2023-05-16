package com.zliio.hummingbird.core.aop.intercept;


import com.zliio.hummingbird.core.aop.proxy.JdkAspectProxy;

/**
 * @author tom
 * JDK implementation of dynamic proxy
 
 */
public class JdkAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {


    @Override
    public Object wrapBean(Object target, Interceptor interceptor) {
        return JdkAspectProxy.wrap(target, interceptor);
    }
}