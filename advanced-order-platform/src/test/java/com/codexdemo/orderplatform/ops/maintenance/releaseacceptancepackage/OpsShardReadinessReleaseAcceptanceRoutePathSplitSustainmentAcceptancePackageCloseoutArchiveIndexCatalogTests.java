package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexCatalogTests {

  @Test
  void archiveIndexConsumesCloseoutReceiptAndPinsLatestNodePlan() {
    var response =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexTestSupport
            .index();

    assertThat(response.version()).isEqualTo("Java v1652");
    assertThat(response.sourcePlan()).isEqualTo("Node v1937");
    assertThat(response.nodeParallelPlan()).isEqualTo("Node v1935-v1937");
    assertThat(response.sourceReceiptVersion()).isEqualTo("Java v1637");
    assertThat(response.sourceAcceptancePackageVersion()).isEqualTo("Java v1634");
    assertThat(response.sourceSnapshotCount()).isEqualTo(1);
    assertThat(response.criteriaEchoCount()).isEqualTo(7);
    assertThat(response.archiveItemCount()).isEqualTo(5);
    assertThat(response.verificationGateCount()).isEqualTo(5);
    assertThat(response.handoffNoteCount()).isEqualTo(4);
    assertThat(response.markdownSectionCount()).isEqualTo(5);
    assertThat(response.checks()).hasSize(19);
    assertThat(response.status()).isEqualTo("passed");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void archiveIndexEvidenceIsReady() {
    var response =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexTestSupport
            .index();

    assertThat(response.criteriaEchoes())
        .allSatisfy(echo -> assertThat(echo.status()).isEqualTo("accepted"));
    assertThat(response.archiveItems()).allSatisfy(item -> assertThat(item.ready()).isTrue());
    assertThat(response.verificationGates()).allSatisfy(gate -> assertThat(gate.passed()).isTrue());
    assertThat(response.handoffNotes()).allSatisfy(note -> assertThat(note.ready()).isTrue());
  }
}
