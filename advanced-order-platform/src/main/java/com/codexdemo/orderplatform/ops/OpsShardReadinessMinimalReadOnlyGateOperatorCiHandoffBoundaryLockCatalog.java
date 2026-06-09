package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBoundaryLockCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBoundaryLockCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .BoundaryLock> locks() {
        return List.of(
                lock("no-java-autostart", "Node must not start Java"),
                lock("no-mini-kv-autostart", "Node must not start mini-kv"),
                lock("no-write-routing", "No write routing may be enabled"),
                lock("no-credential-value", "Credential values stay unread"),
                lock("no-raw-endpoint-url", "Raw endpoint URLs stay unresolved"),
                lock("no-managed-audit-http", "Managed audit HTTP/TCP stays disabled"),
                lock("no-runtime-shell", "Runtime shell remains disabled"),
                lock("no-mini-kv-write-admin", "mini-kv write/admin commands remain forbidden")
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .BoundaryLock lock(String code, String behavior) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                .BoundaryLock(
                        code,
                        behavior,
                        true,
                        "operator-ci handoff is advisory read-only evidence"
                );
    }
}
