package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffAggregateTests {

  @Test
  void aggregateCountsMatchPublishedLists() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

    assertThat(response.moduleCount()).isEqualTo(response.modules().size());
    assertThat(response.sourceCount()).isEqualTo(response.sources().size());
    assertThat(response.sectionHandoffCount()).isEqualTo(response.sectionHandoffs().size());
    assertThat(response.routeContractCount()).isEqualTo(response.routeContracts().size());
    assertThat(response.boundaryDecisionCount()).isEqualTo(response.boundaryDecisions().size());
    assertThat(response.renderedHandoffCount()).isEqualTo(response.renderedHandoffs().size());
    assertThat(response.gateCount()).isEqualTo(response.gates().size());
  }

  @Test
  void transferredCountsMirrorRouteContractsAndSections() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

    assertThat(response.transferredSectionCount()).isEqualTo(response.sectionHandoffs().size());
    assertThat(response.transferredRouteFieldLockCount())
        .isEqualTo(response.routeContracts().size());
    assertThat(response.transferredLockedRouteFieldCount()).isEqualTo(25);
  }
}
