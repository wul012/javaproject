package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"order.expiration.enabled=false", "outbox.publisher.enabled=false"})
class ApiExceptionTraceIntegrationTests {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void validationExceptionLogIncludesRequestTraceContext(CapturedOutput output) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Idempotency-Key", " ");
    String request =
        """
        {
          "customerId": "11111111-1111-1111-1111-111111111111",
          "items": [
            {"productId": 1, "quantity": 1}
          ]
        }
        """;

    ResponseEntity<String> response =
        restTemplate.postForEntity(
            "/api/v1/orders", new HttpEntity<>(request, headers), String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(output.getAll())
        .contains("constraint validation failed")
        .containsPattern("traceId=[0-9a-f]{32}")
        .containsPattern("spanId=[0-9a-f]{16}")
        .doesNotContain("traceId=unavailable")
        .doesNotContain("spanId=unavailable");
  }
}
