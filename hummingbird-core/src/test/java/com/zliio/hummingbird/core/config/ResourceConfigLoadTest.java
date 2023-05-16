package com.zliio.hummingbird.core.config;

import com.zliio.hummingbird.core.factory.BeanFactory;
import com.zliio.hummingbird.core.factory.ConfigurationFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 资源加载配置
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class ResourceConfigLoadTest {

    @BeforeAll
    public static void initResourceLoad(){
        ConfigurationFactory.loadResourceConfiguration(ResourceConfigLoadTest.class.getClassLoader(),new String[]{ResourceConfigLoadTest.class.getPackage().getName()});
    }

    @Test
    @DisplayName("Resource Get From Configuration Factory - String")
    public void propertiesResourceLoadFromFactoryTestString(){
        assertEquals(ConfigurationFactory.getDefaultConfig().getString("hummingbird.application.name"),"Hummingbird-Test");
    }

    @Test
    @DisplayName("Resource Get From Configuration Factory - ReLoad-String")
    public void propertiesResourceLoadFromFactoryTestReloadString(){
        assertEquals(ConfigurationFactory.getDefaultConfig().getString("Hummingbird.application.url"),"Hummingbird-URL");
    }

    @Test
    @DisplayName("Resource Get From Configuration Factory - Int")
    public void propertiesResourceLoadFromFactoryTestInt(){
        assertEquals(ConfigurationFactory.getDefaultConfig().getInt("hummingbird.application.numberInt"),123456);
    }

    @Test
    @DisplayName("Resource Get From Configuration Factory - Long")
    public void propertiesResourceLoadFromFactoryTestLong(){
        assertEquals(ConfigurationFactory.getDefaultConfig().getLong("hummingbird.application.numberLong"),1234567899876L);
    }

    @Test
    @DisplayName("Resource Get From Configuration Factory - Bool")
    public void propertiesResourceLoadFromFactoryTestBool(){
        assertEquals(ConfigurationFactory.getDefaultConfig().getBoolean("hummingbird.application.bool"),true);
    }

    @Test
    @DisplayName("Resource Get From Configuration Factory - Double")
    public void propertiesResourceLoadFromFactoryTestDouble(){
        assertEquals(ConfigurationFactory.getDefaultConfig().getDouble("hummingbird.application.numberDouble"),12345.6789);
    }


    @Test
    @DisplayName("Resource Get From Bean Container - String")
    public void propertiesResourceLoadFromBeanContainerTestString(){
        Configuration configuration =(Configuration)BeanFactory.getInstanceByClazz(ConfigurationManager.class);
        assertEquals(configuration.getString("hummingbird.application.name"),"hummingbird-Test");
    }

    @Test
    @DisplayName("Resource Get From Bean Container - ReLoad-String")
    public void propertiesResourceLoadFromBeanContainerTestReloadString(){
        Configuration configuration =(Configuration)BeanFactory.getInstanceByClazz(ConfigurationManager.class);
        assertEquals(configuration.getString("hummingbird.application.url"),"Hummingbird-URL");
    }

    @Test
    @DisplayName("Resource Get From Bean Container - Int")
    public void propertiesResourceLoadFromBeanContainerTestInt(){
        Configuration configuration =(Configuration)BeanFactory.getInstanceByClazz(ConfigurationManager.class);
        assertEquals(configuration.getInt("hummingbird.application.numberInt"),123456);
    }

    @Test
    @DisplayName("Resource Get From Bean Container - Long")
    public void propertiesResourceLoadFromBeanContainerTestLong(){
        Configuration configuration =(Configuration)BeanFactory.getInstanceByClazz(ConfigurationManager.class);
        assertEquals(configuration.getLong("hummingbird.application.numberLong"),1234567899876L);
    }

    @Test
    @DisplayName("Resource Get From Bean Container - Bool")
    public void propertiesResourceLoadFromBeanContainerTestBool(){
        Configuration configuration =(Configuration)BeanFactory.getInstanceByClazz(ConfigurationManager.class);
        assertEquals(configuration.getBoolean("hummingbird.application.bool"),true);
    }

    @Test
    @DisplayName("Resource Get From Bean Container - Double")
    public void propertiesResourceLoadFromBeanContainerTestDouble(){
        Configuration configuration =(Configuration)BeanFactory.getInstanceByClazz(ConfigurationManager.class);
        assertEquals(configuration.getDouble("hummingbird.application.numberDouble"),12345.6789);
    }
}
