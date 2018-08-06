package com.we.impl;

import com.we.ExceptionConfiguration;

public class ExceptionHelper {
  public static ExceptionConfiguration newTranslator() {
    return new ExceptionConfigurationImpl();
  }
}