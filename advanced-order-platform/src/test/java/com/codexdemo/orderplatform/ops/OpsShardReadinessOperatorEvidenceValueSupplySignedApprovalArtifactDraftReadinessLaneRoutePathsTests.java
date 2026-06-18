package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneDigestPinService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneManualPackageGateService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneOperatorReviewService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSignatureReviewService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneRoutePathsTests {

  @Test
  void signedApprovalArtifactDraftReadinessLaneEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalogService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_DIGEST_PINS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneDigestPinService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_OPERATOR_REVIEW,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneOperatorReviewService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_SIGNATURE_REVIEW,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSignatureReviewService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EVIDENCE_REVIEW,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_VALUE_REDACTION,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EMBARGO_LOCKS,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_MANUAL_PACKAGE_GATE,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneManualPackageGateService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService
                        .ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint)
                    .isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route)
                    .contains("signed-approval-artifact-draft-readiness-lane"));
  }

  @Test
  void signedApprovalArtifactDraftReadinessLaneRoutesDelegateToSplitOwner() {
    assertThat(OpsShardReadinessRoutePaths.BASE_PATH)
        .isEqualTo(OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths.BASE_PATH);
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_CATALOG,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_CATALOG),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_DIGEST_PINS,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_DIGEST_PINS),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_OPERATOR_REVIEW,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_OPERATOR_REVIEW),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_SIGNATURE_REVIEW,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_SIGNATURE_REVIEW),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EVIDENCE_REVIEW,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EVIDENCE_REVIEW),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_VALUE_REDACTION,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_VALUE_REDACTION),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EMBARGO_LOCKS,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EMBARGO_LOCKS),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_MANUAL_PACKAGE_GATE,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_MANUAL_PACKAGE_GATE),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_CLOSEOUT,
                    OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_CLOSEOUT)))
        .allSatisfy((rootRoute, splitRoute) -> assertThat(rootRoute).isEqualTo(splitRoute));
  }
}
