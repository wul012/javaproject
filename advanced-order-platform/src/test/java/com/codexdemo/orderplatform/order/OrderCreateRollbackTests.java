package com.codexdemo.orderplatform.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.catalog.Product;
import com.codexdemo.orderplatform.catalog.ProductRepository;
import com.codexdemo.orderplatform.common.BusinessException;
import com.codexdemo.orderplatform.inventory.InventoryItem;
import com.codexdemo.orderplatform.inventory.InventoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(
    properties = {
      "order.expiration.enabled=false",
      "outbox.publisher.enabled=false",
      "spring.datasource.url=jdbc:h2:mem:order-create-tx;MODE=PostgreSQL;"
          + "DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1"
    })
class OrderCreateRollbackTests {

  private static final List<String> TABLES =
      List.of(
          "orders",
          "order_lines",
          "inventory_movements",
          "outbox_events",
          "order_status_history",
          "payment_transactions");

  @Autowired private OrderApplicationService service;
  @Autowired private OrderRepository orders;
  @Autowired private ProductRepository products;
  @Autowired private InventoryRepository inventory;
  @Autowired private JdbcTemplate jdbc;

  @ParameterizedTest
  @ValueSource(booleans = {false, true})
  void stockFailureRollsBackAndAllowsRetry(boolean partial) {
    long first = addProduct(5);
    long second = addProduct(0);
    String key = "rollback-" + UUID.randomUUID();
    CreateOrderRequest request =
        new CreateOrderRequest(
            UUID.randomUUID(),
            List.of(
                new CreateOrderLineRequest(first, partial ? 1 : 6),
                new CreateOrderLineRequest(second, 1)));
    List<Long> counts = rowCounts();

    // No test transaction: each proxy call must commit or roll back on its own.
    assertThatThrownBy(() -> service.createOrder(key, request))
        .isInstanceOfSatisfying(
            BusinessException.class,
            failure -> assertThat(failure.getCode()).isEqualTo("INSUFFICIENT_STOCK"));

    assertThat(orders.findByIdempotencyKey(key)).isEmpty();
    assertThat(rowCounts()).containsExactlyElementsOf(counts);
    assertStock(first, 5, 0);
    assertStock(second, 0, 0);
    assertThat(inventory.findByProductId(first).orElseThrow().getVersion()).isZero();

    jdbc.update(
        "update inventory_items set available = 10 where product_id in (?, ?)", first, second);
    CreateOrderResult created = service.createOrder(key, request);
    assertThat(created.replayed()).isFalse();
    assertThat(orders.findByIdempotencyKey(key)).isPresent();
    assertStock(first, partial ? 9 : 4, partial ? 1 : 6);
    assertStock(second, 9, 1);
    List<Long> committed = rowCounts();
    assertThat(committed)
        .containsExactly(
            counts.get(0) + 1,
            counts.get(1) + 2,
            counts.get(2) + 2,
            counts.get(3) + 1,
            counts.get(4) + 1,
            counts.get(5));

    CreateOrderResult replay = service.createOrder(key, request);
    assertThat(replay.replayed()).isTrue();
    assertThat(replay.order().id()).isEqualTo(created.order().id());
    assertThat(rowCounts()).containsExactlyElementsOf(committed);
    assertStock(first, partial ? 9 : 4, partial ? 1 : 6);
    assertStock(second, 9, 1);
  }

  private long addProduct(int stock) {
    Product product =
        products.saveAndFlush(Product.create("tx-" + UUID.randomUUID(), "Item", BigDecimal.TEN));
    inventory.saveAndFlush(InventoryItem.create(product.getId(), stock));
    return product.getId();
  }

  private void assertStock(long productId, int available, int reserved) {
    InventoryItem item = inventory.findByProductId(productId).orElseThrow();
    assertThat(item.getAvailable()).isEqualTo(available);
    assertThat(item.getReserved()).isEqualTo(reserved);
  }

  private List<Long> rowCounts() {
    return TABLES.stream()
        .map(table -> jdbc.queryForObject("select count(*) from " + table, Long.class))
        .toList();
  }
}
