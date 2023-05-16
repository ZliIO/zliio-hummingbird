package com.zliio.hummingbird.core.logging;

import com.zliio.hummingbird.core.exception.ConfigClassInitException;
import com.zliio.hummingbird.core.logging.log4j.Logger4JImpl;
import com.zliio.hummingbird.core.logging.log4j2.Logger4J2Impl;
import com.zliio.hummingbird.core.logging.commons.JakartaCommonsLoggingImpl;
import com.zliio.hummingbird.core.logging.jdk14.Jdk14LoggingImpl;
import com.zliio.hummingbird.core.logging.nologging.NoLoggingImpl;
import com.zliio.hummingbird.core.logging.slf4j.Slf4jImpl;
import com.zliio.hummingbird.core.logging.stdout.StdOutImpl;

import java.lang.reflect.Constructor;

/**
 * LogFactory 这里的处理方式来自Mybatis-3
 *
 * @author Clinton Begin
 * @author Eduardo Macarron
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public final class LoggerFactory {
    /**
     * Marker to be used by logging implementations that support markers.
     */
    public static final String MARKER = "Hummingbird";

    private static Constructor<? extends Logger> logConstructor;

    static {
        tryImplementation(LoggerFactory::useSlf4jLogging);
        tryImplementation(LoggerFactory::useCommonsLogging);
        tryImplementation(LoggerFactory::useLog4J2Logging);
        tryImplementation(LoggerFactory::useLog4JLogging);
        tryImplementation(LoggerFactory::useJdkLogging);
        tryImplementation(LoggerFactory::useStdOutLogging);
        tryImplementation(LoggerFactory::useNoLogging);
    }

    private LoggerFactory() {
        // disable construction
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static Logger getLogger(String logger) {
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new ConfigClassInitException("Error creating logger for logger " + logger + ".  Cause: " + t, t);
        }
    }

    public static synchronized void useCustomLogging(Class<? extends Logger> clazz) {
        setImplementation(clazz);
    }

    public static synchronized void useSlf4jLogging() {
        setImplementation(Slf4jImpl.class);
    }

    public static synchronized void useCommonsLogging() {
        setImplementation(JakartaCommonsLoggingImpl.class);
    }

    public static synchronized void useLog4JLogging() {
        setImplementation(Logger4JImpl.class);
    }

    public static synchronized void useLog4J2Logging() {
        setImplementation(Logger4J2Impl.class);
    }

    public static synchronized void useJdkLogging() {
        setImplementation(Jdk14LoggingImpl.class);
    }

    public static synchronized void useStdOutLogging() {
        setImplementation(StdOutImpl.class);
    }

    public static synchronized void useNoLogging() {
        setImplementation(NoLoggingImpl.class);
    }

    private static void tryImplementation(Runnable runnable) {
        if (logConstructor == null) {
            try {
                runnable.run();
            } catch (Throwable ignore) {
                // ignore
            }
        }
    }

    private static void setImplementation(Class<? extends Logger> implClass) {
        try {
            Constructor<? extends Logger> candidate = implClass.getConstructor(String.class);
            Logger log = candidate.newInstance(LoggerFactory.class.getName());
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized using '" + implClass + "' adapter.");
            }
            logConstructor = candidate;
        } catch (Throwable t) {
            throw new ConfigClassInitException("Error setting Log implementation.  Cause: " + t, t);
        }
    }

}
