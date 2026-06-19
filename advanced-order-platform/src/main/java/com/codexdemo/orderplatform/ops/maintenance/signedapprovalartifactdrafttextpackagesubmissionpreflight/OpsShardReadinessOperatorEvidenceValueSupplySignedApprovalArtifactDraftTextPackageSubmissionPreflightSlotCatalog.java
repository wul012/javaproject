package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import java.util.List;
import java.util.stream.Stream;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog {

  static final int SLOT_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
              .SubmissionSlot>
      allSlots() {
    return Stream.concat(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightFoundationSlotCatalog
                .foundationSlots()
                .stream(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightAssuranceSlotCatalog
                .assuranceSlots()
                .stream())
        .toList();
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
              .SubmissionSlot>
      slots(int fromInclusive, int toExclusive) {
    return List.copyOf(allSlots().subList(fromInclusive, toExclusive));
  }
}
