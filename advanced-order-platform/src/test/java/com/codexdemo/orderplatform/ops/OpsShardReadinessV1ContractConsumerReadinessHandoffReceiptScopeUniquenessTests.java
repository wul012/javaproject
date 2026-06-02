package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.scopes;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptScopeUniquenessTests {

    @Test
    void keepsPostHandoffReceiptScopesUniqueAndNormalized() {
        assertThat(scopes()).doesNotHaveDuplicates();
        assertThat(scopes()).allSatisfy(scope -> {
            assertThat(scope).isEqualTo(scope.trim());
            assertThat(scope).isEqualTo(scope.toLowerCase());
            assertThat(scope).doesNotContain("  ");
        });
    }

    @Test
    void keepsReceiptScopeUniquenessPathVersionedToV287() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffService
                        .CONSUMER_READINESS_HANDOFF_RECEIPT_SCOPE_UNIQUENESS_EVIDENCE_PATH,
                287,
                "receipt-scope-uniqueness"
        );
    }
}
