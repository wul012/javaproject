package com.codexdemo.orderplatform.notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

final class FailedEventCommandSupport {

  private FailedEventCommandSupport() {}

  static FailedEventOperatorContext requireOperatorContext(
      FailedEventOperatorContext operatorContext) {
    if (operatorContext == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "operator context is required");
    }
    return operatorContext;
  }

  static String firstNonBlank(String... values) {
    for (String value : values) {
      if (value != null && !value.isBlank()) {
        return value;
      }
    }
    return null;
  }

  static String truncate(String value, int maxLength) {
    if (value == null || value.length() <= maxLength) {
      return value;
    }
    return value.substring(0, maxLength);
  }
}
