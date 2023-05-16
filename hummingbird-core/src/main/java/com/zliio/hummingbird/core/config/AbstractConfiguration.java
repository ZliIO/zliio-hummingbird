package com.zliio.hummingbird.core.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的配置实现
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public abstract class AbstractConfiguration implements Configuration{

    private static final Map<String, String> CONFIGURATION_CACHE = new ConcurrentHashMap<>(64);

    @Override
    public int getInt(String key) {
        String result = CONFIGURATION_CACHE.get(key);
        return Integer.parseInt(result);
    }

    @Override
    public String getString(String key) {
        return CONFIGURATION_CACHE.get(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        String result = CONFIGURATION_CACHE.get(key);
        return Boolean.parseBoolean(result);
    }

    @Override
    public Long getLong(String key) {
        String result = CONFIGURATION_CACHE.get(key);
        return Long.parseLong(result);
    }

    @Override
    public Double getDouble(String key) {
        String result = CONFIGURATION_CACHE.get(key);
        return Double.parseDouble(result);
    }

    @Override
    public Map<String, String> getAll() {
        return CONFIGURATION_CACHE;
    }

    @Override
    public void put(String key, String content) {
        CONFIGURATION_CACHE.put(key, content);
    }

    @Override
    public void putAll(Map<String, String> maps) {
        CONFIGURATION_CACHE.putAll(maps);
    }
}
