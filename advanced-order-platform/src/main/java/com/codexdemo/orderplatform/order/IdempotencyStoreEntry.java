package com.codexdemo.orderplatform.order;

public record IdempotencyStoreEntry(
        SalesOrder order,
        String requestFingerprint
) {
}
