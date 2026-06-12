package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest(
    properties = {
      "spring.profiles.active=prod",
      "order.expiration.enabled=false",
      "outbox.publisher.enabled=false"
    })
class ProductionProfileConfigurationTests {

  @Autowired private Environment environment;

  @Test
  void prodProfileDisablesLocalDebugSurfacesAndEnablesGracefulShutdown() {
    assertThat(environment.getProperty("spring.h2.console.enabled", Boolean.class)).isFalse();
    assertThat(environment.getProperty("spring.jpa.show-sql", Boolean.class)).isFalse();
    assertThat(environment.getProperty("spring.jpa.properties.hibernate.format_sql", Boolean.class))
        .isFalse();
    assertThat(environment.getProperty("server.shutdown")).isEqualTo("graceful");
    assertThat(environment.getProperty("spring.lifecycle.timeout-per-shutdown-phase"))
        .isEqualTo("30s");
    assertThat(environment.getProperty("management.endpoint.health.probes.enabled", Boolean.class))
        .isTrue();
    assertThat(environment.getProperty("management.health.rabbit.enabled", Boolean.class))
        .isFalse();
  }
}
