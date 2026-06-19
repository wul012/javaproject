package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDraftAuthoringGateService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_DRAFT_AUTHORING_GATE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-authoring-gate.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      draftAuthoringGate() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
        .response(
            "Java v853",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                .slots(20, 25),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .guards(20, 25),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .allGates(),
            List.of(
                "signed-approval-artifact-draft-review-package-preflight-draft-authoring-gate-metadata-only",
                "signed-approval-artifact-draft-review-package-preflight-draft-authoring-gate-no-signed-draft-text",
                "signed-approval-artifact-draft-review-package-preflight-draft-authoring-gate-no-file-write",
                "signed-approval-artifact-draft-review-package-preflight-draft-authoring-gate-no-service-start"));
  }
}
