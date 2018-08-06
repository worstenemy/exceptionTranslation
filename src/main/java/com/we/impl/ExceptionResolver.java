package com.we.impl;

import com.we.ExceptionSupplier;

import java.util.Map;
import java.util.Optional;

public class ExceptionResolver {
  private final Map<Class<? extends Exception>, ExceptionSupplier> exceptionMapping;

  ExceptionResolver(Map<Class<? extends Exception>, ExceptionSupplier> exceptionMapping) {
    this.exceptionMapping = exceptionMapping;
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