package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.tracing.Tracer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest(properties = {"order.expiration.enabled=false", "outbox.publisher.enabled=false"})
class ObservabilityConfigurationTests {

  @Autowired private Environment environment;

  @Autowired private Tracer tracer;

  @Test
  void tracingBridgeAndLogCorrelationPatternAreConfigured() {
    assertThat(tracer).isNotNull();
    assertThat(environment.getProperty("management.tracing.sampling.probability")).isEqualTo("1.0");
    assertThat(environment.getProperty("logging.pattern.level"))
        .contains("traceId")
        .contains("spanId");
  }
}
