package com.zliio.hummingbird.core.factory;

import com.zliio.hummingbird.core.annotation.aop.Aspect;
import com.zliio.hummingbird.core.annotation.ioc.Component;
import com.zliio.hummingbird.core.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZLiIO
 *
 *
 */
public class ClassFactory {
    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new ConcurrentHashMap<>();
    private static final Set<Class<? extends Annotation>> SCAN_ANNOTATION = new HashSet<>(16);

    static {
        SCAN_ANNOTATION.add(Aspect.class);
        SCAN_ANNOTATION.add(Component.class);
    }

    public static void addScanAnnotation(Class<? extends Annotation> annotation){
        if(annotation.isAnnotationPresent(Component.class)){
            SCAN_ANNOTATION.add(annotation);
        }
    }

    public static void loadClass(String[] packageNames) {
        CLASSES.putAll(ReflectionUtils.scanAnnotatedClasses(packageNames,SCAN_ANNOTATION));
    }
}
