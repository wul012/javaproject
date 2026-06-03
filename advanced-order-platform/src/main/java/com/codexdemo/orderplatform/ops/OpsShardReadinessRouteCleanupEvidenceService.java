package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupEvidenceService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EVIDENCE_CATALOG;

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupEvidenceResponse catalog() {
        List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries =
                OpsShardReadinessRouteCleanupEvidenceCatalog.entries();
        return new OpsShardReadinessRouteCleanupEvidenceResponse(
                "advanced-order-platform",
                "Java v306",
                true,
                false,
                ENDPOINT,
                "java-shard-readiness-route-cleanup-evidence-catalog.v1",
                entries.size(),
                entries,
                forbiddenOperations(),
                catalogStatus(entries)
        );
    }

    private List<String> forbiddenOperations() {
        return List.of(
                "write-routing",
                "active-shard-router",
                "credential-value-read",
                "raw-endpoint-parse",
                "managed-audit-connection",
                "deployment-or-rollback",
                "node-start-or-stop-java-or-mini-kv"
        );
    }

    private String catalogStatus(List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries) {
        boolean allReadOnly = entries.stream()
                .allMatch(entry -> entry.readOnly()
                        && !entry.executionAllowed()
                        && !entry.startsJavaService()
                        && !entry.startsMiniKvService()
                        && !entry.credentialValueRead()
                        && !entry.rawEndpointParsed()
                        && !entry.managedAuditConnectionOpened()
                        && !entry.writeRoutingChanged());
        return allReadOnly ? "passed" : "blocked";
    }
}
