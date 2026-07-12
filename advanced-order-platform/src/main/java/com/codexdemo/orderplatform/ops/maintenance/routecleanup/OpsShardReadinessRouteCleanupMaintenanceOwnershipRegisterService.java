package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_OWNERSHIP_REGISTER;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-ownership-register.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse register() {
    List<OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse.OwnerEntry> owners =
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().stream()
            .map(this::owner)
            .toList();
    int distinctOwners =
        (int)
            owners.stream()
                .map(
                    OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse.OwnerEntry
                        ::owner)
                .distinct()
                .count();
    List<String> checks =
        List.of(
            "owner-entry-count-" + owners.size(),
            "distinct-owner-count-" + distinctOwners,
            "each-owner-has-boundary",
            "each-owner-has-source-endpoint",
            "ownership-register-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse(
        "advanced-order-platform",
        "Java v518",
        true,
        false,
        ENDPOINT,
        PROFILE,
        owners.size(),
        distinctOwners,
        owners,
        checks,
        status(owners));
  }

  private OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse.OwnerEntry owner(
      OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item) {
    return new OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse.OwnerEntry(
        item.name(), item.consumer(), item.boundary(), item.endpoint(), item.status());
  }

  private String status(
      List<OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse.OwnerEntry> owners) {
    boolean passed =
        owners.size() == 9
            && owners.stream().allMatch(owner -> !owner.owner().isBlank())
            && owners.stream().allMatch(owner -> !owner.boundary().isBlank())
            && owners.stream().allMatch(owner -> "passed".equals(owner.status()));
    return passed ? "passed" : "blocked";
  }
}
