package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PackageCatalogTests {

  @Test
  void acceptancePackageConsumesSustainmentAndPinsLatestNodePlan() {
    var response = PackageTestData.registry();

    assertThat(response.version()).isEqualTo("Java v1634");
    assertThat(response.sourcePlan()).isEqualTo("Node v1903");
    assertThat(response.nodeParallelPlan()).isEqualTo("Node v1879-v1903");
    assertThat(response.sourceSustainmentVersion()).isEqualTo("Java v1604");
    assertThat(response.sourceCloseoutVersion()).isEqualTo("Java v1579");
    assertThat(response.sourceSplitVersion()).isEqualTo("Java v1570");
    assertThat(response.sourceSnapshotCount()).isEqualTo(1);
    assertThat(response.lineageEntryCount()).isEqualTo(3);
    assertThat(response.decisionRecordCount()).isEqualTo(6);
    assertThat(response.archiveItemCount()).isEqualTo(5);
    assertThat(response.reviewItemCount()).isEqualTo(5);
    assertThat(response.ciEvidenceCount()).isEqualTo(5);
    assertThat(response.runtimeBoundaryCount()).isEqualTo(7);
    assertThat(response.nextChangeRuleCount()).isEqualTo(6);
    assertThat(response.scorecardEntryCount()).isEqualTo(9);
    assertThat(response.markdownSectionCount()).isEqualTo(9);
    assertThat(response.status()).isEqualTo("passed");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void acceptancePackageEvidenceIsReady() {
    var response = PackageTestData.registry();

    assertThat(response.lineage())
        .extracting(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .VersionLineage
                ::stage)
        .containsExactly(
            "route-path-split", "route-path-split-closeout", "route-path-split-sustainment");
    assertThat(response.decisions())
        .allSatisfy(decision -> assertThat(decision.accepted()).isTrue());
    assertThat(response.archiveItems()).allSatisfy(item -> assertThat(item.ready()).isTrue());
    assertThat(response.reviewItems()).allSatisfy(item -> assertThat(item.passed()).isTrue());
    assertThat(response.ciEvidence()).allSatisfy(item -> assertThat(item.passed()).isTrue());
    assertThat(response.runtimeBoundaries())
        .allSatisfy(boundary -> assertThat(boundary.locked()).isTrue());
    assertThat(response.nextChangeRules()).allSatisfy(rule -> assertThat(rule.ready()).isTrue());
    assertThat(response.scorecard()).allSatisfy(entry -> assertThat(entry.passed()).isTrue());
  }
}
