package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeAssuranceCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeIdentityDigestSignatureService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceAcceptanceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSubmissionComparisonService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePathsTests {

  @Test
  void comparedPackageEvidenceIntakeEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.of(
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_CATALOG,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_SOURCE_ACCEPTANCE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceAcceptanceService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_SUBMISSION_COMPARISON,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSubmissionComparisonService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_IDENTITY_DIGEST_SIGNATURE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeIdentityDigestSignatureService
                    .ENDPOINT,
                OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_ASSURANCE_CLOSEOUT,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeAssuranceCloseoutService
                    .ENDPOINT))
        .allSatisfy(
            (routePath, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessService.BASE_PATH + routePath));
  }
}
