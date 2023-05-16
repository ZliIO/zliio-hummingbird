package com.zliio.hummingbird.core.config.resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * 基本的资源加载实现
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public abstract class AbstractResourceLoader implements ResourceLoader{
    @Override
    public Map<String, String> load(Path path) throws IOException {
        return loads(path);
    }


    protected abstract Map<String, String> loads(Path path) throws IOException;
}
