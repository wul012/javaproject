package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffServiceTests {

    @Test
    void buildsReadOnlyConsumerReadinessHandoffFromFrozenDigest() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                new OpsShardReadinessV1ContractConsumerReadinessHandoffService().handoff();

        assertThat(handoff.project()).isEqualTo("advanced-order-platform");
        assertThat(handoff.version()).isEqualTo("Java v225");
        assertThat(handoff.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(handoff.readOnly()).isTrue();
        assertThat(handoff.executionAllowed()).isFalse();
        assertThat(handoff.shardEnabled()).isFalse();
        assertThat(handoff.readinessHandoffEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-readiness-handoff");
        assertThat(handoff.readinessHandoffFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json");
        assertThat(handoff.evidenceDigestEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-consumer-evidence-digest");
        assertThat(handoff.evidenceDigestFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.fixture.json");
        assertThat(handoff.evidenceDigestEvidencePath())
                .isEqualTo("e/220/evidence/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.json");
        assertThat(handoff.evidenceDigestReceiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-evidence-digest-receipt-v220");
        assertThat(handoff.digestEvidenceCount()).isEqualTo(5);
        assertThat(handoff.digestCheckCount()).isEqualTo(7);
        assertThat(handoff.digestEvidence())
                .containsExactly(
                        "e/215/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.json",
                        "e/216/evidence/java-shard-readiness-v215-consumer-verification-checklist-snapshot-freeze-v216.json",
                        "e/217/evidence/java-shard-readiness-v215-consumer-verification-checklist-historical-compatibility-v217.json",
                        "e/218/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-integrity-v218.json",
                        "e/219/evidence/java-shard-readiness-v1-contract-consumer-route-inventory-v219.json"
                );
        assertThat(handoff.handoffGuardEvidence())
                .containsExactly(
                        "e/221/evidence/java-shard-readiness-v220-consumer-evidence-digest-snapshot-freeze-v221.json",
                        "e/222/evidence/java-shard-readiness-v220-consumer-evidence-digest-historical-compatibility-v222.json",
                        "e/223/evidence/java-shard-readiness-v1-contract-consumer-evidence-digest-integrity-v223.json",
                        "e/224/evidence/java-shard-readiness-v1-contract-consumer-readiness-completion-v224.json"
                );
        assertThat(handoff.handoffChecks())
                .containsExactly(
                        "digest-version:Java v220",
                        "digest-evidence-count:5",
                        "digest-check-count:7",
                        "handoff-guard-evidence-count:4",
                        "probes-are-get-only:true",
                        "upstream-actions-allowed:false",
                        "node-may-start-or-stop-java-or-mini-kv:false"
                );
        assertThat(handoff.blockedOperations())
                .containsExactly(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "raw-endpoint-parse",
                        "managed-audit-connection",
                        "deployment-or-rollback",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(handoff.probesAreGetOnly()).isTrue();
        assertThat(handoff.upstreamActionsAllowed()).isFalse();
        assertThat(handoff.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(handoff.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-readiness-handoff-receipt-v225");
        assertThat(handoff.evidencePath())
                .isEqualTo("e/225/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.json");
        assertThat(handoff.status()).isEqualTo("passed");
    }
}
