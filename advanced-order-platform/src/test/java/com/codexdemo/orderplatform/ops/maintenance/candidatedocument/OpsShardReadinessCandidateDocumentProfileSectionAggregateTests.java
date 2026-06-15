package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionAggregateTests {

  @Test
  void aggregateCountsMatchPublishedLists() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.moduleCount()).isEqualTo(response.modules().size());
    assertThat(response.sourceRouteCount()).isEqualTo(response.sources().size());
    assertThat(response.sectionCount()).isEqualTo(response.sections().size());
    assertThat(response.renderedSectionCount()).isEqualTo(response.renderedSections().size());
    assertThat(response.fieldEntryCount()).isEqualTo(response.fieldEntries().size());
    assertThat(response.routeFieldLockCount()).isEqualTo(response.routeFieldLocks().size());
    assertThat(response.gateCount()).isEqualTo(response.gates().size());
  }

  @Test
  void lockedRouteFieldsMatchEndpointProfileVersionTriples() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.routeFieldLocks())
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RouteFieldLock
                ::lockedFieldCount)
        .containsOnly(3);
    assertThat(response.lockedRouteFieldCount()).isEqualTo(15);
  }
}
