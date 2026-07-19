package com.codexdemo.orderplatform.order;

import com.codexdemo.orderplatform.common.ImmutableLists;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
    Long id,
    UUID customerId,
    OrderStatus status,
    BigDecimal totalAmount,
    Instant createdAt,
    Instant paidAt,
    Instant shippedAt,
    Instant completedAt,
    Instant refundedAt,
    Instant canceledAt,
    List<OrderLineResponse> lines) {

  public OrderResponse {
    lines = ImmutableLists.copy(lines);
  }

  public List<OrderLineResponse> lines() {
    return ImmutableLists.copy(lines);
  }

  static OrderResponse from(SalesOrder order) {
    return new OrderResponse(
        order.getId(),
        order.getCustomerId(),
        order.getStatus(),
        order.getTotalAmount(),
        order.getCreatedAt(),
        order.getPaidAt(),
        order.getShippedAt(),
        order.getCompletedAt(),
        order.getRefundedAt(),
        order.getCanceledAt(),
        order.getLines().stream().map(OrderLineResponse::from).toList());
  }
}
