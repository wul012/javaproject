package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCatalogTests {

    @Test
    void catalogsEveryPostHandoffReceiptThroughV241() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions())
                .containsExactly(
                        226, 227, 228, 229,
                        230, 231, 232, 233,
                        234, 235, 236, 237,
                        238, 239, 240, 241
                );
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .hasSize(16)
                .extracting(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                        .Receipt::scope)
                .contains("post handoff catalog", "legacy registry alignment", "completion");
    }

    @Test
    void keepsPostHandoffCatalogOutsideFrozenV225Handoff() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();
        List<String> postHandoffPaths =
                OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.evidencePaths();

        assertThat(handoff.digestEvidence()).doesNotContainAnyElementsOf(postHandoffPaths);
        assertThat(handoff.handoffGuardEvidence()).doesNotContainAnyElementsOf(postHandoffPaths);
    }

    @Test
    void keepsPostHandoffCatalogEvidencePathVersionedToV241() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_POST_HANDOFF_CATALOG_EVIDENCE_PATH)
                .isEqualTo(
                        "e/241/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "post-handoff-catalog-v241.json"
                );
    }
}
