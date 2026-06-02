package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffConsumerBoundaryCompletionTests {

    @Test
    void keepsAllSecondGroupConsumerBoundaryReceiptsCataloged() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .extracting(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                        .Receipt::scope)
                .containsSubsequence(
                        "blocked operation catalog",
                        "get only probe boundary",
                        "credential raw endpoint boundary",
                        "audit deployment boundary",
                        "process control boundary",
                        "write router boundary",
                        "consumer boundary completion"
                );
    }

    @Test
    void keepsConsumerBoundaryCompletionFullyReadOnly() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.probesAreGetOnly()).isTrue();
        assertThat(handoff.upstreamActionsAllowed()).isFalse();
        assertThat(handoff.writeRoutingAllowed()).isFalse();
        assertThat(handoff.activeShardRouterAllowed()).isFalse();
        assertThat(handoff.credentialValueRead()).isFalse();
        assertThat(handoff.rawEndpointParsed()).isFalse();
        assertThat(handoff.managedAuditConnectionAllowed()).isFalse();
        assertThat(handoff.deploymentOrRollbackAllowed()).isFalse();
        assertThat(handoff.startsJavaService()).isFalse();
        assertThat(handoff.startsMiniKvService()).isFalse();
        assertThat(handoff.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
    }

    @Test
    void keepsConsumerBoundaryCompletionEvidencePathVersionedToV253() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_CONSUMER_BOUNDARY_COMPLETION_EVIDENCE_PATH)
                .isEqualTo(
                        "e/253/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "consumer-boundary-completion-v253.json"
                );
    }
}
