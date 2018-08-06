package com.we;

@FunctionalInterface
public interface ExceptionSupplier {
  RuntimeException get(String message, Throwable cause);
}