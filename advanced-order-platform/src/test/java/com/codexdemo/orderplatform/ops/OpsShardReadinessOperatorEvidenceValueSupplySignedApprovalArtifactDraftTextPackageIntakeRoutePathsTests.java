package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeRoutePathsTests {

    @Test
    void draftTextPackageIntakeEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_CATALOG,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_IDENTITY_CORRELATION,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_DIGEST_BINDING,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeDigestBindingService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ENVELOPE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSignatureEnvelopeService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_EVIDENCE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_VALUE_HANDLE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeOperatorValueHandleService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_POLICY_REVIEW_STATE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_EXECUTION_LOCK,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_ARCHIVE_CLOSEOUT,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService
                                .ENDPOINT
                )
        )).allSatisfy((routePath, endpoint) -> assertThat(endpoint)
                .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + routePath));
    }
}
