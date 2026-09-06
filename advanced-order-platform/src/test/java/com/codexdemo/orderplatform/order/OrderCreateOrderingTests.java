package com.codexdemo.orderplatform.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.codexdemo.orderplatform.catalog.Product;
import com.codexdemo.orderplatform.catalog.ProductRepository;
import com.codexdemo.orderplatform.inventory.InventoryService;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import com.codexdemo.orderplatform.payment.PaymentService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

class OrderCreateOrderingTests {

  private final OrderRepository orders = mock(OrderRepository.class);
  private final IdempotencyStore store = mock(IdempotencyStore.class);
  private final ProductRepository products = mock(ProductRepository.class);
  private final InventoryService inventory = mock(InventoryService.class);
  private final OutboxRepository outbox = mock(OutboxRepository.class);
  private final OrderStatusHistoryRepository history = mock(OrderStatusHistoryRepository.class);
  private final PaymentService payments = mock(PaymentService.class);
  private final OrderApplicationService service =
      new OrderApplicationService(orders, store, products, inventory, outbox, history, payments);
  private final CreateOrderRequest request =
      new CreateOrderRequest(
          UUID.fromString("20202020-2020-2020-2020-202020202020"),
          List.of(new CreateOrderLineRequest(1L, 1)));

  @BeforeEach
  void arrangeNewOrder() {
    Product product = Product.create("sku-1", "Keyboard", BigDecimal.valueOf(99));
    ReflectionTestUtils.setField(product, "id", 1L);
    when(store.findByKey("ordering-key-001")).thenReturn(Optional.empty());
    when(products.findAllById(any())).thenReturn(List.of(product));
    when(store.saveNewOrder(any()))
        .thenAnswer(
            call -> {
              SalesOrder order = call.getArgument(0);
              ReflectionTestUtils.setField(order, "id", 101L);
              return order;
            });
  }

  @Test
  void insertFailureStopsSideEffects() {
    var failure = new DataIntegrityViolationException("duplicate key");
    doThrow(failure).when(store).saveNewOrder(any());
    assertThatThrownBy(() -> service.createOrder("ordering-key-001", request)).isSameAs(failure);
    verifyNoInteractions(inventory, outbox, history, payments);
  }

  @Test
  void insertPrecedesReserveAndEvidence() {
    CreateOrderResult result = service.createOrder("ordering-key-001", request);
    var sequence = inOrder(store, inventory, outbox, history);
    sequence.verify(store).saveNewOrder(any());
    sequence.verify(inventory).reserve(Map.of(1L, 1));
    sequence.verify(outbox).save(any());
    sequence.verify(history).save(any());
    assertThat(result.replayed()).isFalse();
    assertThat(result.order().id()).isEqualTo(101L);
    verifyNoInteractions(payments);
  }

  @Test
  void reserveFailureStopsEvidence() {
    var failure = new IllegalStateException("inventory unavailable");
    doThrow(failure).when(inventory).reserve(any());
    assertThatThrownBy(() -> service.createOrder("ordering-key-001", request)).isSameAs(failure);
    verify(store).saveNewOrder(any());
    verifyNoInteractions(outbox, history, payments);
  }
}
