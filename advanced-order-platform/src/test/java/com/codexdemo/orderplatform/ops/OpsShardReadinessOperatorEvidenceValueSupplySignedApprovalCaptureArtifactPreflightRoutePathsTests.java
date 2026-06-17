package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRoutePathsTests {

  @Test
  void signedApprovalCaptureArtifactPreflightEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_DIGEST,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_TEMPLATE_REVIEW,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_FRAGMENT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_POLICY,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_SEAL,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_STATEMENT_EVIDENCE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTION_VALUE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_FAIL_CLOSED_LOCKS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_ARCHIVE_PLAN,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint)
                    .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route)
                    .contains("signed-approval-capture-artifact-preflight"));
  }

  @Test
  void signedApprovalCaptureArtifactPreflightRoutesDelegateToSplitOwner() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CATALOG,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CATALOG),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_DIGEST,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_DIGEST),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_TEMPLATE_REVIEW,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_TEMPLATE_REVIEW),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_FRAGMENT,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_FRAGMENT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_POLICY,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_POLICY),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_SEAL,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_SEAL),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_STATEMENT_EVIDENCE,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_STATEMENT_EVIDENCE),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTION_VALUE,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTION_VALUE),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_FAIL_CLOSED_LOCKS,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_FAIL_CLOSED_LOCKS),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_ARCHIVE_PLAN,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_ARCHIVE_PLAN),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CLOSEOUT)))
        .allSatisfy((legacy, split) -> assertThat(legacy).isEqualTo(split));
  }
}
