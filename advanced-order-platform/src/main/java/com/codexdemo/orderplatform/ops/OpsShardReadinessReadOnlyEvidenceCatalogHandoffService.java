package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReadOnlyEvidenceCatalogHandoffService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json";
    static final String EVIDENCE_PATH =
            "e/177/evidence/java-shard-readiness-read-only-evidence-catalog-handoff-v177.json";

    private final OpsShardReadinessReadOnlyEvidenceCatalogService catalogService;

    public OpsShardReadinessReadOnlyEvidenceCatalogHandoffService(
            OpsShardReadinessReadOnlyEvidenceCatalogService catalogService
    ) {
        this.catalogService = catalogService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse handoff() {
        OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog = catalogService.catalog();

        return new OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse(
                "advanced-order-platform",
                "Java v177",
                true,
                false,
                catalog.shardEnabled(),
                catalog.version(),
                catalog.receiptId(),
                catalog.catalogEndpoint(),
                catalog.fixtureEndpoint(),
                catalog.evidencePath(),
                catalog.liveEndpointCount(),
                catalog.fixtureEndpointCount(),
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "java-shard-readiness-read-only-evidence-catalog-handoff.v1",
                "java-shard-readiness-read-only-evidence-catalog-handoff-receipt-v177",
                "Node may consume the frozen Java v175 catalog after Java and mini-kv evidence is versioned",
                handoffArtifacts(catalog),
                consumerRules(),
                failClosedChecks(catalog),
                blockedOperations(),
                EVIDENCE_PATH,
                handoffStatus(catalog)
        );
    }

    private List<String> handoffArtifacts(OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog) {
        return List.of(
                catalog.catalogEndpoint(),
                catalog.fixtureEndpoint(),
                catalog.evidencePath(),
                "e/176/evidence/java-shard-readiness-read-only-evidence-catalog-snapshot-freeze-v176.json",
                EVIDENCE_PATH
        );
    }

    private List<String> consumerRules() {
        return List.of(
                "consume-versioned-fixtures-before-live-probes",
                "treat-v175-catalog-as-frozen-snapshot",
                "node-may-batch-consume-java-evidence-after-versioned-archive-exists",
                "node-must-not-start-or-stop-java-or-mini-kv",
                "node-must-not-open-write-routing-or-active-shard-router",
                "node-must-fail-closed-if-catalog-status-is-not-passed"
        );
    }

    private List<String> failClosedChecks(OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog) {
        return List.of(
                "source-catalog-status-must-be-passed:" + catalog.status(),
                "source-catalog-live-endpoint-count:" + catalog.liveEndpointCount(),
                "source-catalog-fixture-endpoint-count:" + catalog.fixtureEndpointCount(),
                "source-catalog-receipt:" + catalog.receiptId(),
                "snapshot-freeze-evidence-required:e/176/evidence/"
                        + "java-shard-readiness-read-only-evidence-catalog-snapshot-freeze-v176.json",
                "handoff-does-not-read-secrets-or-raw-endpoints",
                "handoff-does-not-enable-runtime-execution"
        );
    }

    private List<String> blockedOperations() {
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

    private String handoffStatus(OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog) {
        boolean sourcePassed = "passed".equals(catalog.status());
        boolean sourceFrozen = catalog.liveEndpointCount() == 20
                && catalog.fixtureEndpointCount() == 20
                && catalog.liveEndpoints().contains(catalog.catalogEndpoint())
                && catalog.fixtureEndpoints().contains(catalog.fixtureEndpoint());
        boolean boundariesHeld = catalog.readOnly()
                && !catalog.executionAllowed()
                && !catalog.writeRoutingAllowed()
                && !catalog.activeShardRouterAllowed()
                && !catalog.credentialValueRead()
                && !catalog.rawEndpointParsed()
                && !catalog.managedAuditConnectionAllowed()
                && !catalog.nodeMayStartOrStopJavaOrMiniKv();
        return sourcePassed && sourceFrozen && boundariesHeld ? "passed" : "blocked";
    }
}
