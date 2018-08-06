package com.we.impl;

import com.we.ExceptionConfiguration;
import com.we.ExceptionMapping;
import com.we.ExceptionSupplier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExceptionMappingImpl implements ExceptionMapping {
  private final ExceptionConfigurationImpl configuration;

  private final Map<Class<? extends Exception>, ExceptionSupplier> exceptionMapping =
    new HashMap<>(4);

  private Class<? extends Exception> exception;

  private int exceptionCount = 0;

  private int supplierCount = 0;

  ExceptionMappingImpl(ExceptionConfigurationImpl configuration) {
    this.configuration = configuration;
  }

  void setException(Class<? extends Exception> exception) {
    this.exception = exception;
    ++exceptionCount;
  }

  Map<Class<? extends Exception>, ExceptionSupplier> getExceptionMapping() {
    return exceptionMapping;
  }

  @Override
  public ExceptionConfiguration to(ExceptionSupplier to) {
    if (++this.supplierCount != this.exceptionCount) {
      throw new IllegalArgumentException("less supplier");
    }
    this.exceptionMapping.putIfAbsent(this.exception, to);
    this.exception = null;
    return this.configuration;
  }

  void check() {
    if (this.supplierCount != this.exceptionCount) {
      throw new IllegalArgumentException("exception class and exception supplier is not equal");
    }
  }

  @SuppressWarnings("unchecked")
  Optional<ExceptionSupplier> getSupplier(Class<? extends Exception> from) {
    ExceptionSupplier supplier = this.exceptionMapping.get(from);
    if (null == supplier) {
      Class<?> parent = from.getSuperclass();
      while (Exception.class.isAssignableFrom(parent) && null == supplier) {
        supplier = this.exceptionMapping.get((Class<? extends Exception>) parent);
        parent = parent.getSuperclass();
      }
    }
    return Optional.ofNullable(supplier);
  }
}