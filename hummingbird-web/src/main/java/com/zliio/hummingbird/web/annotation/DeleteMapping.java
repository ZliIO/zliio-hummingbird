package com.zliio.hummingbird.web.annotation;

import java.lang.annotation.*;


/**
 * Delete 请求的相关Map映射
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteMapping {

    String name() default "";

    String[] value() default {};

    String[] path() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}
