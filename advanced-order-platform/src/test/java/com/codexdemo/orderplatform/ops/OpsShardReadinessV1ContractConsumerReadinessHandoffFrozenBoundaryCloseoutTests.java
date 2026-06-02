package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffFrozenBoundaryCloseoutTests {

    @Test
    void keepsV280ThroughV284FrozenBoundaryScopesCatalogedInOrder() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions())
                .containsSubsequence(280, 281, 282, 283, 284);
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .extracting(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                        .Receipt::scope)
                .containsSubsequence(
                        "frozen boundary flags",
                        "frozen fixture endpoint stability",
                        "frozen digest count parity",
                        "catalog boundary schema strictness",
                        "frozen boundary closeout"
                );
    }

    @Test
    void keepsFrozenBoundaryCloseoutPathVersionedToV284() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_FROZEN_BOUNDARY_CLOSEOUT_EVIDENCE_PATH)
                .isEqualTo(
                        "e/284/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "frozen-boundary-closeout-v284.json"
                );
    }
}
