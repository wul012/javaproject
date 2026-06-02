package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffV275V289FifteenVersionCompletionTests {

    @Test
    void keepsV275ThroughV289CatalogedAsCompleteFifteenVersionRun() {
        List<Integer> versions = OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .versions();

        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .hasSizeGreaterThanOrEqualTo(64);
        assertThat(versions).containsSubsequence(
                275, 276, 277, 278, 279,
                280, 281, 282, 283, 284,
                285, 286, 287, 288, 289
        );
        assertThat(versions.stream()
                .filter(version -> version >= 275 && version <= 289)
                .toList())
                .containsExactly(
                        275, 276, 277, 278, 279,
                        280, 281, 282, 283, 284,
                        285, 286, 287, 288, 289
                );
    }

    @Test
    void keepsV275ThroughV289ScopesCatalogedInOrder() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .extracting(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                        .Receipt::scope)
                .containsSubsequence(
                        "validation artifact depth",
                        "readme description alignment",
                        "walkthrough filename alignment",
                        "evidence path structure stability",
                        "auditability closeout",
                        "frozen boundary flags",
                        "frozen fixture endpoint stability",
                        "frozen digest count parity",
                        "catalog boundary schema strictness",
                        "frozen boundary closeout",
                        "catalog receipt count floor",
                        "archive artifact byte floor",
                        "receipt scope uniqueness",
                        "post handoff catalog growth closeout",
                        "v275 v289 fifteen version completion"
                );
    }

    @Test
    void keepsV275V289FifteenVersionCompletionPathVersionedToV289() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_V275_V289_FIFTEEN_VERSION_COMPLETION_EVIDENCE_PATH)
                .isEqualTo(
                        "e/289/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "v275-v289-fifteen-version-completion-v289.json"
                );
    }
}
