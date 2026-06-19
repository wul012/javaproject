package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CATALOG;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-submission-preflight-catalog.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      catalog() {
    return response(
        "Java v966",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog
            .allSlots(),
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog
            .allControls(),
        List.of("draft-text-package-submission-preflight-catalog-full"));
  }

  static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      response(
          String version,
          String endpoint,
          String profile,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
                      .SubmissionSlot>
              slots,
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
                      .ComparisonControl>
              controls,
          List<String> checks) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
        .response(
            version,
            endpoint,
            profile,
            slots,
            controls,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog
                .allGates(),
            checks);
  }
}
