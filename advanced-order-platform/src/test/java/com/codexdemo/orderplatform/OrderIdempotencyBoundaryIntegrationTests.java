package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codexdemo.orderplatform.catalog.Product;
import com.codexdemo.orderplatform.catalog.ProductRepository;
import com.codexdemo.orderplatform.order.CreateOrderLineRequest;
import com.codexdemo.orderplatform.order.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {"order.expiration.enabled=false", "outbox.publisher.enabled=false"})
@AutoConfigureMockMvc
class OrderIdempotencyBoundaryIntegrationTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private ProductRepository productRepository;

  @Test
  void createOrderReturnsCreatedThenOkForSameIdempotencyKeyAndSameRequest() throws Exception {
    Product product = productRepository.findAll().getFirst();
    CreateOrderRequest request =
        new CreateOrderRequest(
            UUID.fromString("14141414-1414-1414-1414-141414141414"),
            List.of(new CreateOrderLineRequest(product.getId(), 1)));
    String body = objectMapper.writeValueAsString(request);

    mockMvc
        .perform(
            post("/api/v1/orders")
                .header("Idempotency-Key", "http-idempotency-key-001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", matchesPattern("/api/v1/orders/\\d+")))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").value("CREATED"));

    mockMvc
        .perform(
            post("/api/v1/orders")
                .header("Idempotency-Key", "http-idempotency-key-001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.status").value("CREATED"));
  }

  @Test
  void createOrderReturnsConflictForSameIdempotencyKeyAndDifferentRequest() throws Exception {
    Product product = productRepository.findAll().getFirst();
    CreateOrderRequest firstRequest =
        new CreateOrderRequest(
            UUID.fromString("15151515-1515-1515-1515-151515151515"),
            List.of(new CreateOrderLineRequest(product.getId(), 1)));
    CreateOrderRequest differentRequest =
        new CreateOrderRequest(
            UUID.fromString("15151515-1515-1515-1515-151515151515"),
            List.of(new CreateOrderLineRequest(product.getId(), 2)));

    mockMvc
        .perform(
            post("/api/v1/orders")
                .header("Idempotency-Key", "http-idempotency-key-002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstRequest)))
        .andExpect(status().isCreated());

    mockMvc
        .perform(
            post("/api/v1/orders")
                .header("Idempotency-Key", "http-idempotency-key-002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(differentRequest)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.title").value("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"))
        .andExpect(
            jsonPath("$.detail")
                .value("Idempotency-Key was already used for a different create order request"));
  }

  @Test
  void createOrderRejectsInvalidBoundaryInputsBeforeBusinessHandling() throws Exception {
    Product product = productRepository.findAll().getFirst();

    mockMvc
        .perform(
            post("/api/v1/orders")
                .header("Idempotency-Key", " ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new CreateOrderRequest(
                            UUID.fromString("16161616-1616-1616-1616-161616161616"),
                            List.of(new CreateOrderLineRequest(product.getId(), 1))))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.fieldErrors[0]").exists());

    mockMvc
        .perform(
            post("/api/v1/orders")
                .header("Idempotency-Key", "http-idempotency-key-invalid-empty-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new CreateOrderRequest(
                            UUID.fromString("17171717-1717-1717-1717-171717171717"), List.of()))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.fieldErrors[0]").exists());

    mockMvc
        .perform(
            post("/api/v1/orders")
                .header("Idempotency-Key", "http-idempotency-key-invalid-quantity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new CreateOrderRequest(
                            UUID.fromString("18181818-1818-1818-1818-181818181818"),
                            List.of(new CreateOrderLineRequest(product.getId(), 0))))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.fieldErrors[0]").exists());

    List<CreateOrderLineRequest> tooManyItems =
        IntStream.range(0, 101)
            .mapToObj(ignored -> new CreateOrderLineRequest(product.getId(), 1))
            .toList();
    mockMvc
        .perform(
            post("/api/v1/orders")
                .header("Idempotency-Key", "http-idempotency-key-invalid-too-many-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        new CreateOrderRequest(
                            UUID.fromString("19191919-1919-1919-1919-191919191919"),
                            tooManyItems))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.fieldErrors[0]").exists());
  }
}
