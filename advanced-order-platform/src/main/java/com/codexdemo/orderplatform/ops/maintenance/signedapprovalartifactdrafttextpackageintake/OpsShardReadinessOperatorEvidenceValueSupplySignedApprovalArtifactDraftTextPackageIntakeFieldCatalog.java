package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake;

import java.util.List;
import java.util.stream.Stream;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog {

  static final int FIELD_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
              .IntakeField>
      allFields() {
    return Stream.concat(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationFieldCatalog
                .foundationFields()
                .stream(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceFieldCatalog
                .assuranceFields()
                .stream())
        .toList();
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
              .IntakeField>
      fields(int fromInclusive, int toExclusive) {
    return List.copyOf(allFields().subList(fromInclusive, toExclusive));
  }
}
