package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessPrototypeHandoffServiceTests {

    @Test
    void buildsPrototypeHandoffCatalogFromPrototypeCloseout() {
        OpsShardReadinessPrototypeHandoffCatalogResponse catalog = service().catalog();

        assertThat(catalog.project()).isEqualTo("advanced-order-platform");
        assertThat(catalog.version()).isEqualTo(catalog.entries().getLast().version());
        assertThat(catalog.readOnly()).isTrue();
        assertThat(catalog.executionAllowed()).isFalse();
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-catalog");
        assertThat(catalog.profile()).isEqualTo("java-shard-readiness-prototype-handoff-catalog.v1");
        assertThat(catalog.sourcePrototypeVersion()).isEqualTo("Java v427");
        assertThat(catalog.sourcePrototypeEndpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-closeout");
        assertThat(catalog.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(catalog.entryCount()).isEqualTo(catalog.entries().size());
        assertThat(catalog.entries())
                .first()
                .satisfies(entry -> {
                    assertThat(entry.javaVersion()).isEqualTo(429);
                    assertThat(entry.key()).isEqualTo("handoff-catalog");
                    assertThat(entry.nodePlanVersion()).isEqualTo("Node v368");
                    assertThat(entry.checks())
                            .contains("consume-java-v428-prototype-closeout-route");
                });
        assertThat(catalog.forbiddenOperations()).contains("active-shard-router");
        assertThat(catalog.status()).isEqualTo("passed");
    }

    @Test
    void buildsEndpointInventoryEvidenceForReadOnlyRouteHandoff() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence = service().endpointInventory();

        assertThat(evidence.version()).isEqualTo("Java v431");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-endpoint-inventory");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-endpoint-inventory.v1");
        assertThat(evidence.checks())
                .contains(
                        "inventory-prototype-catalog-route-present",
                        "inventory-prototype-closeout-route-present",
                        "inventory-handoff-catalog-route-present",
                        "inventory-no-root-controller-regression"
                );
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsBoundaryMatrixEvidenceForForbiddenOperations() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence = service().boundaryMatrix();

        assertThat(evidence.version()).isEqualTo("Java v433");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-boundary-matrix");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-boundary-matrix.v1");
        assertThat(evidence.checks())
                .contains(
                        "preserve-read-only-contract-boundary",
                        "preserve-executionAllowed-false",
                        "forbid-write-routing",
                        "forbid-managed-audit-connection",
                        "forbid-node-process-control"
                );
        assertThat(evidence.forbiddenOperations())
                .contains("write-routing", "managed-audit-connection", "node-start-or-stop-java-or-mini-kv");
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsConsumerVerificationChecklistEvidence() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence =
                service().consumerVerificationChecklist();

        assertThat(evidence.version()).isEqualTo("Java v435");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-consumer-verification-checklist");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-consumer-verification-checklist.v1");
        assertThat(evidence.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(evidence.checks())
                .contains(
                        "verify-contractName-shard-readiness-v1",
                        "verify-readOnly-true",
                        "verify-executionAllowed-false",
                        "verify-status-passed",
                        "verify-digest-present"
                );
        assertThat(evidence.digestValue()).matches("[0-9a-f]{64}");
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsReadWindowChecklistEvidence() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence = service().readWindowChecklist();

        assertThat(evidence.version()).isEqualTo("Java v437");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-read-window-checklist");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-read-window-checklist.v1");
        assertThat(evidence.checks())
                .contains(
                        "read-window-java-health-route-observed",
                        "read-window-ops-overview-route-observed",
                        "read-window-shard-readiness-route-observed",
                        "read-window-upstream-probes-only",
                        "read-window-actions-disabled"
                );
        assertThat(evidence.executionAllowed()).isFalse();
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsDigestManifestEvidence() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence = service().digestManifest();

        assertThat(evidence.version()).isEqualTo("Java v439");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-digest-manifest");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-digest-manifest.v1");
        assertThat(evidence.checks())
                .contains(
                        "digest-covers-source-catalog-version",
                        "digest-covers-source-closeout-version",
                        "digest-covers-source-closeout-digest",
                        "digest-covers-entry-key-and-profile",
                        "digest-covers-evidence-path"
                );
        assertThat(evidence.evidenceRefs()).contains(
                "prototype-catalog:/api/v1/ops/shard-readiness/prototype-catalog",
                "prototype-closeout:/api/v1/ops/shard-readiness/prototype-closeout"
        );
        assertThat(evidence.digestValue()).matches("[0-9a-f]{64}");
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsCiManifestEvidence() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence = service().ciManifest();

        assertThat(evidence.version()).isEqualTo("Java v441");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-ci-manifest");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-ci-manifest.v1");
        assertThat(evidence.checks())
                .contains(
                        "ci-runs-handoff-service-tests",
                        "ci-runs-route-path-tests",
                        "ci-runs-handoff-integration-tests",
                        "ci-runs-full-maven-test-before-push",
                        "ci-leaves-runtime-processes-stopped"
                );
        assertThat(evidence.readOnly()).isTrue();
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsArchiveManifestEvidence() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence = service().archiveManifest();

        assertThat(evidence.version()).isEqualTo("Java v443");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-archive-manifest");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-archive-manifest.v1");
        assertThat(evidence.evidencePath())
                .isEqualTo("e/443/evidence/java-shard-readiness-prototype-handoff-archive-manifest-v443.json");
        assertThat(evidence.checks())
                .contains(
                        "archive-evidence-paths-versioned",
                        "archive-entries-use-e-folder-only",
                        "archive-routes-remain-read-only",
                        "archive-runtime-artifacts-not-required",
                        "archive-node-consumer-can-pin-versioned-paths"
                );
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsOperatorSignoffPacketEvidence() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence = service().operatorSignoffPacket();

        assertThat(evidence.version()).isEqualTo("Java v445");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-operator-signoff-packet");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-operator-signoff-packet.v1");
        assertThat(evidence.checks())
                .contains(
                        "operator-owns-java-read-window",
                        "node-does-not-start-or-stop-java",
                        "node-does-not-start-or-stop-mini-kv",
                        "writes-and-deployments-remain-forbidden",
                        "handoff-fails-closed-on-status-mismatch"
                );
        assertThat(evidence.forbiddenOperations())
                .contains("write-routing", "deployment-or-rollback", "node-start-or-stop-java-or-mini-kv");
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void allHandoffCatalogEntriesProducePassedReadOnlyEvidence() {
        OpsShardReadinessPrototypeHandoffService service = service();

        assertThat(OpsShardReadinessPrototypeHandoffEvidenceCatalog.entries())
                .allSatisfy(entry -> {
                    OpsShardReadinessPrototypeHandoffEvidenceResponse evidence =
                            service.evidence(entry.key());

                    assertThat(evidence.version()).isEqualTo(entry.version());
                    assertThat(evidence.endpoint()).isEqualTo(entry.endpoint());
                    assertThat(evidence.profile()).isEqualTo(entry.profile());
                    assertThat(evidence.readOnly()).isTrue();
                    assertThat(evidence.executionAllowed()).isFalse();
                    assertThat(evidence.sourceCatalogVersion()).isEqualTo("Java v427");
                    assertThat(evidence.sourceCloseoutVersion()).isEqualTo("Java v427");
                    assertThat(evidence.digestValue()).matches("[0-9a-f]{64}");
                    assertThat(evidence.status()).isEqualTo("passed");
                });
    }

    private OpsShardReadinessPrototypeHandoffService service() {
        return new OpsShardReadinessPrototypeHandoffService(prototypeEvidenceService());
    }

    private OpsShardReadinessPrototypeEvidenceService prototypeEvidenceService() {
        OpsShardReadinessEvidenceIndexService evidenceIndexService = new OpsShardReadinessEvidenceIndexService();
        OpsShardReadinessEvidenceVerificationService evidenceVerificationService =
                new OpsShardReadinessEvidenceVerificationService(evidenceIndexService);
        OpsShardReadinessEvidenceHandoffService evidenceHandoffService =
                new OpsShardReadinessEvidenceHandoffService(evidenceIndexService, evidenceVerificationService);
        OpsShardReadinessEchoService echoService = new OpsShardReadinessEchoService(
                new OpsShardReadinessService(),
                new OpsShardReadinessHardeningService(),
                evidenceIndexService,
                evidenceHandoffService
        );
        return new OpsShardReadinessPrototypeEvidenceService(
                new OpsShardReadinessService(),
                echoService,
                OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.postCompletionCloseoutService()
        );
    }
}
