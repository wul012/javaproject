package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService {

    static final String ENDPOINT =
            "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json";
    static final String EVIDENCE_PATH =
            "e/179/evidence/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.json";

    private final OpsShardReadinessReadOnlyEvidenceCatalogService catalogService;
    private final OpsShardReadinessReadOnlyEvidenceCatalogHandoffService handoffService;

    public OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService(
            OpsShardReadinessReadOnlyEvidenceCatalogService catalogService,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffService handoffService
    ) {
        this.catalogService = catalogService;
        this.handoffService = handoffService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse verification() {
        OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog = catalogService.catalog();
        OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse handoff = handoffService.handoff();
        List<String> currentLiveEndpoints = OpsShardReadinessEvidenceEndpoints.liveEndpoints();
        List<String> currentFixtureEndpoints = OpsShardReadinessEvidenceEndpoints.fixtureEndpoints();

        return new OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse(
                "advanced-order-platform",
                "Java v179",
                true,
                false,
                catalog.shardEnabled(),
                catalog.version(),
                handoff.version(),
                catalog.receiptId(),
                handoff.receiptId(),
                "passed".equals(catalog.status()),
                "passed".equals(handoff.status()),
                handoff.sourceCatalogFrozen(),
                catalog.liveEndpointCount(),
                catalog.fixtureEndpointCount(),
                currentLiveEndpoints.size(),
                currentFixtureEndpoints.size(),
                currentLiveEndpoints.contains(ENDPOINT) && currentFixtureEndpoints.contains(FIXTURE_ENDPOINT),
                !catalog.liveEndpoints().contains(ENDPOINT) && !catalog.fixtureEndpoints().contains(FIXTURE_ENDPOINT),
                sourceBoundariesHeld(catalog, handoff),
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "java-shard-readiness-read-only-evidence-catalog-handoff-verification.v1",
                "java-shard-readiness-read-only-evidence-catalog-handoff-verification-receipt-v179",
                verifiedArtifacts(catalog, handoff),
                verificationChecks(catalog, handoff, currentLiveEndpoints, currentFixtureEndpoints),
                failClosedRules(),
                blockedOperations(),
                EVIDENCE_PATH,
                verificationStatus(catalog, handoff, currentLiveEndpoints, currentFixtureEndpoints)
        );
    }

    private boolean sourceBoundariesHeld(
            OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse handoff
    ) {
        return catalog.readOnly()
                && handoff.readOnly()
                && !catalog.executionAllowed()
                && !handoff.executionAllowed()
                && !catalog.writeRoutingAllowed()
                && !handoff.writeRoutingAllowed()
                && !catalog.activeShardRouterAllowed()
                && !handoff.activeShardRouterAllowed()
                && !catalog.credentialValueRead()
                && !handoff.credentialValueRead()
                && !catalog.rawEndpointParsed()
                && !handoff.rawEndpointParsed()
                && !catalog.managedAuditConnectionAllowed()
                && !handoff.managedAuditConnectionAllowed()
                && !catalog.nodeMayStartOrStopJavaOrMiniKv()
                && !handoff.nodeMayStartOrStopJavaOrMiniKv();
    }

    private List<String> verifiedArtifacts(
            OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse handoff
    ) {
        return List.of(
                catalog.catalogEndpoint(),
                catalog.fixtureEndpoint(),
                catalog.evidencePath(),
                "e/176/evidence/java-shard-readiness-read-only-evidence-catalog-snapshot-freeze-v176.json",
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.ENDPOINT,
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.FIXTURE_ENDPOINT,
                handoff.evidencePath(),
                EVIDENCE_PATH
        );
    }

    private List<String> verificationChecks(
            OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse handoff,
            List<String> currentLiveEndpoints,
            List<String> currentFixtureEndpoints
    ) {
        return List.of(
                "source-catalog-status:" + catalog.status(),
                "source-handoff-status:" + handoff.status(),
                "source-catalog-frozen:" + handoff.sourceCatalogFrozen(),
                "frozen-catalog-live-endpoint-count:" + catalog.liveEndpointCount(),
                "frozen-catalog-fixture-endpoint-count:" + catalog.fixtureEndpointCount(),
                "current-live-endpoint-count:" + currentLiveEndpoints.size(),
                "current-fixture-endpoint-count:" + currentFixtureEndpoints.size(),
                "current-registry-includes-verification:" + currentLiveEndpoints.contains(ENDPOINT),
                "v175-catalog-does-not-include-v179-verification:" + !catalog.liveEndpoints().contains(ENDPOINT)
        );
    }

    private List<String> failClosedRules() {
        return List.of(
                "block-if-source-catalog-status-is-not-passed",
                "block-if-source-handoff-status-is-not-passed",
                "block-if-v175-catalog-endpoint-count-drifts",
                "block-if-current-registry-misses-verification-endpoint",
                "block-if-any-source-opens-runtime-execution",
                "block-if-node-starts-or-stops-java-or-mini-kv"
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

    private String verificationStatus(
            OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse handoff,
            List<String> currentLiveEndpoints,
            List<String> currentFixtureEndpoints
    ) {
        boolean sourcesPassed = "passed".equals(catalog.status()) && "passed".equals(handoff.status());
        boolean frozenCatalogHeld = handoff.sourceCatalogFrozen()
                && catalog.liveEndpointCount() == 20
                && catalog.fixtureEndpointCount() == 20
                && !catalog.liveEndpoints().contains(ENDPOINT)
                && !catalog.fixtureEndpoints().contains(FIXTURE_ENDPOINT);
        boolean currentRegistryIncludesVerification = currentLiveEndpoints.contains(ENDPOINT)
                && currentFixtureEndpoints.contains(FIXTURE_ENDPOINT)
                && currentLiveEndpoints.size() == currentFixtureEndpoints.size();
        return sourcesPassed
                && frozenCatalogHeld
                && currentRegistryIncludesVerification
                && sourceBoundariesHeld(catalog, handoff) ? "passed" : "blocked";
    }
}
