package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRoutePathsTests {

    @Test
    void signedApprovalCaptureArtifactPreflightEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CATALOG,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_DIGEST,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_TEMPLATE_REVIEW,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_FRAGMENT,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_POLICY,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_SEAL,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_STATEMENT_EVIDENCE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTION_VALUE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_FAIL_CLOSED_LOCKS,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_ARCHIVE_PLAN,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CLOSEOUT,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService
                                .ENDPOINT
                )
        )).allSatisfy((route, endpoint) -> assertThat(endpoint)
                .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route)
                .contains("signed-approval-capture-artifact-preflight"));
    }
}
