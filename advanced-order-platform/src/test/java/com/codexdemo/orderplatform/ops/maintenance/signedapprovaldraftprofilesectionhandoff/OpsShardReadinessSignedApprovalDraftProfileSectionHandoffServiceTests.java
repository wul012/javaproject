package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffServiceTests {

  @Test
  void buildsReadOnlyHandoffFromSignedApprovalDraftRegistry() {
    var response = HandoffTestData.handoff();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1262");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForDraftProfileSectionHandoff()).isTrue();
    assertThat(response.sourcePlan()).isEqualTo("Node v1506");
    assertThat(response.sourceRegistryVersion()).isEqualTo("Java v1237");
    assertThat(response.moduleCount()).isEqualTo(8);
    assertThat(response.sourceCount()).isEqualTo(1);
    assertThat(response.sectionHandoffCount()).isEqualTo(5);
    assertThat(response.routeContractCount()).isEqualTo(5);
    assertThat(response.boundaryDecisionCount()).isEqualTo(7);
    assertThat(response.renderedHandoffCount()).isEqualTo(5);
    assertThat(response.gateCount()).isEqualTo(52);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void preservesTransferredRegistryCounts() {
    var response = HandoffTestData.handoff();

    assertThat(response.transferredSectionCount()).isEqualTo(5);
    assertThat(response.transferredRouteFieldLockCount()).isEqualTo(5);
    assertThat(response.transferredLockedRouteFieldCount()).isEqualTo(25);
  }
}
