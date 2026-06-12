package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"order.expiration.enabled=false", "outbox.publisher.enabled=false"})
class ActuatorHealthIntegrationTests {

  private static final ParameterizedTypeReference<Map<String, Object>> MAP_RESPONSE =
      new ParameterizedTypeReference<>() {};

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void defaultHealthEndpointStaysUpWhenRabbitMqFeaturesAreDisabled() {
    ResponseEntity<Map<String, Object>> health = get("/actuator/health");
    ResponseEntity<Map<String, Object>> liveness = get("/actuator/health/liveness");
    ResponseEntity<Map<String, Object>> readiness = get("/actuator/health/readiness");

    assertThat(health.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(health.getBody()).containsEntry("status", "UP");
    assertThat(liveness.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(liveness.getBody()).containsEntry("status", "UP");
    assertThat(readiness.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(readiness.getBody()).containsEntry("status", "UP");
  }

  @Test
  void infoAndMetricsEndpointsStayExposedForRuntimeDiagnostics() {
    ResponseEntity<Map<String, Object>> info = get("/actuator/info");
    ResponseEntity<Map<String, Object>> metrics = get("/actuator/metrics");

    assertThat(info.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(info.getBody()).isNotNull();
    assertThat(metrics.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(metrics.getBody()).containsKey("names");
    assertThat(((List<?>) metrics.getBody().get("names")).stream().map(Object::toString).toList())
        .isNotEmpty()
        .contains("jvm.memory.used");
  }

  private ResponseEntity<Map<String, Object>> get(String path) {
    return restTemplate.exchange(path, HttpMethod.GET, null, MAP_RESPONSE);
  }
}
