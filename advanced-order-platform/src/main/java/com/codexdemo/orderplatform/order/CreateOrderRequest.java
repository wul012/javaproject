package com.codexdemo.orderplatform.order;

import com.codexdemo.orderplatform.common.ImmutableLists;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
    @NotNull UUID customerId,
    @NotEmpty @Size(max = 100) List<@NotNull @Valid CreateOrderLineRequest> items) {

  public CreateOrderRequest {
    items = ImmutableLists.copy(items);
  }

  public List<CreateOrderLineRequest> items() {
    return ImmutableLists.copy(items);
  }
}
