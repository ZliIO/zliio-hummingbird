package com.zliio.hummingbird.core.logging.stdout;


import com.zliio.hummingbird.core.logging.AbstractLogger;

/**
 *
 * @author Clinton Begin
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 */
public class StdOutImpl extends AbstractLogger {

  public StdOutImpl(String clazz) {
    // Do Nothing
  }

  @Override
  public boolean isDebugEnabled() {
    return true;
  }

  @Override
  public boolean isTraceEnabled() {
    return true;
  }

  @Override
  public boolean isInfoEnabled() {
    return true;
  }

  @Override
  public void error(String s, Throwable e) {
    System.err.println(s);
    e.printStackTrace(System.err);
  }

  @Override
  public void error(String s) {
    System.err.println(s);
  }

  @Override
  public void debug(String s) {
    System.out.println(s);
  }

  @Override
  public void info(String s) {
    System.out.println(s);
  }

  @Override
  public void trace(String s) {
    System.out.println(s);
  }

  @Override
  public void warn(String s) {
    System.out.println(s);
  }
}
