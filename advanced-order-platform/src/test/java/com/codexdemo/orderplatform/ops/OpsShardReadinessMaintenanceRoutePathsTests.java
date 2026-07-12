package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMaintenanceRoutePathsTests {

  @Test
  void maintenanceUpkeepEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RELEASE_CHECKLIST,
                    OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_REMEDIATION_QUEUE,
                    OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_FRESHNESS_WINDOW,
                    OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_OWNERSHIP_REGISTER,
                    OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RISK_LEDGER,
                    OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_HANDOFF_ACCEPTANCE_DIGEST,
                    OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_DEPENDENCY_BOUNDARY_MAP,
                    OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_RETENTION_CALENDAR,
                    OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_TEST_EVIDENCE_ROLLUP,
                    OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_OPERATIONS_SCORECARD,
                    OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SUSTAINMENT_CLOSEOUT,
                    OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONTRACT_FREEZE,
                    OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_GATE_HANDOFF,
                    OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SHARD_FIELD_MAP,
                    OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_READ_WINDOW_EVIDENCE,
                    OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .ROUTE_CLEANUP_MAINTENANCE_RUNTIME_BOUNDARY_CHECKLIST,
                    OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONSUMER_GATE_PACKET,
                    OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_VERIFIER_SUMMARY,
                    OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CI_BUDGET_LEDGER,
                    OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ROUTE_INVENTORY_DIGEST,
                    OpsShardReadinessRouteCleanupMaintenanceRouteInventoryDigestService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_OPERATOR_SIGNOFF,
                    OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_EXTENDED_CLOSEOUT,
                    OpsShardReadinessRouteCleanupMaintenanceExtendedCloseoutService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
  }
}
