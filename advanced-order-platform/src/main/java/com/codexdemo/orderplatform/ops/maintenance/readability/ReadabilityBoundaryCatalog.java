package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityBoundaryCatalog {

    private ReadabilityBoundaryCatalog() {
    }

    static List<ReadabilityUpkeepRegistryResponse.BoundaryRule> boundaryRules() {
        return List.of(
                denied("no-write-routing", "write routing", "readability upkeep cannot mutate orders"),
                denied("no-active-shard-router", "active shard router", "topic maps do not activate routing"),
                denied("no-credential-value", "credential value reads", "docs and registries never read secrets"),
                denied("no-raw-endpoint-url", "raw endpoint URL resolution", "maps use route patterns only"),
                denied("no-managed-audit-connection", "managed audit HTTP/TCP", "no external audit call is needed"),
                denied("no-deployment-or-rollback", "deployment or rollback", "maintenance docs are not release actions"),
                denied("no-java-autostart", "Java autostart", "tests instantiate services or read files only"),
                denied("no-minikv-autostart", "mini-kv autostart", "mini-kv remains outside this upkeep batch")
        );
    }

    private static ReadabilityUpkeepRegistryResponse.BoundaryRule denied(
            String code,
            String forbiddenAction,
            String rationale
    ) {
        return new ReadabilityUpkeepRegistryResponse.BoundaryRule(
                code,
                forbiddenAction,
                false,
                rationale
        );
    }
}
