package com.we;

public interface TryResultBlock<T> {
  T run() throws Exception;
}