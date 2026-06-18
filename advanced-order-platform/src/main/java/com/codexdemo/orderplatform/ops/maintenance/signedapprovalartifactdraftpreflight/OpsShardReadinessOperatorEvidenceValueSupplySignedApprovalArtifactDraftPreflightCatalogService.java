package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CATALOG;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-preflight-catalog.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      catalog() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
        .response(
            "Java v796",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                .allFields(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                .allGuards(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog
                .allGates(),
            List.of(
                "signed-approval-artifact-draft-preflight-catalog-field-count-25",
                "signed-approval-artifact-draft-preflight-catalog-guard-count-25",
                "signed-approval-artifact-draft-preflight-catalog-gate-count-20",
                "signed-approval-artifact-draft-preflight-catalog-no-real-manual-draft"));
  }
}
