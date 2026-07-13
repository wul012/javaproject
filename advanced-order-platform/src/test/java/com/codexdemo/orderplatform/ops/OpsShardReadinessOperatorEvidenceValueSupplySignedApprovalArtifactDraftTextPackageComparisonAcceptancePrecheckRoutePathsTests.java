package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckPolicyExecutionArchiveService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSignatureEvidenceValueService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSourceIdentityDigestService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePathsTests {

  @Test
  void acceptancePrecheckEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.of(
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_ACCEPTANCE_PRECHECK_CATALOG,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_ACCEPTANCE_PRECHECK_SOURCE_IDENTITY_DIGEST,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSourceIdentityDigestService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_ACCEPTANCE_PRECHECK_SIGNATURE_EVIDENCE_VALUE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSignatureEvidenceValueService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_ACCEPTANCE_PRECHECK_POLICY_EXECUTION_ARCHIVE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckPolicyExecutionArchiveService
                    .ENDPOINT))
        .allSatisfy(
            (routePath, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessService.BASE_PATH + routePath));
  }
}
