package com.zliio.hummingbird.core.factory;

import com.zliio.hummingbird.core.common.BeanHelper;
import com.zliio.hummingbird.core.config.Configuration;
import com.zliio.hummingbird.core.config.ConfigurationManager;
import com.zliio.hummingbird.core.config.DefaultConfiguration;
import com.zliio.hummingbird.core.config.ResourceConfigurationPostProcess;
import com.zliio.hummingbird.core.util.ReflectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 配置需要的相关管理器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class ConfigurationFactory {
    private static final String CONFIG_SYSTEM_PROPERTY_KEY = "-D";
    private static final String CONFIG_APPLICATION_PROPERTY_KEY = "-T";
    private static final String CONFIG_PROPERTY_MAPPER_TAG = "=";

    private static List<ResourceConfigurationPostProcess> resourceConfigurationPostProcessList = new LinkedList<>();

    public static Configuration getDefaultConfig() {
        return SingleConfigurationHolder.INSTANCE_CONFIGURATION;
    }


    public static void loadResourceConfiguration(ClassLoader classLoader,String[] loadPackageNames){
        ConfigurationManager configurationManager = SingleConfigurationHolder.INSTANCE_CONFIGURATION_MANAGER;
        BeanFactory.insertBean(BeanHelper.getBeanName(ConfigurationManager.class),configurationManager);
        initResourceConfigurationPostProcessList(loadPackageNames);
        configurationManager.loadConfigurationResources(classLoader);
    }


    private static void initResourceConfigurationPostProcessList(String[] loadPackageNames){
        Set<Class<? extends ResourceConfigurationPostProcess>> resourceConfigurationPostProcessList
                = ReflectionUtils.getSubClass(loadPackageNames, ResourceConfigurationPostProcess.class);
        if(null != resourceConfigurationPostProcessList && 0 != resourceConfigurationPostProcessList.size()){
            for (Class<? extends ResourceConfigurationPostProcess>  resourceConfigurationPostProcessClazz: resourceConfigurationPostProcessList) {
                ResourceConfigurationPostProcess resourceConfigurationPostProcess = (ResourceConfigurationPostProcess)
                        BeanFactory.getInstanceByClazz(resourceConfigurationPostProcessClazz);
                if(null == resourceConfigurationPostProcess){
                    resourceConfigurationPostProcess = ReflectionUtils.newInstance(resourceConfigurationPostProcessClazz);
                }
                ConfigurationFactory.resourceConfigurationPostProcessList.add(resourceConfigurationPostProcess);
                BeanFactory.insertBean(BeanHelper.getBeanName(resourceConfigurationPostProcessClazz),resourceConfigurationPostProcess);
            }
            ConfigurationFactory.resourceConfigurationPostProcessList = ConfigurationFactory.resourceConfigurationPostProcessList.stream()
                    .sorted(Comparator.comparing(ResourceConfigurationPostProcess::getOrder)).collect(Collectors.toList());
        }
    }

    public static Map<String, String> runResourceConfigurationPostProcess(Map<String, String> resourceConfigurationMap){
        for (ResourceConfigurationPostProcess resourceConfigurationPostProcess : resourceConfigurationPostProcessList) {
            resourceConfigurationMap = resourceConfigurationPostProcess.processingHandler(resourceConfigurationMap);
        }
        return resourceConfigurationMap;
    }

    public static void loadBootstrapConfiguration(String... args) {
        ConfigurationManager configurationManager = SingleConfigurationHolder.INSTANCE_CONFIGURATION_MANAGER;
        loadSystemPropertyFromBootstrapConfig(args);
        Map<String, String> configMap = analyzeApplicationBootstrapConfiguration(args);
        configurationManager.supplementApplicationConfigurations(configMap);
    }

    private static Map<String, String> analyzeApplicationBootstrapConfiguration(String[] args) {
        Map<String, String> configurations = new HashMap<>(16);
        for (String arg : args) {
            assert null != arg;
            if(arg.contains(CONFIG_PROPERTY_MAPPER_TAG) && arg.startsWith(CONFIG_APPLICATION_PROPERTY_KEY)){
                String[] config = arg.substring(2).split(CONFIG_PROPERTY_MAPPER_TAG);
                String propertyKey = config[0];
                String propertyValue = config[1];
                if(null != propertyKey && !"".equals(propertyKey)
                        && null != propertyValue && !"".equals(propertyValue)){
                    configurations.put(propertyKey.toLowerCase(),propertyValue.toLowerCase());
                }
            }
        }
        return configurations;
    }


    private static void loadSystemPropertyFromBootstrapConfig(String... args){
        for (String arg : args) {
            assert null != arg;
            if(arg.contains(CONFIG_PROPERTY_MAPPER_TAG) && arg.startsWith(CONFIG_SYSTEM_PROPERTY_KEY)){
                String[] config = arg.substring(2).split(CONFIG_PROPERTY_MAPPER_TAG);
                String systemPropertyKey = config[0];
                String systemPropertyValue = config[1];
                if(null != systemPropertyKey && !"".equals(systemPropertyKey)
                        && null != systemPropertyValue && !"".equals(systemPropertyValue)){
                    System.setProperty(systemPropertyKey.toLowerCase(),systemPropertyValue.toLowerCase());
                }
            }
        }
    }

    private static class SingleConfigurationHolder {

        private static final Configuration INSTANCE_CONFIGURATION = buildConfiguration();

        private static final ConfigurationManager INSTANCE_CONFIGURATION_MANAGER = buildConfigurationManager();

        private static Configuration buildConfiguration() {
            return new DefaultConfiguration();
        }

        private static ConfigurationManager buildConfigurationManager() {
            return new ConfigurationManager(INSTANCE_CONFIGURATION);
        }
    }
}
