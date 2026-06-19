package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_CATALOG;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-authoring-readiness-catalog.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      catalog() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
        .response(
            "Java v871",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog
                .allRequirements(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .allBlockers(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .allGates(),
            List.of(
                "signed-approval-artifact-draft-authoring-readiness-catalog-requirements-only",
                "signed-approval-artifact-draft-authoring-readiness-catalog-blockers-only",
                "signed-approval-artifact-draft-authoring-readiness-catalog-no-draft-text",
                "signed-approval-artifact-draft-authoring-readiness-catalog-no-runtime"));
  }
}
