package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionGateCatalogTests {

  @Test
  void gateCatalogPublishesNoRuntimeGateFloor() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.gateCount()).isEqualTo(46);
    assertThat(response.gates())
        .hasSize(46)
        .allSatisfy(
            gate ->
                assertThat(gate)
                    .startsWith("signed-approval-draft-profile-section-registry-no-runtime-gate-"));
  }

  @Test
  void checksRecordAllFailClosedGateCounts() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.checks())
        .contains(
            "signed-approval-draft-profile-section-registry-gate-count-46",
            "signed-approval-draft-profile-section-registry-draft-materialization-disabled",
            "signed-approval-draft-profile-section-registry-runtime-payload-disabled");
  }
}
