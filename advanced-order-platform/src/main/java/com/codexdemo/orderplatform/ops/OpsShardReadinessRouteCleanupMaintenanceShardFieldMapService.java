package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceVersionLineageService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SHARD_FIELD_MAP;
  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-shard-field-map.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse fieldMap() {
    return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
        "Java v541",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "project-version",
                "catalog-maintainer",
                "advanced-order-platform Java version labels",
                OpsShardReadinessRouteCleanupMaintenanceVersionLineageService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "read-only-boundary",
                "runtime-boundary-reviewer",
                "readOnly=true executionAllowed=false",
                OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService.ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "shard-shape",
                "release-reviewer",
                "shardEnabled shardCount slotCount routingMode",
                OpsShardReadinessPrototypeEvidenceService.FIELD_ALIGNMENT_ENDPOINT),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                "evidence-path",
                "archive-reviewer",
                "versioned evidencePath required by shard-readiness.v1",
                OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService.ENDPOINT)),
        List.of(
            "shard-readiness-v1-minimal-fields-mapped",
            "field-map-does-not-enable-active-routing",
            "field-map-does-not-change-order-write-path"));
  }
}
