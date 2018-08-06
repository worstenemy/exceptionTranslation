package com.we.impl;

import com.we.ExceptionSupplier;
import com.we.ExceptionTranslator;
import com.we.TryBlock;
import com.we.TryResultBlock;

import java.util.Optional;

public class ExceptionTranslatorImpl implements ExceptionTranslator {
  private final ExceptionMappingImpl exceptionMapping;

  ExceptionTranslatorImpl(ExceptionMappingImpl exceptionMapping) {
    this.exceptionMapping = exceptionMapping;
  }

  @Override
  public void withException(TryBlock tryBlock) {
    try {
      tryBlock.run();
    } catch (Exception e) {
      handleException(e);
    }
  }

  @Override
  public <T> T withException(TryResultBlock<T> resultBlock) {
    try {
      return resultBlock.run();
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private <T> T handleException(Exception e) {
    Optional<ExceptionSupplier> supplier = this.exceptionMapping.getSupplier(e.getClass());
    if (supplier.isPresent()) {
      throw supplier.get().get(e.getMessage(), e.getCause());
    } else {
      throw new RuntimeException(e.getMessage(), e.getCause());
    }
  }
}