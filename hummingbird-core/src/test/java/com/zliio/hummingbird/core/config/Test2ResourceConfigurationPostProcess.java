package com.zliio.hummingbird.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 测试配置后处理器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class Test2ResourceConfigurationPostProcess implements ResourceConfigurationPostProcess{
    private static final Logger logger = LoggerFactory.getLogger( Test2ResourceConfigurationPostProcess.class);

    @Override
    public Map<String, String> processingHandler(Map<String, String> resourceMap) {
        logger.info("Test2ResourceConfigurationPostProcess Handler Running ....");
        return resourceMap;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
