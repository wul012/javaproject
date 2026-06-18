package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog {

  static final int LANE_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
              .ReadinessLane>
      allLanes() {
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
                .ReadinessLane>
        lanes = new ArrayList<>();
    lanes.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationLaneCatalog
            .foundationLanes());
    lanes.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceLaneCatalog
            .assuranceLanes());
    return List.copyOf(lanes);
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
              .ReadinessLane>
      lanes(int fromInclusive, int toExclusive) {
    return List.copyOf(allLanes().subList(fromInclusive, toExclusive));
  }
}
