package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityTests {

  @Test
  void stableBarrelKeepsReleaseAcceptanceRouteValues() {
    assertThat(OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH)
        .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH);
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY)
        .isEqualTo(OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY);
    assertThat(
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY)
        .isEqualTo(
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY);
  }

  @Test
  void routeCatalogMapsStableAndSplitEntrypointsOneForOne() {
    var routes = OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog.routes();
    var compatibilityChecks =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityCatalog.checks(routes);

    assertThat(routes).hasSize(11);
    assertThat(routes)
        .allSatisfy(
            route -> {
              assertThat(route.stableEntrypoint()).startsWith("OpsShardReadinessRoutePaths.");
              assertThat(route.splitEntrypoint())
                  .startsWith("OpsShardReadinessReleaseAcceptanceRoutePaths.");
              assertThat(route.stablePath()).isEqualTo(route.splitPath());
              assertThat(route.path()).isEqualTo(route.splitPath());
              assertThat(route.legacyCompatible()).isTrue();
              assertThat(route.status()).isEqualTo("passed");
            });
    assertThat(compatibilityChecks).hasSize(11);
    assertThat(compatibilityChecks)
        .allSatisfy(
            check -> {
              assertThat(check.stableValue()).isEqualTo(check.splitValue());
              assertThat(check.matched()).isTrue();
            });
  }
}
