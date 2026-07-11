package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.scopes;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptScopeUniquenessTests {

  @Test
  void keepsPostHandoffReceiptScopesUniqueAndNormalized() {
    assertThat(scopes()).doesNotHaveDuplicates();
    assertThat(scopes())
        .allSatisfy(
            scope -> {
              assertThat(scope).isEqualTo(scope.trim());
              assertThat(scope).isEqualTo(scope.toLowerCase());
              assertThat(scope).doesNotContain("  ");
            });
  }

  @Test
  void keepsReceiptScopeUniquenessPathVersionedToV287() {
    assertEvidencePath(
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_RECEIPT_SCOPE_UNIQUENESS_EVIDENCE_PATH,
        287,
        "receipt-scope-uniqueness");
  }
}
