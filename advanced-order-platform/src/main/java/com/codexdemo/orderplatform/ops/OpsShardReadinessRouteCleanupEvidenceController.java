package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupEvidenceController {

  private final OpsShardReadinessRouteCleanupEvidenceService routeCleanupEvidenceService;

  public OpsShardReadinessRouteCleanupEvidenceController(
      OpsShardReadinessRouteCleanupEvidenceService routeCleanupEvidenceService) {
    this.routeCleanupEvidenceService = routeCleanupEvidenceService;
  }

  @GetMapping(RouteCleanupRoutes.EVIDENCE_CATALOG)
  public OpsShardReadinessRouteCleanupEvidenceResponse routeCleanupEvidenceCatalog() {
    return routeCleanupEvidenceService.catalog();
  }
}
