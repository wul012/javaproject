package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness.*;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRoutePathsTests {

  @Test
  void signedApprovalArtifactDraftAuthoringReadinessEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_DIGEST_PINS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDigestPinService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_OPERATOR_REQUIREMENTS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessOperatorRequirementService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_SIGNATURE_REQUIREMENTS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSignatureRequirementService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_EVIDENCE_REQUIREMENTS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEvidenceRequirementService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_VALUE_POLICY_REQUIREMENTS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessValuePolicyRequirementService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_EMBARGO_REQUIREMENTS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEmbargoRequirementService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_DRAFT_TEXT_ABSENCE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDraftTextAbsenceService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
  }
}
