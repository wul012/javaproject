package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceUpkeepController {

  private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService;

  private final OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService
      consumerHandoffMatrixService;

  private final OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService
      ciExpectationManifestService;

  private final OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService
      routeTopologyIndexService;

  private final OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService
      failClosedPolicyService;

  public OpsShardReadinessRouteCleanupMaintenanceUpkeepController(
      OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService,
      OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService
          consumerHandoffMatrixService,
      OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService
          ciExpectationManifestService,
      OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService routeTopologyIndexService,
      OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService failClosedPolicyService) {
    this.upkeepCatalogService = upkeepCatalogService;
    this.consumerHandoffMatrixService = consumerHandoffMatrixService;
    this.ciExpectationManifestService = ciExpectationManifestService;
    this.routeTopologyIndexService = routeTopologyIndexService;
    this.failClosedPolicyService = failClosedPolicyService;
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_UPKEEP_CATALOG)
  public OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse upkeepCatalog() {
    return upkeepCatalogService.catalog();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_CONSUMER_HANDOFF_MATRIX)
  public OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse
      consumerHandoffMatrix() {
    return consumerHandoffMatrixService.matrix();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_CI_EXPECTATION_MANIFEST)
  public OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse
      ciExpectationManifest() {
    return ciExpectationManifestService.manifest();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_ROUTE_TOPOLOGY_INDEX)
  public OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse routeTopologyIndex() {
    return routeTopologyIndexService.index();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_FAIL_CLOSED_POLICY)
  public OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse failClosedPolicy() {
    return failClosedPolicyService.report();
  }
}
