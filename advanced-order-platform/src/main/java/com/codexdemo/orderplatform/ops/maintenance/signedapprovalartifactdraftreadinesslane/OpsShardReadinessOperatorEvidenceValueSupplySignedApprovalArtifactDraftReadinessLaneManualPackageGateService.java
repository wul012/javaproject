package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneManualPackageGateService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_MANUAL_PACKAGE_GATE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-lane-manual-package.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
      manualPackageGate() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
        .response(
            "Java v828",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog
                .lanes(20, 25),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog
                .blockers(20, 25),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog
                .allGates(),
            List.of(
                "signed-approval-artifact-draft-readiness-lane-manual-package-gate-metadata-only",
                "signed-approval-artifact-draft-readiness-lane-manual-package-gate-no-draft-authoring",
                "signed-approval-artifact-draft-readiness-lane-manual-package-gate-no-file-write",
                "signed-approval-artifact-draft-readiness-lane-manual-package-gate-no-process-start"));
  }
}
