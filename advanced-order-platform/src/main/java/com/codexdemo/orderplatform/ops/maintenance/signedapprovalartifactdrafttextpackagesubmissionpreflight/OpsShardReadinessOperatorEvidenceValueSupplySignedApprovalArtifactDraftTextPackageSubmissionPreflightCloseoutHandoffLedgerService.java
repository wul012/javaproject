package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffLedgerService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CLOSEOUT_HANDOFF_LEDGER;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-submission-preflight-closeout-handoff-ledger.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse
      ledger() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport
        .response(
            "Java v980",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffCatalog
                .allItems(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutGuardrailCatalog
                .allGuardrails(),
            List.of(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutAcceptanceStateCatalog
                .acceptanceChecks());
  }
}
