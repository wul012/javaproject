package com.codexdemo.orderplatform.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.order.CreateOrderLineRequest;
import com.codexdemo.orderplatform.order.CreateOrderRequest;
import com.codexdemo.orderplatform.order.OrderLineResponse;
import com.codexdemo.orderplatform.order.OrderResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class ImmutableListBoundaryTests {

  @Test
  void copyPreservesNulls() {
    assertThat(ImmutableLists.<String>copy(null)).isNull();
    assertThat(ImmutableLists.copy(new ArrayList<>(java.util.Arrays.asList("value", null))))
        .containsExactly("value", null);
  }

  @Test
  void dtoListsAreSnapshots() {
    assertSnapshot(
        new ArrayList<>(List.of("event")),
        values -> new PagedResponse<>(values, 0, 1, 1, 1, true, true, false, "id,asc").content());

    CreateOrderLineRequest requestLine = new CreateOrderLineRequest(7L, 2);
    assertSnapshot(
        new ArrayList<>(List.of(requestLine)),
        values ->
            new CreateOrderRequest(UUID.fromString("71717171-7171-7171-7171-717171717171"), values)
                .items());

    OrderLineResponse responseLine =
        new OrderLineResponse(7L, "Keyboard", BigDecimal.TEN, 2, BigDecimal.valueOf(20));
    assertSnapshot(
        new ArrayList<>(List.of(responseLine)),
        values ->
            new OrderResponse(
                    1L,
                    UUID.fromString("72727272-7272-7272-7272-727272727272"),
                    null,
                    BigDecimal.valueOf(20),
                    Instant.EPOCH,
                    null,
                    null,
                    null,
                    null,
                    null,
                    values)
                .lines());
  }

  private static <T> void assertSnapshot(List<T> source, Function<List<T>, List<T>> boundary) {
    List<T> expected = new ArrayList<>(source);
    List<T> snapshot = boundary.apply(source);

    source.clear();

    assertThat(snapshot).containsExactlyElementsOf(expected);
    assertThatThrownBy(snapshot::clear).isInstanceOf(UnsupportedOperationException.class);
  }
}
