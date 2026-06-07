package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRoutePathsTests {

    @Test
    void signedApprovalCapturePreflightEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CATALOG,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TEMPLATE_DIGEST,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REVIEW_DIGEST,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_OPERATOR_INPUT,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TIMING_WINDOW,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CHANNEL_SIGNATURE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_STATEMENT_JUSTIFICATION,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_SOURCE_EVIDENCE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REDACTION_PROVENANCE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_FAIL_CLOSED_LOCKS,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CLOSEOUT,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService
                                .ENDPOINT
                )
        )).allSatisfy((route, endpoint) -> assertThat(endpoint)
                .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route)
                .contains("signed-approval-capture-preflight"));
    }
}
