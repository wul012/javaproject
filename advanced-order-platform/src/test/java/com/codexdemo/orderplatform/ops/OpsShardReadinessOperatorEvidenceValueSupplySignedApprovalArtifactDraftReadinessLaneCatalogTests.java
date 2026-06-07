package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalogTests {

    @Test
    void combinesFoundationAndAssuranceLanesWithoutManualPackageAuthoring() {
        var lanes = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog
                .allLanes();

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationLaneCatalog
                .foundationLanes()).hasSize(13);
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceLaneCatalog
                .assuranceLanes()).hasSize(12);
        assertThat(lanes).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog.LANE_COUNT);
        assertThat(lanes.stream().map(lane -> lane.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(lanes.stream().map(lane -> lane.blockerCode()).collect(Collectors.toSet())).hasSize(25);
        assertThat(lanes).allSatisfy(lane -> {
            assertThat(lane.status()).isEqualTo("passed");
            assertThat(lane.sourceField()).isNotBlank();
            assertThat(lane.reviewPurpose()).isNotBlank();
            assertThat(lane.manualReviewBlocker()).isNotBlank();
            assertThat(lane.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
    }
}
