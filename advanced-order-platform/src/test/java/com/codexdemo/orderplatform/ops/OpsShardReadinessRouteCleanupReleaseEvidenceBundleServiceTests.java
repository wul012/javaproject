package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupReleaseEvidenceBundleServiceTests {

    @Test
    void buildsReleaseEvidenceBundleFromCertificateCiAndTags() {
        OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse bundle =
                OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.releaseEvidenceBundleService().bundle();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(395);
        assertThat(bundle.project()).isEqualTo("advanced-order-platform");
        assertThat(bundle.version()).isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(bundle.readOnly()).isTrue();
        assertThat(bundle.executionAllowed()).isFalse();
        assertThat(bundle.releaseEvidenceBundleEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-release-evidence-bundle");
        assertThat(bundle.releaseEvidenceBundleProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-release-evidence-bundle.v1");
        assertThat(bundle.sourceCount()).isEqualTo(3);
        assertThat(bundle.sources())
                .contains(
                        "/api/v1/ops/shard-readiness/route-cleanup-completion-certificate",
                        "/api/v1/ops/shard-readiness/route-cleanup-ci-run-attestation",
                        "/api/v1/ops/shard-readiness/route-cleanup-tag-manifest"
                );
        assertThat(bundle.bundleItemCount()).isEqualTo(5);
        assertThat(bundle.bundleItems())
                .extracting(OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse.BundleItem::name)
                .containsExactly(
                        "completion-certificate",
                        "ci-run-attestation",
                        "tag-manifest",
                        "boundary-status",
                        "latest-version"
                );
        assertThat(bundle.bundleItems())
                .allSatisfy(item -> assertThat(item.status()).isEqualTo("passed"));
        assertThat(bundle.decision()).isEqualTo("release-evidence-bundle-ready-for-route");
        assertThat(bundle.status()).isEqualTo("passed");
    }
}
