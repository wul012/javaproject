package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffProcessControlBoundaryTests {

    @Test
    void keepsJavaMiniKvAndNodeProcessControlDisabled() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.startsJavaService()).isFalse();
        assertThat(handoff.startsMiniKvService()).isFalse();
        assertThat(handoff.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(handoff.blockedOperations()).contains("node-start-or-stop-java-or-mini-kv");
        assertThat(handoff.handoffChecks()).contains("node-may-start-or-stop-java-or-mini-kv:false");
    }

    @Test
    void keepsProcessControlBoundaryEvidencePathVersionedToV251() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_PROCESS_CONTROL_BOUNDARY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/251/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "process-control-boundary-v251.json"
                );
    }
}
