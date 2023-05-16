package com.zliio.hummingbird.core;

import com.zliio.hummingbird.core.annotation.bean.ComponentScan;
import com.zliio.hummingbird.core.aop.factory.InterceptorFactory;
import com.zliio.hummingbird.core.banner.Banner;
import com.zliio.hummingbird.core.exception.SingleBeanCreateException;
import com.zliio.hummingbird.core.factory.BeanFactory;
import com.zliio.hummingbird.core.factory.ClassFactory;
import com.zliio.hummingbird.core.factory.ConfigurationFactory;
import com.zliio.hummingbird.core.ioc.DependencyInjection;

import java.util.Objects;


/**
 * 应用上下文
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public final class ApplicationContext {
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();

    private ApplicationContext() {
        if(null != APPLICATION_CONTEXT){
            throw new SingleBeanCreateException();
        }
    }

    public void run(Class<?> bootstrapClass,String... args){
        Banner.print();
        // load class package names
        String[] loadPackageNames = parseLoadedPackageNameByBootstrapClass(bootstrapClass);
        // Load classes with custom annotation
        ClassFactory.loadClass(loadPackageNames);
        // Load resource from yaml or properties
        ConfigurationFactory.loadResourceConfiguration(bootstrapClass.getClassLoader(),loadPackageNames);
        ConfigurationFactory.loadBootstrapConfiguration(args);
        // Load beans managed by the ioc container
        BeanFactory.loadBeans();
        // Load interceptors
        InterceptorFactory.loadInterceptors(loadPackageNames);
        // Traverse all the beans in the ioc container and inject instances for all @Autowired annotated attributes.
        DependencyInjection.inject(loadPackageNames);
        // Applies bean post processors on the classes which are from ClassFactory.
        // For example, the class annotated by @Component
        BeanFactory.applyBeanPostProcessors();
    }

    private String[] parseLoadedPackageNameByBootstrapClass(Class<?> bootstrapClass){
        ComponentScan componentScan = bootstrapClass.getAnnotation(ComponentScan.class);
        return !Objects.isNull(componentScan) ? componentScan.value()
                : new String[]{bootstrapClass.getPackage().getName()};
    }



    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

}
