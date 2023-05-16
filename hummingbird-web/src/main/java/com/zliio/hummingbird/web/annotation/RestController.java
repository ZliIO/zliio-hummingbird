package com.zliio.hummingbird.web.annotation;

import com.zliio.hummingbird.core.annotation.ioc.Component;

import java.lang.annotation.*;

/**
 * 标记为控制器的入口的Bean
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RestController {
    String value() default "";
    String name() default "";
}
