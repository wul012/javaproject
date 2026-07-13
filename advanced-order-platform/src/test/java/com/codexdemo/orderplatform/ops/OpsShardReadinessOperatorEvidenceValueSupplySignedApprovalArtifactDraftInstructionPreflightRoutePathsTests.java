package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightOperatorInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightRoutePathsTests {

  @Test
  void signedApprovalArtifactDraftInstructionPreflightEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DIGEST_INSTRUCTIONS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_OPERATOR_INSTRUCTIONS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightOperatorInstructionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SIGNATURE_INSTRUCTIONS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EVIDENCE_INSTRUCTIONS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_VALUE_POLICY_INSTRUCTIONS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EMBARGO_INSTRUCTIONS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DRAFT_TEXT_LOCK,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessService.BASE_PATH + route));
  }

  @Test
  void signedApprovalArtifactDraftInstructionPreflightRoutesDelegateToSplitOwner() {
    assertThat(OpsShardReadinessService.BASE_PATH)
        .isEqualTo(
            OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths.BASE_PATH);
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CATALOG,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CATALOG),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DIGEST_INSTRUCTIONS,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DIGEST_INSTRUCTIONS),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_OPERATOR_INSTRUCTIONS,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_OPERATOR_INSTRUCTIONS),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SIGNATURE_INSTRUCTIONS,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_SIGNATURE_INSTRUCTIONS),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EVIDENCE_INSTRUCTIONS,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EVIDENCE_INSTRUCTIONS),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_VALUE_POLICY_INSTRUCTIONS,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_VALUE_POLICY_INSTRUCTIONS),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EMBARGO_INSTRUCTIONS,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EMBARGO_INSTRUCTIONS),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DRAFT_TEXT_LOCK,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DRAFT_TEXT_LOCK),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CLOSEOUT)))
        .allSatisfy((rootRoute, splitRoute) -> assertThat(rootRoute).isEqualTo(splitRoute));
  }
}
