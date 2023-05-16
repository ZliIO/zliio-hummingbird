package com.zliio.hummingbird.core.aop.intercept;


/**
 * bean 后置处理器
 *
 * @author tom
 
 */
public interface BeanPostProcessor {

    default Object postProcessAfterInitialization(Object bean) {
        return bean;
    }
}
