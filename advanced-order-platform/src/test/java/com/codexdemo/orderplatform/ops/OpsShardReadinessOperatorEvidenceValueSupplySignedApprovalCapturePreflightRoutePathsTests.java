package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCapturePreflightRoutePaths;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRoutePathsTests {

  @Test
  void signedApprovalCapturePreflightEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TEMPLATE_DIGEST,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REVIEW_DIGEST,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_OPERATOR_INPUT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TIMING_WINDOW,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CHANNEL_SIGNATURE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_STATEMENT_JUSTIFICATION,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_SOURCE_EVIDENCE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REDACTION_PROVENANCE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_FAIL_CLOSED_LOCKS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint)
                    .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route)
                    .contains("signed-approval-capture-preflight"));
  }

  @Test
  void signedApprovalCapturePreflightRoutesDelegateToSplitOwner() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CATALOG,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CATALOG),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TEMPLATE_DIGEST,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TEMPLATE_DIGEST),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REVIEW_DIGEST,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REVIEW_DIGEST),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_OPERATOR_INPUT,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_OPERATOR_INPUT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TIMING_WINDOW,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TIMING_WINDOW),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CHANNEL_SIGNATURE,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CHANNEL_SIGNATURE),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_STATEMENT_JUSTIFICATION,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_STATEMENT_JUSTIFICATION),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_SOURCE_EVIDENCE,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_SOURCE_EVIDENCE),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REDACTION_PROVENANCE,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REDACTION_PROVENANCE),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_FAIL_CLOSED_LOCKS,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_FAIL_CLOSED_LOCKS),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CLOSEOUT)))
        .allSatisfy((legacy, split) -> assertThat(legacy).isEqualTo(split));
  }
}
