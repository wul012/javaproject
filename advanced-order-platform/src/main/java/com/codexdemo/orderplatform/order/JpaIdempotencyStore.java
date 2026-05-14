package com.codexdemo.orderplatform.order;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JpaIdempotencyStore implements IdempotencyStore {

    public static final String ABSTRACTION_VERSION = "java-idempotency-store.v1";
    public static final String ACTIVE_STORE = "jpa-order-idempotency-store";
    public static final String MINI_KV_CANDIDATE = "mini-kv-ttl-token-adapter";

    private final OrderRepository orderRepository;

    public JpaIdempotencyStore(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<IdempotencyStoreEntry> findByKey(String idempotencyKey) {
        return orderRepository.findByIdempotencyKey(idempotencyKey)
                .map(order -> new IdempotencyStoreEntry(order, order.getIdempotencyRequestFingerprint()));
    }

    @Override
    public SalesOrder saveNewOrder(SalesOrder order) {
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public IdempotencyStoreDescriptor descriptor() {
        return new IdempotencyStoreDescriptor(
                ABSTRACTION_VERSION,
                ACTIVE_STORE,
                JpaIdempotencyStore.class.getSimpleName(),
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                MINI_KV_CANDIDATE + " is documented for later TTL-token experiments, not wired into create-order.",
                false
        );
    }
}
