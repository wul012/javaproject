package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSourceIdentityDigestService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths
              .BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_ACCEPTANCE_PRECHECK_SOURCE_IDENTITY_DIGEST;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-source-identity-digest.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
      sourceIdentityDigest() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService
        .response(
            "Java v1010",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCheckpointCatalog
                .allCheckpoints()
                .subList(0, 3),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCheckpointCatalog
                .allGuards()
                .subList(0, 3),
            List.of("draft-text-package-comparison-acceptance-precheck-source-identity-digest"));
  }
}
