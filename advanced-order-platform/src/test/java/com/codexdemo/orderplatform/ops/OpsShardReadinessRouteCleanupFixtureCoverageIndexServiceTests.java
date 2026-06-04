package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupFixtureCoverageIndexServiceTests {

    @Test
    void indexesPostCompletionFixtureAndTestCoverage() {
        OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse index =
                OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.fixtureCoverageIndexService().index();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(403);
        assertThat(index.project()).isEqualTo("advanced-order-platform");
        assertThat(index.version()).isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(index.readOnly()).isTrue();
        assertThat(index.executionAllowed()).isFalse();
        assertThat(index.fixtureCoverageIndexEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-fixture-coverage-index");
        assertThat(index.fixtureCoverageIndexProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-fixture-coverage-index.v1");
        assertThat(index.maintenanceBoundaryReportEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-boundary-report");
        assertThat(index.coverageItemCount()).isEqualTo(5);
        assertThat(index.coverageItems())
                .extracting(OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse.CoverageItem::name)
                .containsExactly(
                        "post-completion-fixtures",
                        "controller-split-test",
                        "route-paths-test",
                        "maintenance-boundary-test",
                        "release-bundle-test"
                );
        assertThat(index.coverageItems()).allSatisfy(item -> assertThat(item.status()).isEqualTo("covered"));
        assertThat(index.status()).isEqualTo("passed");
    }
}
