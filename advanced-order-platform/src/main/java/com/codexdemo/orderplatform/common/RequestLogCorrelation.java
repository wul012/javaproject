package com.codexdemo.orderplatform.common;

import org.slf4j.MDC;

final class RequestLogCorrelation {

  private static final String UNAVAILABLE = "unavailable";

  private RequestLogCorrelation() {}

  static Correlation current() {
    return new Correlation(mdcValue("traceId"), mdcValue("spanId"));
  }

  private static String mdcValue(String key) {
    String value = MDC.get(key);
    if (value == null || value.isBlank()) {
      return UNAVAILABLE;
    }
    return value;
  }

  record Correlation(String traceId, String spanId) {}
}
