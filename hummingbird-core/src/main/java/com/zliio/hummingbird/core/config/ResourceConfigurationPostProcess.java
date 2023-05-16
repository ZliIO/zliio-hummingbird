package com.zliio.hummingbird.core.config;

import java.util.Map;

/**
 * 配置加载完成后的后处理
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
@FunctionalInterface
public interface ResourceConfigurationPostProcess {
    Map<String,String> processingHandler(Map<String, String> configurations);

    default int getOrder(){
        return 1;
    }
}
