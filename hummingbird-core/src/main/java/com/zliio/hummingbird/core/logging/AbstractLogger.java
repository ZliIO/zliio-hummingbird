package com.zliio.hummingbird.core.logging;

import com.zliio.hummingbird.core.util.BracePlaceholder;

/**
 * BaseLogger
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public abstract class AbstractLogger implements Logger{

    @Override
    public void error(String content, Object[] values, Throwable e) {
        this.error(BracePlaceholder.resolve(content,values),e);
    }

    @Override
    public void error(String content, Object... values) {
        this.error(BracePlaceholder.resolve(content,values));
    }

    @Override
    public void info(String content, Object... values) {
        this.info(BracePlaceholder.resolve(content,values));
    }

    @Override
    public void debug(String content, Object... values) {
        this.debug(BracePlaceholder.resolve(content,values));
    }

    @Override
    public void trace(String content, Object... values) {
        this.trace(BracePlaceholder.resolve(content,values));
    }

    @Override
    public void warn(String content, Object... values) {
        this.warn(BracePlaceholder.resolve(content,values));
    }
}
