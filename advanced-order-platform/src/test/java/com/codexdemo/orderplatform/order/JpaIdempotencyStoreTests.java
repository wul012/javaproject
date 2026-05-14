package com.codexdemo.orderplatform.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JpaIdempotencyStoreTests {

    private final OrderRepository orderRepository = org.mockito.Mockito.mock(OrderRepository.class);

    private final JpaIdempotencyStore idempotencyStore = new JpaIdempotencyStore(orderRepository);

    @Test
    void findsExistingOrderByIdempotencyKeyWithStoredFingerprint() {
        SalesOrder order = SalesOrder.place(
                UUID.fromString("17171717-1717-1717-1717-171717171717"),
                "store-key-001",
                "fingerprint-001",
                List.of(new OrderLineDraft(1L, "Keyboard", BigDecimal.valueOf(99), 1))
        );
        when(orderRepository.findByIdempotencyKey("store-key-001")).thenReturn(Optional.of(order));

        Optional<IdempotencyStoreEntry> found = idempotencyStore.findByKey("store-key-001");

        assertThat(found).isPresent();
        assertThat(found.orElseThrow().order()).isSameAs(order);
        assertThat(found.orElseThrow().requestFingerprint()).isEqualTo("fingerprint-001");
    }

    @Test
    void savesNewOrderThroughRepositoryFlush() {
        SalesOrder order = SalesOrder.place(
                UUID.fromString("18181818-1818-1818-1818-181818181818"),
                "store-key-002",
                "fingerprint-002",
                List.of(new OrderLineDraft(2L, "Monitor", BigDecimal.valueOf(199), 1))
        );
        when(orderRepository.saveAndFlush(order)).thenReturn(order);

        SalesOrder saved = idempotencyStore.saveNewOrder(order);

        assertThat(saved).isSameAs(order);
        verify(orderRepository).saveAndFlush(order);
    }

    @Test
    void descriptorKeepsMiniKvAsDisabledCandidate() {
        IdempotencyStoreDescriptor descriptor = idempotencyStore.descriptor();

        assertThat(descriptor.abstractionVersion()).isEqualTo("java-idempotency-store.v1");
        assertThat(descriptor.activeStore()).isEqualTo("jpa-order-idempotency-store");
        assertThat(descriptor.activeImplementation()).isEqualTo("JpaIdempotencyStore");
        assertThat(descriptor.activeMode()).isEqualTo("JPA_DATABASE");
        assertThat(descriptor.authoritativeStore()).isEqualTo("orders table");
        assertThat(descriptor.keyColumn()).isEqualTo("orders.idempotency_key");
        assertThat(descriptor.fingerprintColumn()).isEqualTo("orders.idempotency_request_fingerprint");
        assertThat(descriptor.javaDatabaseBacked()).isTrue();
        assertThat(descriptor.miniKvConnected()).isFalse();
        assertThat(descriptor.externalTokenStoreConnected()).isFalse();
        assertThat(descriptor.miniKvAdapterCandidateDeclared()).isTrue();
        assertThat(descriptor.miniKvAdapterEnabled()).isFalse();
        assertThat(descriptor.miniKvCandidateMode()).isEqualTo("DISABLED_CANDIDATE_ONLY");
        assertThat(descriptor.changesPaymentOrInventoryTransaction()).isFalse();
    }
}
