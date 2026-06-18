package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessEvidenceSourceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRoutePathsTests {

  @Test
  void signedApprovalArtifactDraftReadinessEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_DIGEST_CHAIN,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_OPERATOR_WINDOW,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_SIGNATURE_STATEMENT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_EVIDENCE_SOURCE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessEvidenceSourceService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_REDACTION_PROVENANCE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_FAIL_CLOSED_LOCKS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_ARCHIVE_PLAN,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessArchivePlanService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint)
                    .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route)
                    .contains("signed-approval-artifact-draft-readiness"));
  }

  @Test
  void signedApprovalArtifactDraftReadinessRoutesDelegateToSplitOwner() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CATALOG,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CATALOG),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_DIGEST_CHAIN,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_DIGEST_CHAIN),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_OPERATOR_WINDOW,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_OPERATOR_WINDOW),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_SIGNATURE_STATEMENT,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_SIGNATURE_STATEMENT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_EVIDENCE_SOURCE,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_EVIDENCE_SOURCE),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_REDACTION_PROVENANCE,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_REDACTION_PROVENANCE),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_FAIL_CLOSED_LOCKS,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_FAIL_CLOSED_LOCKS),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_ARCHIVE_PLAN,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_ARCHIVE_PLAN),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CLOSEOUT,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CLOSEOUT)))
        .allSatisfy((legacy, split) -> assertThat(legacy).isEqualTo(split));
  }
}
