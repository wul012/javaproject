package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerVerificationChecklistServiceTests {

    @Test
    void buildsReadOnlyConsumerVerificationChecklistFromFrozenHandoffBundle() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                new OpsShardReadinessV1ContractConsumerVerificationChecklistService().checklist();

        assertThat(checklist.project()).isEqualTo("advanced-order-platform");
        assertThat(checklist.version()).isEqualTo("Java v215");
        assertThat(checklist.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(checklist.readOnly()).isTrue();
        assertThat(checklist.executionAllowed()).isFalse();
        assertThat(checklist.shardEnabled()).isFalse();
        assertThat(checklist.verificationChecklistEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-verification-checklist");
        assertThat(checklist.verificationChecklistFixtureEndpoint())
                .isEqualTo(
                        "/contracts/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.fixture.json"
                );
        assertThat(checklist.handoffBundleEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-handoff-bundle");
        assertThat(checklist.handoffBundleFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.fixture.json");
        assertThat(checklist.handoffBundleEvidencePath())
                .isEqualTo("e/211/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.json");
        assertThat(checklist.handoffBundleReceiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-handoff-bundle-receipt-v211");
        assertThat(checklist.catalogedArtifactCount()).isEqualTo(6);
        assertThat(checklist.verificationItems())
                .containsExactly(
                        "read-v208-endpoint-catalog-before-consuming-v211-bundle",
                        "confirm-v211-bundle-required-evidence-count-is-nine",
                        "confirm-v211-bundle-handoff-evidence-count-is-four",
                        "confirm-probes-are-get-only",
                        "confirm-upstream-actions-remain-disabled",
                        "confirm-node-does-not-start-or-stop-java-or-mini-kv",
                        "archive-v215-checklist-receipt-before-any-node-consumption"
                );
        assertThat(checklist.requiredEvidence())
                .containsExactly(
                        "e/208/evidence/java-shard-readiness-v1-contract-endpoint-catalog-v208.json",
                        "e/211/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.json",
                        "e/212/evidence/java-shard-readiness-v211-consumer-handoff-bundle-snapshot-freeze-v212.json",
                        "e/213/evidence/java-shard-readiness-v211-consumer-handoff-bundle-historical-compatibility-v213.json",
                        "e/214/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-integrity-v214.json"
                );
        assertThat(checklist.blockedOperations())
                .containsExactly(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "raw-endpoint-parse",
                        "managed-audit-connection",
                        "deployment-or-rollback",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(checklist.verificationChecks())
                .containsExactly(
                        "bundle-version:Java v211",
                        "cataloged-artifact-count:6",
                        "required-evidence-count:9",
                        "handoff-evidence-count:4",
                        "probes-are-get-only:true",
                        "upstream-actions-allowed:false",
                        "node-may-start-or-stop-java-or-mini-kv:false"
                );
        assertThat(checklist.probesAreGetOnly()).isTrue();
        assertThat(checklist.upstreamActionsAllowed()).isFalse();
        assertThat(checklist.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(checklist.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-verification-checklist-receipt-v215");
        assertThat(checklist.evidencePath())
                .isEqualTo("e/215/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.json");
        assertThat(checklist.status()).isEqualTo("passed");
    }
}
