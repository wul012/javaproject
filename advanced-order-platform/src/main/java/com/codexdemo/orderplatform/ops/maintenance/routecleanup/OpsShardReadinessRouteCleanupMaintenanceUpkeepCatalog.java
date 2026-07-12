package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public final class OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog {

  private OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog() {}

  public static List<Item> items() {
    return OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogSeeds.items();
  }

  public static int firstServiceVersion() {
    return items().getFirst().serviceVersion();
  }

  public static int latestRouteVersion() {
    return items().getLast().routeVersion();
  }

  public record Item(
      String name,
      int serviceVersion,
      int routeVersion,
      String endpoint,
      String evidencePath,
      String consumer,
      String boundary,
      String status) {}
}
