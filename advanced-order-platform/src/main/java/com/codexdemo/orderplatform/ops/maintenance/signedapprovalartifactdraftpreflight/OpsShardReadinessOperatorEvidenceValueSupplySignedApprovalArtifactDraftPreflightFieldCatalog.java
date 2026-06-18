package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog {

  static final int FIELD_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
              .DraftField>
      allFields() {
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
                .DraftField>
        fields = new ArrayList<>();
    fields.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationFieldCatalog
            .foundationFields());
    fields.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceFieldCatalog
            .assuranceFields());
    return List.copyOf(fields);
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
              .DraftField>
      fields(int fromInclusive, int toExclusive) {
    return List.copyOf(allFields().subList(fromInclusive, toExclusive));
  }
}
