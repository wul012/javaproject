package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceVersionLineageService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_VERSION_LINEAGE;
  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-version-lineage.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse lineage() {
    List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item> items =
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items();
    List<OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse.LineagePair> pairs =
        IntStream.range(0, items.size()).mapToObj(index -> pair(items, index)).toList();
    int gapCount = (int) pairs.stream().filter(pair -> !"passed".equals(pair.status())).count();
    List<String> checks =
        List.of(
            "lineage-pair-count-" + pairs.size(),
            "route-versions-follow-service-versions",
            "service-version-step-is-two",
            "route-version-step-is-two",
            "version-lineage-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse(
        "advanced-order-platform",
        "Java v503",
        true,
        false,
        ENDPOINT,
        PROFILE,
        pairs.size(),
        pairs.getFirst().serviceVersion(),
        pairs.getLast().serviceVersion(),
        pairs.getFirst().routeVersion(),
        pairs.getLast().routeVersion(),
        2,
        2,
        gapCount,
        pairs,
        checks,
        gapCount == 0 ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse.LineagePair pair(
      List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item> items, int index) {
    OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item = items.get(index);
    int nextServiceVersion = index == items.size() - 1 ? -1 : items.get(index + 1).serviceVersion();
    boolean routeFollowsService = item.routeVersion() == item.serviceVersion() + 1;
    boolean nextServiceVersionValid =
        nextServiceVersion == -1 || nextServiceVersion == item.serviceVersion() + 2;
    return new OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse.LineagePair(
        item.name(),
        item.serviceVersion(),
        item.routeVersion(),
        routeFollowsService,
        nextServiceVersion,
        routeFollowsService && nextServiceVersionValid ? "passed" : "blocked");
  }
}
