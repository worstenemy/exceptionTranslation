package com.we.impl;

import com.we.ExceptionConfiguration;
import com.we.ExceptionMapping;
import com.we.ExceptionSupplier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExceptionMappingImpl implements ExceptionMapping {
  private final ExceptionConfigurationImpl configuration;

  private final Map<Class<?>, ExceptionSupplier> exceptionMapping =
    new HashMap<>(4);

  private Class<?> exception;

  private int counter = 0;

  ExceptionMappingImpl(ExceptionConfigurationImpl configuration) {
    this.configuration = configuration;
  }

  void setException(Class<? extends Exception> exception) {
    this.exception = exception;
    ++counter;
  }

  @Override
  public ExceptionConfiguration to(ExceptionSupplier to) {
    if (--counter != 0) {
      throw new IllegalArgumentException("less supplier");
    }
    this.exceptionMapping.putIfAbsent(this.exception, to);
    this.exception = null;
    return this.configuration;
  }

  void check() {
    if (this.counter != 0) {
      throw new IllegalArgumentException("exception class and exception supplier is not equal");
    }
  }

  Optional<ExceptionSupplier> getSupplier(Class<? extends Exception> from) {
    ExceptionSupplier supplier = this.exceptionMapping.get(from);
    if (null == supplier) {
      Class<?> parent = from.getSuperclass();
      while (Exception.class.isAssignableFrom(parent) && null == supplier) {
        supplier = this.exceptionMapping.get(parent);
        parent = parent.getSuperclass();
      }
    }
    return Optional.ofNullable(supplier);
  }
}