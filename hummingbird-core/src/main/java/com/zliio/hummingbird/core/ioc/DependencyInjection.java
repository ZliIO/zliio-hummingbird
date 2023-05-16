package com.zliio.hummingbird.core.ioc;

import com.zliio.hummingbird.core.factory.BeanFactory;

/**
 * 依赖注入
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class DependencyInjection {

    /**
     * 遍历ioc容器所有bean的属性, 为所有带@Autowired注解的属性注入实例
     */
    public static void inject(String[] packageNames) {
        AutowiredBeanInitialization autowiredBeanInitialization = new AutowiredBeanInitialization(packageNames);
        //创建好的bean都放入对象工厂
        if (BeanFactory.BEANS.size() > 0) {
            BeanFactory.BEANS.values().forEach(autowiredBeanInitialization::initialize);
        }
    }
}
