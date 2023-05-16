package com.zliio.hummingbird.web.common.util;

import com.zliio.hummingbird.web.annotation.RestController;

/**
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class BeanHelper {
    /**
     * get the bean name
     *
     * @param aClass target class
     * @return the bean name
     */
    public static String getBeanName(Class<?> aClass) {
        String beanName = aClass.getName();
        if (aClass.isAnnotationPresent(RestController.class)) {
            RestController restController = aClass.getAnnotation(RestController.class);
            beanName = "".equals(restController.name()) ? aClass.getName() : restController.name();
        }
        return beanName;
    }
}
