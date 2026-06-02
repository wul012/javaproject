package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertContinuousCatalogFrom;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertReceiptCountAtLeast;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertVersionRun;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.evidencePaths;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffTwentyVersionCompletionTests {

    @Test
    void keepsV240ThroughV259CatalogedAsTheCurrentTwentyVersionRun() {
        assertVersionRun(240, 259);
        assertContinuousCatalogFrom(226);
        assertReceiptCountAtLeast(34);
    }

    @Test
    void keepsFrozenV225HandoffSeparateFromV240ThroughV259Receipts() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.version()).isEqualTo("Java v225");
        assertThat(handoff.evidencePath()).isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .EVIDENCE_PATH);
        assertThat(handoff.handoffGuardEvidence()).doesNotContainAnyElementsOf(evidencePaths());
        assertThat(handoff.digestEvidence()).doesNotContainAnyElementsOf(evidencePaths());
    }

    @Test
    void keepsTwentyVersionCompletionEvidencePathVersionedToV259() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                        .CONSUMER_READINESS_HANDOFF_TWENTY_VERSION_COMPLETION_EVIDENCE_PATH,
                259,
                "twenty-version-completion"
        );
    }
}
