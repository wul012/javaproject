package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertReceiptCountAtLeast;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertVersionRun;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.scopes;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCatalogGrowthCloseoutTests {

    @Test
    void keepsV275ThroughV288PostHandoffGrowthRunCatalogedInOrder() {
        assertReceiptCountAtLeast(63);
        assertVersionRun(275, 288);
        assertThat(scopes())
                .containsSubsequence(
                        "catalog receipt count floor",
                        "archive artifact byte floor",
                        "receipt scope uniqueness",
                        "post handoff catalog growth closeout"
                );
    }

    @Test
    void keepsPostHandoffCatalogGrowthCloseoutPathVersionedToV288() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                        .CONSUMER_READINESS_HANDOFF_POST_HANDOFF_CATALOG_GROWTH_CLOSEOUT_EVIDENCE_PATH,
                288,
                "post-handoff-catalog-growth-closeout"
        );
    }
}
