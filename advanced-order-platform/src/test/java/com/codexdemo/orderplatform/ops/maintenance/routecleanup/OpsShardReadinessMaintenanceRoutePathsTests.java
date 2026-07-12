package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMaintenanceRoutePathsTests {

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
}
