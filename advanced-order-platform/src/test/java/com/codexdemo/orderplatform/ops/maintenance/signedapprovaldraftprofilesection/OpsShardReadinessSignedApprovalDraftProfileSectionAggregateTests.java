package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionAggregateTests {

  @Test
  void aggregateCountsMatchPublishedLists() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.moduleCount()).isEqualTo(response.modules().size());
    assertThat(response.sourceRouteCount()).isEqualTo(response.sources().size());
    assertThat(response.sectionCount()).isEqualTo(response.sections().size());
    assertThat(response.renderedSectionCount()).isEqualTo(response.renderedSections().size());
    assertThat(response.fieldEntryCount()).isEqualTo(response.fieldEntries().size());
    assertThat(response.routeFieldLockCount()).isEqualTo(response.routeFieldLocks().size());
    assertThat(response.gateCount()).isEqualTo(response.gates().size());
  }

  @Test
  void sourceGateCountsRemainAttachedToEachSection() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.sections())
        .allSatisfy(section -> assertThat(section.sourceGateCount()).isEqualTo(20));
  }
}
