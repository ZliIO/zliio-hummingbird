package com.zliio.hummingbird.core.aop.intercept;


import com.zliio.hummingbird.core.aop.proxy.CglibAspectProxy;

/**
 * @author tom
 * JDK implementation of dynamic proxy
 
 */
public class CglibAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {

    @Override
    public Object wrapBean(Object target, Interceptor interceptor) {
        return CglibAspectProxy.wrap(target, interceptor);
    }

}