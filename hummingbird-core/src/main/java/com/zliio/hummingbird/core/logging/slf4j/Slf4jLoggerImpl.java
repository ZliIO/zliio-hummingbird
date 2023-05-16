package com.zliio.hummingbird.core.logging.slf4j;

import com.zliio.hummingbird.core.logging.AbstractLogger;

/**
 *
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 */
class Slf4jLoggerImpl extends AbstractLogger {

  private final org.slf4j.Logger log;

  public Slf4jLoggerImpl(org.slf4j.Logger logger) {
    log = logger;
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
    log.error(s, e);
  }

  @Override
  public void error(String s) {
    log.error(s);
  }

  @Override
  public void info(String s) {
    log.info(s);
  }

  @Override
  public void debug(String s) {
    log.debug(s);
  }

  @Override
  public void trace(String s) {
    log.trace(s);
  }

  @Override
  public void warn(String s) {
    log.warn(s);
  }

}
