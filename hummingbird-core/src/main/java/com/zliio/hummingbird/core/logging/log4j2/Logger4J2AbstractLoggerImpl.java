package com.zliio.hummingbird.core.logging.log4j2;

import com.zliio.hummingbird.core.logging.LoggerFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

/**
 *
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 */
public class Logger4J2AbstractLoggerImpl extends com.zliio.hummingbird.core.logging.AbstractLogger {

  private static final Marker MARKER = MarkerManager.getMarker(LoggerFactory.MARKER);

  private static final String FQCN = Logger4J2Impl.class.getName();

  private final ExtendedLoggerWrapper log;

  public Logger4J2AbstractLoggerImpl(AbstractLogger abstractLogger) {
    log = new ExtendedLoggerWrapper(abstractLogger, abstractLogger.getName(), abstractLogger.getMessageFactory());
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  @Override
  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }

  @Override
  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  @Override
  public void error(String s, Throwable e) {
    log.logIfEnabled(FQCN, Level.ERROR, MARKER, (Message) new SimpleMessage(s), e);
  }

  @Override
  public void error(String s) {
    log.logIfEnabled(FQCN, Level.ERROR, MARKER, (Message) new SimpleMessage(s), null);
  }

  @Override
  public void info(String s) {
    log.info(s);
  }

  @Override
  public void debug(String s) {
    log.logIfEnabled(FQCN, Level.DEBUG, MARKER, (Message) new SimpleMessage(s), null);
  }

  @Override
  public void trace(String s) {
    log.logIfEnabled(FQCN, Level.TRACE, MARKER, (Message) new SimpleMessage(s), null);
  }

  @Override
  public void warn(String s) {
    log.logIfEnabled(FQCN, Level.WARN, MARKER, (Message) new SimpleMessage(s), null);
  }

}
