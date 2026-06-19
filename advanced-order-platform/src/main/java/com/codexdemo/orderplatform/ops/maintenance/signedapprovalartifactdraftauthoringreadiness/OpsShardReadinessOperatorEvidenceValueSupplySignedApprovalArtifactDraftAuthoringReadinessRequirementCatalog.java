package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog {

  static final int REQUIREMENT_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
              .AuthoringRequirement>
      allRequirements() {
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
                .AuthoringRequirement>
        requirements = new ArrayList<>();
    requirements.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessFoundationRequirementCatalog
            .foundationRequirements());
    requirements.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceRequirementCatalog
            .assuranceRequirements());
    return List.copyOf(requirements);
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
              .AuthoringRequirement>
      requirements(int fromInclusive, int toExclusive) {
    return List.copyOf(allRequirements().subList(fromInclusive, toExclusive));
  }
}
