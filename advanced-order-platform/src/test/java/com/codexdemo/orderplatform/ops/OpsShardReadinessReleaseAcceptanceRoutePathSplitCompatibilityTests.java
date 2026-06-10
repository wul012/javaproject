package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityTests {

    @Test
    void stableBarrelKeepsReleaseAcceptanceRouteValues() {
        assertThat(OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH)
                .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH);
        assertThat(OpsShardReadinessReleaseAcceptanceRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY)
                .isEqualTo(OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY);
        assertThat(OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY)
                .isEqualTo(OpsShardReadinessRoutePaths
                        .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY);
    }

    @Test
    void routeCatalogMapsStableAndSplitEntrypointsOneForOne() {
        var routes = OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog.routes();
        var compatibilityChecks =
                OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog.compatibilityChecks();

        assertThat(routes).hasSize(11);
        assertThat(routes)
                .allSatisfy(route -> {
                    assertThat(route.stableEntrypoint()).startsWith("OpsShardReadinessRoutePaths.");
                    assertThat(route.splitEntrypoint())
                            .startsWith("OpsShardReadinessReleaseAcceptanceRoutePaths.");
                    assertThat(route.legacyCompatible()).isTrue();
                    assertThat(route.status()).isEqualTo("passed");
                });
        assertThat(compatibilityChecks).hasSize(11);
        assertThat(compatibilityChecks)
                .allSatisfy(check -> assertThat(check.matched()).isTrue());
    }
}
