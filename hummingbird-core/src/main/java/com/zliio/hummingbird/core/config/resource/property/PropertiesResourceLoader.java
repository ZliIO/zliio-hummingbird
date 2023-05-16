package com.zliio.hummingbird.core.config.resource.property;

import com.zliio.hummingbird.core.config.resource.AbstractResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * props的参数配置实现
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class PropertiesResourceLoader extends AbstractResourceLoader {

    @Override
    protected Map<String, String> loads(Path path) throws IOException {
        Properties properties = new Properties();
        try (InputStream stream = Files.newInputStream(path); Reader reader = new InputStreamReader(stream)) {
            properties.load(reader);
        }
        Map<String, String> resource = new HashMap<>(properties.size());
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            resource.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return resource;
    }
}
