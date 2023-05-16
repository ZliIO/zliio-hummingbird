package com.zliio.hummingbird.core.annotation.bean;

import java.lang.annotation.*;

/**
 * @author ZLiIO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {
    String[] value() default {};
}
