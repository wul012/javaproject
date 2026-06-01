package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerEvidenceDigestServiceTests {

    @Test
    void buildsReadOnlyConsumerEvidenceDigestFromFrozenChecklist() {
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                new OpsShardReadinessV1ContractConsumerEvidenceDigestService().digest();

        assertThat(digest.project()).isEqualTo("advanced-order-platform");
        assertThat(digest.version()).isEqualTo("Java v220");
        assertThat(digest.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(digest.readOnly()).isTrue();
        assertThat(digest.executionAllowed()).isFalse();
        assertThat(digest.shardEnabled()).isFalse();
        assertThat(digest.evidenceDigestEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-evidence-digest");
        assertThat(digest.evidenceDigestFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.fixture.json");
        assertThat(digest.verificationChecklistEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-verification-checklist");
        assertThat(digest.verificationChecklistFixtureEndpoint())
                .isEqualTo(
                        "/contracts/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.fixture.json"
                );
        assertThat(digest.verificationChecklistEvidencePath())
                .isEqualTo("e/215/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.json");
        assertThat(digest.verificationChecklistReceiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-verification-checklist-receipt-v215");
        assertThat(digest.checklistItemCount()).isEqualTo(7);
        assertThat(digest.requiredEvidenceCount()).isEqualTo(5);
        assertThat(digest.verificationCheckCount()).isEqualTo(7);
        assertThat(digest.digestEvidence())
                .containsExactly(
                        "e/215/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.json",
                        "e/216/evidence/java-shard-readiness-v215-consumer-verification-checklist-snapshot-freeze-v216.json",
                        "e/217/evidence/java-shard-readiness-v215-consumer-verification-checklist-historical-compatibility-v217.json",
                        "e/218/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-integrity-v218.json",
                        "e/219/evidence/java-shard-readiness-v1-contract-consumer-route-inventory-v219.json"
                );
        assertThat(digest.digestChecks())
                .containsExactly(
                        "checklist-version:Java v215",
                        "checklist-items:7",
                        "checklist-required-evidence:5",
                        "checklist-verification-checks:7",
                        "digest-evidence-count:5",
                        "probes-are-get-only:true",
                        "node-may-start-or-stop-java-or-mini-kv:false"
                );
        assertThat(digest.probesAreGetOnly()).isTrue();
        assertThat(digest.upstreamActionsAllowed()).isFalse();
        assertThat(digest.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(digest.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-evidence-digest-receipt-v220");
        assertThat(digest.evidencePath())
                .isEqualTo("e/220/evidence/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.json");
        assertThat(digest.status()).isEqualTo("passed");
    }
}
