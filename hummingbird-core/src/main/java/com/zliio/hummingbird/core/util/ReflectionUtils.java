package com.zliio.hummingbird.core.util;

import com.zliio.hummingbird.core.exception.CanNotInvokeTargetMethodException;
import com.zliio.hummingbird.core.exception.CheckClassScanParamsException;
import com.zliio.hummingbird.core.logging.Logger;
import com.zliio.hummingbird.core.logging.LoggerFactory;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * 项目中可使用的通用反射工具
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 */
public final class ReflectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

//    static {
//        Reflections.log = null;
//    }


    /**
     * scan the classes marked by the specified annotation in the specified package
     *
     * @param packageNames specified package name
     * @param annotation  specified annotation
     * @return the classes marked by the specified annotation in the specified package
     */
    public static Set<Class<?>> scanAnnotatedClass(String[] packageNames, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageNames, new TypeAnnotationsScanner());
        Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
        logger.trace("The number of class Annotated with  @{} :[{}]",annotation.getSimpleName(),annotatedClass.size());
        return annotatedClass;
    }


    /**
     * scan the classes marked by the specified annotation in the specified package
     *
     * @param packageNames specified package name
     * @param annotationList  specified annotation
     * @return the classes marked by the specified annotation in the specified package
     */
    public static Map<Class<? extends Annotation>,Set<Class<?>>> scanAnnotatedClasses(String[] packageNames, Set<Class<? extends Annotation>> annotationList) {
        if(null == packageNames || null == annotationList || 0 == packageNames.length || 0 == annotationList.size()){
            throw new CheckClassScanParamsException("Not find scan the classes Params");
        }
        Reflections reflections = new Reflections(packageNames, new TypeAnnotationsScanner());
        Map<Class<? extends Annotation>,Set<Class<?>>> annotatedClassesMap = new ConcurrentHashMap<>(annotationList.size());
        for (Class<? extends Annotation> annotation : annotationList) {
            Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(annotation, true);
            if(!annotatedClass.isEmpty()){
                logger.trace("The number of class Annotated with  @{} :[{}]",annotation.getSimpleName(),annotatedClass.size());
                annotatedClassesMap.put(annotation,annotatedClass) ;
            }
        }
        return annotatedClassesMap;
    }


    /**
     * Get the implementation class of the interface
     *
     * @param packageNames    specified package name
     * @param interfaceClass specified interface
     */
    public static <T> Set<Class<? extends T>> getSubClass(Object[] packageNames, Class<T> interfaceClass) {
        Reflections reflections = new Reflections(packageNames);
        return reflections.getSubTypesOf(interfaceClass);

    }


    /**
     * create object instance through class
     *
     * @param cls target class
     * @return object created by the target class
     */
    public static <T> T newInstance(Class<T> cls) {
        T instance = null;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("new instance failed", e);
        }
        return instance;
    }

    /**
     * set the value of a field in the object
     *
     * @param obj   target object
     * @param field target field
     * @param value the value assigned to the field
     */
    public static void setField(Object obj, Field field, Object value) {

        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("set field failed", e);
            e.printStackTrace();
        }

    }

    /**
     * execute the target method
     *
     * @param method target method
     * @param args   method parameters
     * @return the result of method execution
     */
    public static Object executeTargetMethod(Object targetObject, Method method, Object... args) {
        Object result;
        try {
            // invoke target method through reflection
            result = method.invoke(targetObject, args);
            if(Void.class != method.getReturnType()){
                logger.debug("invoke target method successfully");
            }else {
                logger.debug("invoke target method successfully ,result is: [{}]", result.toString());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CanNotInvokeTargetMethodException(e.toString());
        }
        return result;
    }

    /**
     * execute the target method
     *
     * @param method target method
     * @param args   method parameters
     */
    public static void executeTargetMethodNoResult(Object targetObject, Method method, Object... args) {
        try {
            // invoke target method through reflection
            method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CanNotInvokeTargetMethodException(e.toString());
        }
    }


    /**
     * 获取所有field字段，包含父类继承的
     *
     * @param clazz 字段所属类型
     * @return
     */
    public static Field[] getFields(Class<?> clazz) {
        return getFields(clazz, null);
    }

    /**
     * 获取指定类的所有的field,包括父类
     *
     * @param clazz       字段所属类型
     * @param fieldFilter 字段过滤器
     * @return 符合过滤器条件的字段数组
     */
    public static Field[] getFields(Class<?> clazz, Predicate<Field> fieldFilter) {
        List<Field> fields = new ArrayList<>(32);
        while (Object.class != clazz && clazz != null) {
            // 获得该类所有声明的字段，即包括public、private和protected，但是不包括父类的申明字段，
            // getFields：获得某个类的所有的公共（public）的字段，包括父类中的字段
            for (Field field : clazz.getDeclaredFields()) {
                if (fieldFilter != null && !fieldFilter.test(field)) {
                    continue;
                }
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }

    /**
     * 对指定类的所有字段执行consumer操作
     *
     * @param clazz    目标对象
     * @param consumer 对字段进行操作
     */
    public static void doWithFields(Class<?> clazz, Consumer<Field> consumer) {
        Arrays.stream(getFields(clazz)).forEach(consumer);
    }

    /**
     * 获取指定类的指定field,包括父类
     *
     * @param clazz 字段所属类型
     * @param name  字段名
     * @return
     */
    public static Field getField(Class<?> clazz, String name) {
        return getField(clazz, name, null);
    }

    /**
     * 获取指定类的指定field,包括父类
     *
     * @param clazz 字段所属类型
     * @param name  字段名
     * @param type  field类型
     * @return      Field对象
     */
    public static Field getField(Class<?> clazz, String name, Class<?> type) {
        Assert.notNull(clazz, "clazz不能为空！");
        while (clazz != Object.class && clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if ((name == null || name.equals(field.getName())) &&
                        (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    /**
     * 获取字段值
     *
     * @param field    字段
     * @param target  字段所属实例对象
     * @return        字段值
     */
    public static Object getFieldValue(Field field, Object target) {
        makeAccessible(field);
        try {
            return field.get(target);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("获取%s对象的%s字段值错误!"
                    , target.getClass().getName(), field.getName()), e);
        }
    }

    /**
     * 获取对象中指定field值
     *
     * @param obj       对象
     * @param fieldName  字段名
     * @return          字段值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Assert.notNull(obj, "obj不能为空!");
        if (ObjectUtils.isWrapperOrPrimitive(obj)) {
            return obj;
        }
        return getFieldValue(getField(obj.getClass(), fieldName), obj);
    }

    /**
     * 获取指定对象中指定字段路径的值(类似js访问对象属性) <br/>
     * 如：Product p = new Product(new User())  <br/>
     * 可使用ReflectionUtils.getValueByFieldPath(p, "user.name")获取到用户的name属性
     *
     * @param obj       取值对象
     * @param fieldPath 字段路径(形如 user.name)
     * @return 字段value
     */
    public static Object getValueByFieldPath(Object obj, String fieldPath) {
        String[] fieldNames = fieldPath.split("\\.");
        Object result = null;
        for (String fieldName : fieldNames) {
            result = getFieldValue(obj, fieldName);
            if (result == null) {
                return null;
            }
            obj = result;
        }
        return result;
    }

    /**
     * 设置字段值
     *
     * @param field  字段
     * @param target 字段所属对象实例
     * @param value  需要设置的值
     */
    public static void setFieldValue(Field field, Object target, Object value) {
        makeAccessible(field);
        try {
            field.set(target, value);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("设置%s对象的%s字段值错误!"
                    , target.getClass().getName(), field.getName()), e);
        }
    }

    /**
     * 设置字段为可见
     *
     * @param field
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 调用无参数方法
     *
     * @param method 方法对象
     * @param target 调用对象
     * @return 执行结果
     */
    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, new Object[0]);
    }

    /**
     * 调用指定对象的方法
     *
     * @param method 方法对象
     * @param target 调用对象
     * @param args   方法参数
     * @return 执行结果
     */
    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            makeAccessible(method);
            return method.invoke(target, args);
        } catch (Exception ex) {
            throw new IllegalStateException(String.format("执行%s.%s()方法错误!"
                    , target.getClass().getName(), method.getName()), ex);
        }
    }

    /**
     * 设置方法可见性
     *
     * @param method  方法
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 是否为equals方法
     *
     * @see Object#equals(Object)
     */
    public static boolean isEqualsMethod(Method method) {
        if (!"equals".equals(method.getName())) {
            return false;
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        return (paramTypes.length == 1 && paramTypes[0] == Object.class);
    }

    /**
     * 是否为hashCode方法
     *
     * @see Object#hashCode()
     */
    public static boolean isHashCodeMethod(Method method) {
        return "hashCode".equals(method.getName()) && method.getParameterCount() == 0;
    }

    /**
     * 是否为Object的toString方法
     *
     * @see Object#toString()
     */
    public static boolean isToStringMethod(Method method) {
        return "toString".equals(method.getName()) && method.getParameterCount() == 0;
    }
}
