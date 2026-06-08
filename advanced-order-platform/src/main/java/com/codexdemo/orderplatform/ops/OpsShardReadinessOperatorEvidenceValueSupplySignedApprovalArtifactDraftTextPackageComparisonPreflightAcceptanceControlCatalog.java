package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightAcceptanceControlCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightAcceptanceControlCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
            .AcceptanceControl> allControls() {
        return controlsFor(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightLaneCatalog
                .allLanes());
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
            .AcceptanceControl> controlsFor(
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
                    .ComparisonLane> lanes
    ) {
        return lanes.stream()
                .map(lane -> OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
                        .control(
                                "acceptance-control-" + lane.code(),
                                "draft-text-package-comparison",
                                "Reject submitted package material when lane is missing, uncompared, or unacceptable: "
                                        + lane.comparisonLane(),
                                lane.acceptanceControl()
                        ))
                .toList();
    }
}

