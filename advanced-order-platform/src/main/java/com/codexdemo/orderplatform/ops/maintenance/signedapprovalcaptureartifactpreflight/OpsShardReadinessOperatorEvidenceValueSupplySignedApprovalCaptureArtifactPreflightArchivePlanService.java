package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_ARCHIVE_PLAN;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-artifact-preflight-archive.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      plan() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
        .response(
            "Java v753",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                .fragments(20, 25),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                .seals(20, 25),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
                .allGates(),
            List.of(
                "signed-approval-capture-artifact-preflight-archive-no-file-write",
                "signed-approval-capture-artifact-preflight-archive-closeout-seal-present",
                "signed-approval-capture-artifact-preflight-archive-no-process-start"));
  }
}
