package com.codexdemo.orderplatform.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

class RequestLogCorrelationTests {

  @AfterEach
  void clearMdc() {
    MDC.clear();
  }

  @Test
  void readsTraceAndSpanIdsFromMdc() {
    MDC.put("traceId", "trace-j4");
    MDC.put("spanId", "span-j4");

    RequestLogCorrelation.Correlation correlation = RequestLogCorrelation.current();

    assertThat(correlation.traceId()).isEqualTo("trace-j4");
    assertThat(correlation.spanId()).isEqualTo("span-j4");
  }

  @Test
  void fallsBackWhenNoTraceContextIsAvailable() {
    RequestLogCorrelation.Correlation correlation = RequestLogCorrelation.current();

    assertThat(correlation.traceId()).isEqualTo("unavailable");
    assertThat(correlation.spanId()).isEqualTo("unavailable");
  }
}
