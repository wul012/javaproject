package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogContinuityTests {

    @Test
    void keepsPostHandoffCatalogVersionsConsecutiveThroughV242() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions())
                .containsExactlyElementsOf(IntStream.rangeClosed(226, 242).boxed().toList());
    }

    @Test
    void keepsPostHandoffCatalogPathsUniqueAndVersionScoped() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .doesNotHaveDuplicates()
                .allSatisfy(receipt -> assertThat(receipt.evidencePath())
                        .contains("/" + receipt.version() + "/")
                        .endsWith("-v" + receipt.version() + ".json"));
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.evidencePaths())
                .doesNotHaveDuplicates();
    }

    @Test
    void keepsCatalogContinuityEvidencePathVersionedToV242() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_CATALOG_CONTINUITY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/242/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "catalog-continuity-v242.json"
                );
    }
}
