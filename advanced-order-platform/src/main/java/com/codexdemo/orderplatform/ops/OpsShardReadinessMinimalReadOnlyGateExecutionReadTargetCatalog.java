package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetCatalog {

    private OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget>
            readTargets() {
        return List.of(
                target(
                        "java-health",
                        "java-operator",
                        "HTTP GET",
                        "ORDER_PLATFORM_URL handle",
                        "GET /actuator/health"
                ),
                target(
                        "java-ops-overview",
                        "java-operator",
                        "HTTP GET",
                        "ORDER_PLATFORM_URL handle",
                        "GET /api/v1/ops/overview"
                ),
                target(
                        "mini-kv-health",
                        "mini-kv-operator",
                        "TCP command",
                        "MINIKV_HOST/MINIKV_PORT handle",
                        "HEALTH"
                ),
                target(
                        "mini-kv-infojson",
                        "mini-kv-operator",
                        "TCP command",
                        "MINIKV_HOST/MINIKV_PORT handle",
                        "INFOJSON"
                ),
                target(
                        "mini-kv-statsjson",
                        "mini-kv-operator",
                        "TCP command",
                        "MINIKV_HOST/MINIKV_PORT handle",
                        "STATSJSON"
                )
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget
            target(
                    String target,
                    String owner,
                    String protocol,
                    String addressHandle,
                    String commandOrRoute
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget(
                target,
                owner,
                protocol,
                addressHandle,
                commandOrRoute,
                true,
                true,
                "passed"
        );
    }
}
