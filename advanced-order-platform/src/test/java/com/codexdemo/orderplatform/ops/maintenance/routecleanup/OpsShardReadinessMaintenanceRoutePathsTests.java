package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMaintenanceRoutePathsTests {

  @Test
  void baseEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    RouteCleanupRoutes.EVIDENCE_CATALOG,
                    OpsShardReadinessRouteCleanupEvidenceService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.PHASE_SUMMARY,
                    OpsShardReadinessRouteCleanupPhaseSummaryService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.BOUNDARY_MATRIX,
                    OpsShardReadinessRouteCleanupBoundaryMatrixService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.HANDOFF_CHECKLIST,
                    OpsShardReadinessRouteCleanupHandoffChecklistService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.ARCHIVE_PLAN,
                    OpsShardReadinessRouteCleanupArchivePlanService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.DIGEST, OpsShardReadinessRouteCleanupDigestService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.SOURCE_PLAN_ALIGNMENT,
                    OpsShardReadinessRouteCleanupSourcePlanAlignmentService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.RELEASE_HANDOFF,
                    OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.OPERATOR_RUNBOOK,
                    OpsShardReadinessRouteCleanupOperatorRunbookService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.READ_ONLY_GATE,
                    OpsShardReadinessRouteCleanupReadOnlyGateService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(RouteCleanupRoutes.BASE_PATH + route));
  }

  @Test
  void handoffEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    RouteCleanupRoutes.SUITE_CLOSEOUT,
                    OpsShardReadinessRouteCleanupSuiteCloseoutService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.ARCHIVE_VERIFICATION,
                    OpsShardReadinessRouteCleanupArchiveVerificationService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.CONSUMER_PACKET,
                    OpsShardReadinessRouteCleanupConsumerPacketService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.CI_EVIDENCE,
                    OpsShardReadinessRouteCleanupCiEvidenceService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.ENDPOINT_MANIFEST,
                    OpsShardReadinessRouteCleanupEndpointManifestService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.REGRESSION_GUARD,
                    OpsShardReadinessRouteCleanupRegressionGuardService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.HANDOFF_BUNDLE,
                    OpsShardReadinessRouteCleanupHandoffBundleService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.CONTINUITY_REPORT,
                    OpsShardReadinessRouteCleanupContinuityReportService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.CONSUMER_CHECKLIST,
                    OpsShardReadinessRouteCleanupConsumerChecklistService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.FINAL_DIGEST,
                    OpsShardReadinessRouteCleanupFinalDigestService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.EXTENDED_CLOSEOUT,
                    OpsShardReadinessRouteCleanupExtendedCloseoutService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(RouteCleanupRoutes.BASE_PATH + route));
  }

  @Test
  void maintenanceUpkeepEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_CONTRACT_FREEZE,
                    OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_GATE_HANDOFF,
                    OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_SHARD_FIELD_MAP,
                    OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_READ_WINDOW_EVIDENCE,
                    OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_RUNTIME_BOUNDARY_CHECKLIST,
                    OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService
                        .ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_CONSUMER_GATE_PACKET,
                    OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_ARCHIVE_VERIFIER_SUMMARY,
                    OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_CI_BUDGET_LEDGER,
                    OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_ROUTE_INVENTORY_DIGEST,
                    OpsShardReadinessRouteCleanupMaintenanceRouteInventoryDigestService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_OPERATOR_SIGNOFF,
                    OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_EXTENDED_CLOSEOUT,
                    OpsShardReadinessRouteCleanupMaintenanceExtendedCloseoutService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(RouteCleanupRoutes.BASE_PATH + route));
  }

  @Test
  void postCompletionEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    RouteCleanupRoutes.AUDIT_TRAIL,
                    OpsShardReadinessRouteCleanupAuditTrailService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.ACCEPTANCE_RECEIPT,
                    OpsShardReadinessRouteCleanupAcceptanceReceiptService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.EVIDENCE_REGISTER,
                    OpsShardReadinessRouteCleanupEvidenceRegisterService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.OPERATIONAL_SNAPSHOT,
                    OpsShardReadinessRouteCleanupOperationalSnapshotService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.POLICY_GUARD,
                    OpsShardReadinessRouteCleanupPolicyGuardService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.REVIEWER_PACKET,
                    OpsShardReadinessRouteCleanupReviewerPacketService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.TRANSITION_BRIEF,
                    OpsShardReadinessRouteCleanupTransitionBriefService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.FINAL_VERIFICATION,
                    OpsShardReadinessRouteCleanupFinalVerificationService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.FINAL_ARCHIVE_PLAN,
                    OpsShardReadinessRouteCleanupFinalArchivePlanService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.THIRD_RUN_CLOSEOUT,
                    OpsShardReadinessRouteCleanupThirdRunCloseoutService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.COMPLETION_INDEX,
                    OpsShardReadinessRouteCleanupCompletionIndexService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.COMPLETION_CERTIFICATE,
                    OpsShardReadinessRouteCleanupCompletionCertificateService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.POST_PUSH_CLOSEOUT,
                    OpsShardReadinessRouteCleanupPostPushCloseoutService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.CI_RUN_ATTESTATION,
                    OpsShardReadinessRouteCleanupCiRunAttestationService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.TAG_MANIFEST,
                    OpsShardReadinessRouteCleanupTagManifestService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.RELEASE_EVIDENCE_BUNDLE,
                    OpsShardReadinessRouteCleanupReleaseEvidenceBundleService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.CONSUMER_SIGNOFF_PACKET,
                    OpsShardReadinessRouteCleanupConsumerSignoffPacketService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.ARCHIVE_HANDOFF_RECEIPT,
                    OpsShardReadinessRouteCleanupArchiveHandoffReceiptService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.MAINTENANCE_BOUNDARY_REPORT,
                    OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.FIXTURE_COVERAGE_INDEX,
                    OpsShardReadinessRouteCleanupFixtureCoverageIndexService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.COMPLETION_AUDIT_DIGEST,
                    OpsShardReadinessRouteCleanupCompletionAuditDigestService.ENDPOINT),
                Map.entry(
                    RouteCleanupRoutes.POST_COMPLETION_CLOSEOUT,
                    OpsShardReadinessRouteCleanupPostCompletionCloseoutService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(RouteCleanupRoutes.BASE_PATH + route));
  }
}
