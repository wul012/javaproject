package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractOperatorChecklistServiceTests {

    @Test
    void buildsReadOnlyOperatorChecklistFromFrozenPacketEvidence() {
        OpsShardReadinessV1ContractOperatorChecklistResponse checklist =
                new OpsShardReadinessV1ContractOperatorChecklistService(
                        new OpsShardReadinessV1ContractEvidencePacketService()
                ).checklist();

        assertThat(checklist.project()).isEqualTo("advanced-order-platform");
        assertThat(checklist.version()).isEqualTo("Java v196");
        assertThat(checklist.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(checklist.readOnly()).isTrue();
        assertThat(checklist.executionAllowed()).isFalse();
        assertThat(checklist.shardEnabled()).isFalse();
        assertThat(checklist.checklistEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-operator-checklist");
        assertThat(checklist.checklistFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json");
        assertThat(checklist.packetEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-evidence-packet");
        assertThat(checklist.packetEvidencePath())
                .isEqualTo("e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json");
        assertThat(checklist.packetSnapshotFreezeEvidencePath())
                .isEqualTo("e/194/evidence/java-shard-readiness-v193-evidence-packet-snapshot-freeze-v194.json");
        assertThat(checklist.packetHistoricalCompatibilityEvidencePath())
                .isEqualTo(
                        "e/195/evidence/java-shard-readiness-v193-evidence-packet-historical-snapshot-compatibility-v195.json"
                );
        assertThat(checklist.operatorChecklistItems())
                .containsExactly(
                        "confirm-java-v193-packet-endpoint-is-readable",
                        "confirm-java-v194-packet-snapshot-freeze-evidence-is-archived",
                        "confirm-java-v195-historical-snapshot-compatibility-evidence-is-archived",
                        "confirm-node-consumes-only-read-only-get-endpoints",
                        "confirm-no-write-routing-or-active-shard-router-is-enabled",
                        "confirm-no-credential-value-or-raw-endpoint-is-read",
                        "confirm-no-java-or-mini-kv-process-control-is-delegated-to-node"
                );
        assertThat(checklist.requiredReadOnlyEvidence())
                .containsExactly(
                        "e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json",
                        "e/194/evidence/java-shard-readiness-v193-evidence-packet-snapshot-freeze-v194.json",
                        "e/195/evidence/java-shard-readiness-v193-evidence-packet-historical-snapshot-compatibility-v195.json",
                        "e/196/evidence/java-shard-readiness-v1-contract-operator-checklist-v196.json"
                );
        assertThat(checklist.nodeResponsibilities())
                .contains(
                        "read-checklist-endpoint-with-get-only",
                        "avoid-java-or-mini-kv-process-start-stop"
                );
        assertThat(checklist.javaResponsibilities())
                .contains(
                        "serve-checklist-as-read-only-json",
                        "keep-execution-allowed-false"
                );
        assertThat(checklist.blockedOperations())
                .contains(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(checklist.verificationChecks())
                .contains(
                        "operator-checklist-item-count:7",
                        "packet-frozen:true",
                        "historical-snapshots-protected:true",
                        "execution-allowed:false"
                );
        assertThat(checklist.packetFrozen()).isTrue();
        assertThat(checklist.historicalSnapshotsProtected()).isTrue();
        assertThat(checklist.writeRoutingAllowed()).isFalse();
        assertThat(checklist.activeShardRouterAllowed()).isFalse();
        assertThat(checklist.credentialValueRead()).isFalse();
        assertThat(checklist.rawEndpointParsed()).isFalse();
        assertThat(checklist.managedAuditConnectionAllowed()).isFalse();
        assertThat(checklist.deploymentOrRollbackAllowed()).isFalse();
        assertThat(checklist.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(checklist.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-operator-checklist-receipt-v196");
        assertThat(checklist.evidencePath())
                .isEqualTo("e/196/evidence/java-shard-readiness-v1-contract-operator-checklist-v196.json");
        assertThat(checklist.status()).isEqualTo("passed");
    }
}
