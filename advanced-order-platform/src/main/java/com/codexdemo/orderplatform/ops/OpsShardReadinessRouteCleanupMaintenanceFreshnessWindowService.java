package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_FRESHNESS_WINDOW;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-freshness-window.v1";
  private static final int MAX_VERSION_LAG = 20;

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse window() {
    int latestRouteVersion =
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.latestRouteVersion();
    List<OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse.FreshnessEntry> entries =
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().stream()
            .map(item -> entry(item, latestRouteVersion))
            .toList();
    int stale = (int) entries.stream().filter(entry -> !entry.withinWindow()).count();
    List<String> checks =
        List.of(
            "freshness-entry-count-" + entries.size(),
            "max-version-lag-" + MAX_VERSION_LAG,
            "stale-evidence-count-" + stale,
            "freshness-calculated-from-versioned-catalog",
            "freshness-window-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse(
        "advanced-order-platform",
        "Java v516",
        true,
        false,
        ENDPOINT,
        PROFILE,
        entries.size(),
        MAX_VERSION_LAG,
        stale,
        entries,
        checks,
        stale == 0 ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse.FreshnessEntry entry(
      OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item, int latestRouteVersion) {
    int lag = latestRouteVersion - item.routeVersion();
    boolean withinWindow = lag <= MAX_VERSION_LAG;
    return new OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse.FreshnessEntry(
        item.name(),
        item.evidencePath(),
        item.routeVersion(),
        lag,
        withinWindow,
        withinWindow ? "passed" : "stale");
  }
}
