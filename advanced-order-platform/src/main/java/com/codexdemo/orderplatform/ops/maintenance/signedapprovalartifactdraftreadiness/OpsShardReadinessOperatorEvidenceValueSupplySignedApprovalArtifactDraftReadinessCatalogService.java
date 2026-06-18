package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CATALOG;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-catalog.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
      catalog() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport
        .response(
            "Java v771",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog
                .allItems(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog
                .allOwnershipRules(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog
                .allGates(),
            List.of(
                "signed-approval-artifact-draft-readiness-catalog-item-count-25",
                "signed-approval-artifact-draft-readiness-catalog-ownership-count-20",
                "signed-approval-artifact-draft-readiness-catalog-gate-count-20",
                "signed-approval-artifact-draft-readiness-catalog-split-foundation-assurance-items",
                "signed-approval-artifact-draft-readiness-catalog-no-manual-artifact-draft"));
  }
}
