package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalogTests {

  @Test
  void exposesOneFailClosedBlockerPerAuthoringRequirement() {
    var blockers =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
            .allBlockers();

    assertThat(blockers)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .BLOCKER_COUNT);
    assertThat(blockers.stream().map(blocker -> blocker.code()).collect(Collectors.toSet()))
        .hasSize(25);
    assertThat(blockers)
        .allSatisfy(
            blocker -> {
              assertThat(blocker.code()).endsWith("_BLOCKER");
              assertThat(blocker.rejectionCode()).startsWith("REJECT_DRAFT_AUTHORING_READINESS_");
              assertThat(blocker.enforcement()).isEqualTo("fail-closed");
              assertThat(blocker.status()).isEqualTo("passed");
            });
  }
}
