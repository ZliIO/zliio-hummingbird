package com.zliio.hummingbird.core.common;

import com.zliio.hummingbird.core.annotation.ioc.Component;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

public class BeanHelper {
    /**
     * get the bean name
     *
     * @param targetBeanClass target class
     * @return the bean name
     */
    public static String getBeanName(Class<?> targetBeanClass) {
        String beanName = targetBeanClass.getName();
        if (targetBeanClass.isAnnotationPresent(Component.class)) {
            Component component = targetBeanClass.getAnnotation(Component.class);
            beanName = "".equals(component.name()) ? targetBeanClass.getName() : component.name();
        }
        return beanName;
    }

    /**
     * convert from String to a target type
     *
     * @param targetType the type to be converted
     * @param s          the string to be converted
     * @throws NumberFormatException When string to number, if string is not a number,then throw NumberFormatException
     */
    public static Object convertBeanType(Class<?> targetType, String s) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(s);
        return editor.getValue();
    }
}
