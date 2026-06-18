package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_REDACTION_PROVENANCE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-redaction.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
      redactionProvenance() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport
        .response(
            "Java v776",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog
                .items(15, 19),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog
                .ownershipRules(6, 7),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog
                .gates(7, 9),
            List.of(
                "signed-approval-artifact-draft-readiness-redacted-value-and-shape-ready",
                "signed-approval-artifact-draft-readiness-redaction-provenance-ready",
                "signed-approval-artifact-draft-readiness-no-value-body-or-import"));
  }
}
