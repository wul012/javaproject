package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptScopeUniquenessTests {

    @Test
    void keepsPostHandoffReceiptScopesUniqueAndNormalized() {
        List<String> scopes = OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .receipts()
                .stream()
                .map(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                        .Receipt::scope)
                .toList();

        assertThat(scopes).doesNotHaveDuplicates();
        assertThat(scopes).allSatisfy(scope -> {
            assertThat(scope).isEqualTo(scope.trim());
            assertThat(scope).isEqualTo(scope.toLowerCase());
            assertThat(scope).doesNotContain("  ");
        });
    }

    @Test
    void keepsReceiptScopeUniquenessPathVersionedToV287() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_RECEIPT_SCOPE_UNIQUENESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/287/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "receipt-scope-uniqueness-v287.json"
                );
    }
}
