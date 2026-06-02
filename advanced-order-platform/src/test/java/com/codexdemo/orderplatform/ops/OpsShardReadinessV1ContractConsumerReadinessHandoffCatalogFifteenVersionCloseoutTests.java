package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogFifteenVersionCloseoutTests {

    @Test
    void keepsV260ThroughV273CatalogedAsTheActiveFifteenVersionRunPreview() {
        var versions = OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions();

        assertThat(versions).containsSubsequence(IntStream.rangeClosed(260, 273).boxed().toArray(Integer[]::new));
        assertThat(versions).containsExactlyElementsOf(IntStream.rangeClosed(226, versions.getLast()).boxed().toList());
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .hasSizeGreaterThanOrEqualTo(48);
    }

    @Test
    void keepsV270ThroughV273FrozenContractCloseoutScopesCatalogedInOrder() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .extracting(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                        .Receipt::scope)
                .containsSubsequence(
                        "v1 endpoint registry stability",
                        "frozen payload stability",
                        "post handoff isolation",
                        "catalog fifteen version closeout"
                );
    }

    @Test
    void keepsCatalogFifteenVersionCloseoutPathVersionedToV273() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_CATALOG_FIFTEEN_VERSION_CLOSEOUT_EVIDENCE_PATH)
                .isEqualTo(
                        "e/273/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "catalog-fifteen-version-closeout-v273.json"
                );
    }
}
