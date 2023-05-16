package com.zliio.hummingbird.example.aspect;

import com.zliio.hummingbird.core.aop.intercept.Interceptor;
import com.zliio.hummingbird.core.aop.intercept.MethodInvocation;
import com.zliio.hummingbird.example.controller.HummingbirdTestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GlobalInterceptor extends Interceptor {
    private final static Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);
    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        logger.debug(" before method：" + methodInvocation.getTargetMethod().getName());
        Object result = methodInvocation.proceed();
        logger.debug(" after method：" + methodInvocation.getTargetMethod().getName());
        return result;
    }

    @Override
    public boolean supports(Object bean) {
        return bean instanceof HummingbirdTestController;
    }
}
