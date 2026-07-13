package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRoutePathsTests {

  @Test
  void signedApprovalArtifactDraftPreflightEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_DIGEST_CHAIN,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_WINDOW,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_STATEMENT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_EVIDENCE_SOURCE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_REDACTION_PROVENANCE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_FAIL_CLOSED_LOCKS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_ARCHIVE_PLAN,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightArchivePlanService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint)
                    .isEqualTo(OpsShardReadinessService.BASE_PATH + route)
                    .contains("signed-approval-artifact-draft-preflight"));
  }

  @Test
  void signedApprovalArtifactDraftPreflightRoutesDelegateToSplitOwner() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CATALOG,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CATALOG),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_DIGEST_CHAIN,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_DIGEST_CHAIN),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_WINDOW,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_WINDOW),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_STATEMENT,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_STATEMENT),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_EVIDENCE_SOURCE,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_EVIDENCE_SOURCE),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_REDACTION_PROVENANCE,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_REDACTION_PROVENANCE),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_FAIL_CLOSED_LOCKS,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_FAIL_CLOSED_LOCKS),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_ARCHIVE_PLAN,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_ARCHIVE_PLAN),
                Map.entry(
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CLOSEOUT,
                    OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CLOSEOUT)))
        .allSatisfy((rootRoute, splitRoute) -> assertThat(rootRoute).isEqualTo(splitRoute));
  }
}
