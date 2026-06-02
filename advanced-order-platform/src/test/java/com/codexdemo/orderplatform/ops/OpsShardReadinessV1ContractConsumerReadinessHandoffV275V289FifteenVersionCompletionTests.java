package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertExactVersionWindow;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertReceiptCountAtLeast;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertVersionRun;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.scopes;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffV275V289FifteenVersionCompletionTests {

    @Test
    void keepsV275ThroughV289CatalogedAsCompleteFifteenVersionRun() {
        assertReceiptCountAtLeast(64);
        assertVersionRun(275, 289);
        assertExactVersionWindow(275, 289);
    }

    @Test
    void keepsV275ThroughV289ScopesCatalogedInOrder() {
        assertThat(scopes())
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
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffService
                        .CONSUMER_READINESS_HANDOFF_V275_V289_FIFTEEN_VERSION_COMPLETION_EVIDENCE_PATH,
                289,
                "v275-v289-fifteen-version-completion"
        );
    }
}
