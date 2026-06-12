package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityUpkeepAuditBoundaryCatalog {

    private ReadabilityUpkeepAuditBoundaryCatalog() {
    }

    static List<ReadabilityUpkeepAuditResponse.BoundaryRule> boundaryRules() {
        return List.of(
                denied("no-write-routing", "write routing", "audit maps cannot mutate order routing"),
                denied("no-active-shard-router", "active shard router", "audit evidence remains a preview"),
                denied("no-credential-value", "credential value reads", "catalog facts never inspect secrets"),
                denied("no-raw-endpoint-url", "raw endpoint URL resolution", "routes are symbolic path contracts"),
                denied("no-managed-audit-connection", "managed audit HTTP/TCP", "audit is rendered from local catalogs"),
                denied("no-deployment-or-rollback", "deployment or rollback", "readability upkeep is not a release action"),
                denied("no-java-autostart", "Java autostart", "tests instantiate services directly"),
                denied("no-minikv-autostart", "mini-kv autostart", "mini-kv remains outside Java readability upkeep")
        );
    }

    private static ReadabilityUpkeepAuditResponse.BoundaryRule denied(
            String code,
            String forbiddenAction,
            String rationale
    ) {
        return new ReadabilityUpkeepAuditResponse.BoundaryRule(
                code,
                forbiddenAction,
                false,
                rationale
        );
    }
}
