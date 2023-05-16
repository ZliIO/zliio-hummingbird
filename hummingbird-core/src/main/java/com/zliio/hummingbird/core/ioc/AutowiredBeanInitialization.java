package com.zliio.hummingbird.core.ioc;

import com.zliio.hummingbird.core.annotation.ioc.Autowired;
import com.zliio.hummingbird.core.annotation.ioc.Qualifier;
import com.zliio.hummingbird.core.annotation.ioc.Value;
import com.zliio.hummingbird.core.aop.factory.AopProxyBeanPostProcessorFactory;
import com.zliio.hummingbird.core.aop.intercept.BeanPostProcessor;
import com.zliio.hummingbird.core.common.BeanHelper;
import com.zliio.hummingbird.core.config.Configuration;
import com.zliio.hummingbird.core.config.ConfigurationManager;
import com.zliio.hummingbird.core.exception.CanNotDetermineTargetBeanException;
import com.zliio.hummingbird.core.exception.InterfaceNotHaveImplementedClassException;
import com.zliio.hummingbird.core.factory.BeanFactory;
import com.zliio.hummingbird.core.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自动注入的Bean初始化管理器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class AutowiredBeanInitialization {

    private final String[] packageNames;

    private static final Map<String, Object> SINGLETON_OBJECTS = new ConcurrentHashMap<>(64);

    public AutowiredBeanInitialization(String[] packageNames) {
       this.packageNames = packageNames;
    }

    public void initialize(Object beanInstance){
        Class<?> beanClass = beanInstance.getClass();
        Field[] beanFields = beanClass.getDeclaredFields();
        // 遍历bean的属性
        if(beanFields.length > 0){
            for (Field beanField : beanFields) {
                if (beanField.isAnnotationPresent(Autowired.class)) {
                    Object beanFieldInstance = processAutowiredAnnotationField(beanField);
                    String beanFieldName = BeanHelper.getBeanName(beanField.getType());
                    // 解决循环依赖问题
                    beanFieldInstance = resolveCircularDependency(beanInstance, beanFieldInstance, beanFieldName);
                    // AOP
                    BeanPostProcessor beanPostProcessor = AopProxyBeanPostProcessorFactory.get(beanField.getType());
                    beanFieldInstance = beanPostProcessor.postProcessAfterInitialization(beanFieldInstance);
                    ReflectionUtils.setField(beanInstance, beanField, beanFieldInstance);
                }
                if (beanField.isAnnotationPresent(Value.class)) {
                    Object convertedValue = processValueAnnotationField(beanField);
                    ReflectionUtils.setField(beanInstance, beanField, convertedValue);
                }
            }
        }

    }

    /**
     * 处理被 @Autowired 注解标记的字段
     *
     * @param beanField 目标类的字段
     * @return 目标类的字段对应的对象
     */
    private Object processAutowiredAnnotationField(Field beanField) {
        Class<?> beanFieldClass = beanField.getType();
        String beanFieldName = BeanHelper.getBeanName(beanFieldClass);
        Object beanFieldInstance;
        if (beanFieldClass.isInterface()) {
            @SuppressWarnings("unchecked")
            Set<Class<?>> subClasses = ReflectionUtils.getSubClass(packageNames,(Class<Object>) beanFieldClass);
            if (subClasses.size() == 0) {
                throw new InterfaceNotHaveImplementedClassException(beanFieldClass.getName() + "is interface and do not have implemented class exception");
            }
            if (subClasses.size() == 1) {
                Class<?> subClass = subClasses.iterator().next();
                beanFieldName = BeanHelper.getBeanName(subClass);
            }
            if (subClasses.size() > 1) {
                Qualifier qualifier = beanField.getDeclaredAnnotation(Qualifier.class);
                beanFieldName = qualifier == null ? beanFieldName : qualifier.value();
            }

        }
        beanFieldInstance = BeanFactory.BEANS.get(beanFieldName);
        if (beanFieldInstance == null) {
            throw new CanNotDetermineTargetBeanException("can not determine target bean of" + beanFieldClass.getName());
        }
        return beanFieldInstance;
    }


    /**
     * 处理被 @Value 注解标记的字段
     *
     * @param beanField 目标类的字段
     * @return 目标类的字段对应的对象
     */
    private Object processValueAnnotationField(Field beanField) {
        String key = beanField.getDeclaredAnnotation(Value.class).value();
        key = "".equals(key) ? beanField.getName() : key;
        Configuration configuration =(Configuration)BeanFactory.getInstanceByClazz(ConfigurationManager.class);
        String value = configuration.getString(key);
        if (value == null) {
            throw new IllegalArgumentException("can not find target value for property:{" + key + "}");
        }
        return BeanHelper.convertBeanType(beanField.getType(), value);
    }

    private Object resolveCircularDependency(Object beanInstance, Object beanFieldInstance, String beanFieldName) {
        if (SINGLETON_OBJECTS.containsKey(beanFieldName)) {
            beanFieldInstance = SINGLETON_OBJECTS.get(beanFieldName);
        } else {
            SINGLETON_OBJECTS.put(beanFieldName, beanFieldInstance);
            initialize(beanInstance);
        }
        return beanFieldInstance;
    }
}
