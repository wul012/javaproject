package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractAlignmentHandoffServiceTests {

    @Test
    void buildsReadOnlyHandoffAcrossContractAlignmentFreezeAndHistoricalGuard() {
        OpsShardReadinessV1ContractAlignmentHandoffResponse handoff =
                new OpsShardReadinessV1ContractAlignmentHandoffService(
                        new OpsShardReadinessV1ContractAlignmentService()
                ).handoff();

        assertThat(handoff.project()).isEqualTo("advanced-order-platform");
        assertThat(handoff.version()).isEqualTo("Java v190");
        assertThat(handoff.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(handoff.readOnly()).isTrue();
        assertThat(handoff.executionAllowed()).isFalse();
        assertThat(handoff.shardEnabled()).isFalse();
        assertThat(handoff.alignmentVersion()).isEqualTo("Java v187");
        assertThat(handoff.alignmentEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-alignment");
        assertThat(handoff.alignmentFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json");
        assertThat(handoff.snapshotFreezeVersion()).isEqualTo("Java v188");
        assertThat(handoff.historicalCompatibilityVersion()).isEqualTo("Java v189");
        assertThat(handoff.minimalFieldsFrozen()).isTrue();
        assertThat(handoff.historicalSnapshotsProtected()).isTrue();
        assertThat(handoff.registryContainsAlignment()).isTrue();
        assertThat(handoff.olderSnapshotsRemainUnbackfilled()).isTrue();
        assertThat(handoff.writeRoutingAllowed()).isFalse();
        assertThat(handoff.activeShardRouterAllowed()).isFalse();
        assertThat(handoff.credentialValueRead()).isFalse();
        assertThat(handoff.rawEndpointParsed()).isFalse();
        assertThat(handoff.managedAuditConnectionAllowed()).isFalse();
        assertThat(handoff.deploymentOrRollbackAllowed()).isFalse();
        assertThat(handoff.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(handoff.handoffArtifacts())
                .containsExactly(
                        "/api/v1/ops/shard-readiness/v1-contract-alignment",
                        "/contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json",
                        "e/187/evidence/java-shard-readiness-v1-contract-alignment-v187.json",
                        "e/188/evidence/java-shard-readiness-v1-contract-alignment-snapshot-freeze-v188.json",
                        "e/189/evidence/java-shard-readiness-v187-historical-snapshot-compatibility-v189.json"
                );
        assertThat(handoff.verificationChecks())
                .contains(
                        "contract-name:shard-readiness.v1",
                        "alignment-version:Java v187",
                        "historical-snapshots-protected:true",
                        "execution-allowed:false"
                );
        assertThat(handoff.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-alignment-handoff-receipt-v190");
        assertThat(handoff.evidencePath())
                .isEqualTo("e/190/evidence/java-shard-readiness-v1-contract-alignment-handoff-v190.json");
        assertThat(handoff.status()).isEqualTo("passed");
    }
}
