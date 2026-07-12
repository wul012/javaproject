package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_DEPENDENCY_BOUNDARY_MAP;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-dependency-boundary-map.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse map() {
    List<OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse.BoundaryEntry>
        boundaries =
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().stream()
                .map(this::entry)
                .toList();
    List<String> forbiddenOperations =
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations();
    List<String> checks =
        List.of(
            "boundary-entry-count-" + boundaries.size(),
            "forbidden-operation-count-" + forbiddenOperations.size(),
            "all-boundaries-have-owners",
            "all-boundaries-map-to-source-endpoints",
            "dependency-boundary-map-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse(
        "advanced-order-platform",
        "Java v524",
        true,
        false,
        ENDPOINT,
        PROFILE,
        boundaries.size(),
        forbiddenOperations.size(),
        boundaries,
        forbiddenOperations,
        checks,
        status(boundaries, forbiddenOperations));
  }

  private OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse.BoundaryEntry entry(
      OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item) {
    return new OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse.BoundaryEntry(
        item.name(),
        item.consumer(),
        item.boundary(),
        item.endpoint(),
        "read-only-evidence-preview",
        item.status());
  }

  private String status(
      List<OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse.BoundaryEntry>
          boundaries,
      List<String> forbiddenOperations) {
    boolean passed =
        boundaries.size() == OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().size()
            && boundaries.stream().allMatch(boundary -> !boundary.owner().isBlank())
            && boundaries.stream().allMatch(boundary -> !boundary.sourceEndpoint().isBlank())
            && boundaries.stream()
                .allMatch(boundary -> "read-only-evidence-preview".equals(boundary.allowedScope()))
            && forbiddenOperations.contains("write-routing")
            && forbiddenOperations.contains("managed-audit-connection")
            && boundaries.stream().allMatch(boundary -> "passed".equals(boundary.status()));
    return passed ? "passed" : "blocked";
  }
}
