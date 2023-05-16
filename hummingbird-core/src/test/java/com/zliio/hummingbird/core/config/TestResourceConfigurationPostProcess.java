package com.zliio.hummingbird.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Map;

/**
 * 测试配置后处理器
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class TestResourceConfigurationPostProcess implements ResourceConfigurationPostProcess{
    private static final Logger logger = LoggerFactory.getLogger( TestResourceConfigurationPostProcess.class);

    @Override
    public Map<String, String> processingHandler(Map<String, String> resourceMap) {
        String url = resourceMap.get("Hummingbird.application.url");
        resourceMap.put("Hummingbird.application.url", new String(Base64.getDecoder().decode(url)));
        logger.info("TestResourceConfigurationPostProcess Handler Running ....");
        return resourceMap;
    }
}
