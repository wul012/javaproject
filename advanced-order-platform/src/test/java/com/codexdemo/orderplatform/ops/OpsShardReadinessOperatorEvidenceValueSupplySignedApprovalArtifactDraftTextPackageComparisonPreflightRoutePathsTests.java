package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightIdentityRequestService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightRoutePathsTests {

  @Test
  void comparisonPreflightEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.of(
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonPreflightRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_PREFLIGHT_CATALOG,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonPreflightRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_PREFLIGHT_IDENTITY_REQUEST,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightIdentityRequestService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonPreflightRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_PREFLIGHT_DIGEST_SIGNATURE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonPreflightRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_PREFLIGHT_EVIDENCE_VALUE_POLICY,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparisonPreflightRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARISON_PREFLIGHT_EXECUTION_CLOSEOUT,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService
                    .ENDPOINT))
        .allSatisfy(
            (routePath, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessService.BASE_PATH + routePath));
  }
}
