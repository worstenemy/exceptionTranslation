package com.we;

public interface ExceptionTranslator {
  void withException(TryBlock tryBlock);

  <T> T withException(TryResultBlock<T> resultBlock);
}