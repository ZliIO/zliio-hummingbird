package com.zliio.hummingbird.core.config.resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@FunctionalInterface
public interface ResourceLoader {
    Map<String,String> load(Path path) throws IOException;
}
