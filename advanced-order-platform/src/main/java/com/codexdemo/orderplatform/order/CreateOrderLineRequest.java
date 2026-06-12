package com.codexdemo.orderplatform.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderLineRequest(@NotNull @Positive Long productId, @Positive int quantity) {}
