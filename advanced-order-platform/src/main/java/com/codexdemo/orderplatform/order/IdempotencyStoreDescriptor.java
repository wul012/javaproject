package com.codexdemo.orderplatform.order;

public record IdempotencyStoreDescriptor(
        String abstractionVersion,
        String activeStore,
        String activeImplementation,
        String activeMode,
        String authoritativeStore,
        String keyColumn,
        String fingerprintColumn,
        boolean javaDatabaseBacked,
        boolean miniKvConnected,
        boolean externalTokenStoreConnected,
        boolean miniKvAdapterCandidateDeclared,
        boolean miniKvAdapterEnabled,
        String miniKvCandidateMode,
        String disabledCandidateReason,
        boolean changesPaymentOrInventoryTransaction
) {
}
