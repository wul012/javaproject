package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexRendererControllerTests {

  @Test
  void markdownDescribesArchiveIndexSections() {
    var response =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexTestSupport
            .index();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Receipt",
            "Criteria Echoes",
            "Archive Items",
            "Verification Gates",
            "Handoff Notes");
    assertThat(response.checks())
        .contains(
            "release-acceptance-route-path-split-closeout-archive-index-source-plan-Node v1937",
            "release-acceptance-route-path-split-closeout-archive-index-source-receipt-version-Java v1637",
            "release-acceptance-route-path-split-closeout-archive-index-ready-for-retention");
  }

  @Test
  void controllerExposesArchiveIndexRoute() {
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE_CLOSEOUT_ARCHIVE_INDEX)
        .isEqualTo(
            OpsShardReadinessRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE_CLOSEOUT_ARCHIVE_INDEX);

    var response =
        new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexController(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexTestSupport
                    .service())
            .index();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/release-acceptance-route-path-split-sustainment-acceptance-package-closeout-archive-index");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-release-acceptance-route-path-split-sustainment-acceptance-package-closeout-archive-index.v1");
  }
}
