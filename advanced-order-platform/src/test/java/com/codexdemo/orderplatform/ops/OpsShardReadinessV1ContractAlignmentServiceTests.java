package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractAlignmentServiceTests {

    @Test
    void alignsFrozenRootReadinessWithShardReadinessV1WithoutOpeningExecution() {
        OpsShardReadinessV1ContractAlignmentResponse alignment =
                new OpsShardReadinessV1ContractAlignmentService(new OpsShardReadinessService()).alignment();

        assertThat(alignment.project()).isEqualTo("advanced-order-platform");
        assertThat(alignment.version()).isEqualTo("Java v187");
        assertThat(alignment.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(alignment.readOnly()).isTrue();
        assertThat(alignment.executionAllowed()).isFalse();
        assertThat(alignment.shardEnabled()).isFalse();
        assertThat(alignment.sourceReadinessVersion()).isEqualTo("Java v153");
        assertThat(alignment.sourceEndpoint()).isEqualTo("/api/v1/ops/shard-readiness");
        assertThat(alignment.sourceFixtureEndpoint()).isEqualTo("/contracts/java-shard-readiness-v153.fixture.json");
        assertThat(alignment.sourceEvidencePath()).isEqualTo("e/153/evidence/java-shard-readiness-v153.json");
        assertThat(alignment.minimalFields())
                .containsExactly(
                        "project",
                        "version",
                        "readOnly",
                        "executionAllowed",
                        "shardEnabled",
                        "shardCount",
                        "slotCount",
                        "routingMode",
                        "evidencePath",
                        "status"
                );
        assertThat(alignment.minimalFieldsFrozen()).isTrue();
        assertThat(alignment.readOnlyMatches()).isTrue();
        assertThat(alignment.executionBlocked()).isTrue();
        assertThat(alignment.shardRoutingDisabled()).isTrue();
        assertThat(alignment.shardCountsClosed()).isTrue();
        assertThat(alignment.routingModeFixtureBacked()).isTrue();
        assertThat(alignment.writeRoutingAllowed()).isFalse();
        assertThat(alignment.activeShardRouterAllowed()).isFalse();
        assertThat(alignment.credentialValueRead()).isFalse();
        assertThat(alignment.rawEndpointParsed()).isFalse();
        assertThat(alignment.managedAuditConnectionAllowed()).isFalse();
        assertThat(alignment.deploymentOrRollbackAllowed()).isFalse();
        assertThat(alignment.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(alignment.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-alignment-receipt-v187");
        assertThat(alignment.verificationChecks())
                .contains(
                        "contract-name:shard-readiness.v1",
                        "source-readiness-version:Java v153",
                        "minimal-field-count:10",
                        "execution-blocked:true"
                );
        assertThat(alignment.blockedOperations())
                .contains(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(alignment.evidencePath())
                .isEqualTo("e/187/evidence/java-shard-readiness-v1-contract-alignment-v187.json");
        assertThat(alignment.status()).isEqualTo("passed");
    }

    @Test
    void contractHelperKeepsMinimalFieldListStable() {
        assertThat(OpsShardReadinessV1Contract.minimalFields()).hasSize(10);
        assertThat(OpsShardReadinessV1Contract.alignsWithReadOnlyContract(
                new OpsShardReadinessService().readiness()
        )).isTrue();
    }
}
