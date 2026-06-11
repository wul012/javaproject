package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTests {

    @Test
    void receiptClosesOutAcceptancePackage() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTestSupport
                .receipt();

        assertThat(response.version()).isEqualTo("Java v1637");
        assertThat(response.sourcePlan()).isEqualTo("Node v1903");
        assertThat(response.nodeParallelPlan()).isEqualTo("Node v1879-v1903");
        assertThat(response.sourceAcceptancePackageVersion()).isEqualTo("Java v1634");
        assertThat(response.acceptedCriteriaCount()).isEqualTo(7);
        assertThat(response.markdownLineCount()).isEqualTo(7);
        assertThat(response.checks()).hasSize(9);
        assertThat(response.status()).isEqualTo("passed");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.acceptedCriteria())
                .allSatisfy(criterion -> assertThat(criterion.status()).isEqualTo("accepted"));
    }

    @Test
    void controllerExposesReceiptRoute() {
        assertThat(OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE_CLOSEOUT_RECEIPT)
                .isEqualTo(OpsShardReadinessRoutePaths
                        .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE_CLOSEOUT_RECEIPT);

        var response =
                new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptController(
                        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTestSupport
                                .service()
                ).receipt();

        assertThat(response.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/release-acceptance-route-path-split-sustainment-acceptance-package-closeout-receipt");
        assertThat(response.profile()).isEqualTo(
                "java-shard-readiness-release-acceptance-route-path-split-sustainment-acceptance-package-closeout-receipt.v1");
    }

    @Test
    void receiptCollectionsAreImmutable() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTestSupport
                .receipt();

        assertThatThrownBy(() -> response.acceptedCriteria().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.markdownLines().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.checks().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
