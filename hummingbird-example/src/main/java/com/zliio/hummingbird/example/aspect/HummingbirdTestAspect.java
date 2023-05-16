package com.zliio.hummingbird.example.aspect;

import com.zliio.hummingbird.core.annotation.aop.*;
import com.zliio.hummingbird.core.annotation.ioc.Component;
import com.zliio.hummingbird.core.aop.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
@Order(value = 1)
public class HummingbirdTestAspect {
    private final static Logger logger = LoggerFactory.getLogger(HummingbirdTestAspect.class);

    @Pointcut("com.zliio.hummingbird.example.service.*Service*")
    public void oneAspect() {
    }

    @Before
    public void beforeAction(JoinPoint params) {
        logger.debug("aspect headmaster : before to do something");
    }

    @After
    public void afterAction(Object result, JoinPoint joinPoint) {
        logger.debug("aspect headmaster after to do something");
    }

}
