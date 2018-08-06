package com.we;

public interface ExceptionConfiguration {
  ExceptionMapping translate(Class<? extends Exception> from);

  ExceptionTranslator done();
}