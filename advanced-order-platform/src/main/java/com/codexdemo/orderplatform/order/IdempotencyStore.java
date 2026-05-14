package com.codexdemo.orderplatform.order;

import java.util.Optional;

public interface IdempotencyStore {

    Optional<IdempotencyStoreEntry> findByKey(String idempotencyKey);

    SalesOrder saveNewOrder(SalesOrder order);

    IdempotencyStoreDescriptor descriptor();
}
