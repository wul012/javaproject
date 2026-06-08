package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightRoutePathsTests {

    @Test
    void signedApprovalArtifactDraftInstructionPreflightEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CATALOG,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DIGEST_INSTRUCTIONS,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_OPERATOR_INSTRUCTIONS,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightOperatorInstructionService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SIGNATURE_INSTRUCTIONS,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EVIDENCE_INSTRUCTIONS,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_VALUE_POLICY_INSTRUCTIONS,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EMBARGO_INSTRUCTIONS,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DRAFT_TEXT_LOCK,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService
                                .ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CLOSEOUT,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService
                                .ENDPOINT
                )
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }
}
