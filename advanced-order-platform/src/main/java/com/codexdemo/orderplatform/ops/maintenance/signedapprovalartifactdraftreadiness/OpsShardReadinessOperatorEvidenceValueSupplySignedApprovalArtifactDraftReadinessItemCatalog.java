package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog {

  static final int ITEM_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
              .ReadinessItem>
      allItems() {
    List<
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
                .ReadinessItem>
        items = new ArrayList<>();
    items.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationItemCatalog
            .foundationItems());
    items.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceItemCatalog
            .assuranceItems());
    return List.copyOf(items);
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
              .ReadinessItem>
      items(int fromInclusive, int toExclusive) {
    return List.copyOf(allItems().subList(fromInclusive, toExclusive));
  }
}
