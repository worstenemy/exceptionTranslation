package com.we.impl;

import com.we.ExceptionConfiguration;
import com.we.ExceptionMapping;
import com.we.ExceptionSupplier;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMappingImpl implements ExceptionMapping {
  private final ExceptionConfigurationImpl configuration;

  private final Map<Class<? extends Exception>, ExceptionSupplier> exceptionMapping =
    new HashMap<>(4);

  private Class<? extends Exception> exception;

  private int exceptionCount = 0;

  private int supplierCount = 0;

  void setException(Class<? extends Exception> exception) {
    this.exception = exception;
    ++exceptionCount;
  }

  Map<Class<? extends Exception>, ExceptionSupplier> getExceptionMapping() {
    return exceptionMapping;
  }

  ExceptionMappingImpl(ExceptionConfigurationImpl configuration) {
    this.configuration = configuration;
  }

  @Override
  public ExceptionConfiguration to(ExceptionSupplier to) {
    if (this.supplierCount + 1 != this.exceptionCount) {
      throw new IllegalArgumentException("less supplier");
    }
    ++supplierCount;
    this.exceptionMapping.putIfAbsent(this.exception, to);
    this.exception = null;
    return this.configuration;
  }

  void check() {
    if (this.supplierCount != this.exceptionCount) {
      throw new IllegalArgumentException("exception class and exception supplier is not equal");
    }
  }
}