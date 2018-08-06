package com.we.impl;

import com.we.ExceptionConfiguration;
import com.we.ExceptionMapping;
import com.we.ExceptionTranslator;

public class ExceptionConfigurationImpl implements ExceptionConfiguration {
  private final ExceptionMappingImpl mapping;


  ExceptionConfigurationImpl() {
    this.mapping = new ExceptionMappingImpl(this);
  }

  @Override
  public ExceptionMapping translate(Class<? extends Exception> from) {
    this.mapping.setException(from);
    return this.mapping;
  }

  @Override
  public ExceptionTranslator done() {
    this.mapping.check();
    return new ExceptionTranslatorImpl(this.mapping);
  }
}