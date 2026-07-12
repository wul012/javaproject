package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

public final class RouteCleanupRoutes {

  public static final String BASE_PATH = "/api/v1/ops/shard-readiness";
  public static final String EVIDENCE_CATALOG = "/route-cleanup-evidence-catalog";
  public static final String PHASE_SUMMARY = "/route-cleanup-phase-summary";
  public static final String BOUNDARY_MATRIX = "/route-cleanup-boundary-matrix";
  public static final String HANDOFF_CHECKLIST = "/route-cleanup-handoff-checklist";
  public static final String ARCHIVE_PLAN = "/route-cleanup-archive-plan";
  public static final String DIGEST = "/route-cleanup-digest";
  public static final String SOURCE_PLAN_ALIGNMENT = "/route-cleanup-source-plan-alignment";
  public static final String RELEASE_HANDOFF = "/route-cleanup-release-handoff";
  public static final String OPERATOR_RUNBOOK = "/route-cleanup-operator-runbook";
  public static final String READ_ONLY_GATE = "/route-cleanup-read-only-gate";
  public static final String MAINTENANCE_SEGMENT_CATALOG =
      "/route-cleanup-maintenance-segment-catalog";
  public static final String MAINTENANCE_CONTINUITY = "/route-cleanup-maintenance-continuity";
  public static final String MAINTENANCE_LATEST_SIBLING_REPORT =
      "/route-cleanup-maintenance-latest-sibling-report";
  public static final String MAINTENANCE_HANDOFF_PAIR_AUDIT =
      "/route-cleanup-maintenance-handoff-pair-audit";
  public static final String MAINTENANCE_BOUNDARY_DRIFT =
      "/route-cleanup-maintenance-boundary-drift";
  public static final String MAINTENANCE_SOURCE_PLAN_ALIGNMENT =
      "/route-cleanup-maintenance-source-plan-alignment";
  public static final String MAINTENANCE_TEST_BUDGET_PLAN =
      "/route-cleanup-maintenance-test-budget-plan";
  public static final String MAINTENANCE_ARCHIVE_MANIFEST =
      "/route-cleanup-maintenance-archive-manifest";
  public static final String MAINTENANCE_CLOSEOUT = "/route-cleanup-maintenance-closeout";
  public static final String MAINTENANCE_UPKEEP_CATALOG =
      "/route-cleanup-maintenance-upkeep-catalog";
  public static final String MAINTENANCE_CONSUMER_HANDOFF_MATRIX =
      "/route-cleanup-maintenance-consumer-handoff-matrix";
  public static final String MAINTENANCE_CI_EXPECTATION_MANIFEST =
      "/route-cleanup-maintenance-ci-expectation-manifest";
  public static final String MAINTENANCE_ROUTE_TOPOLOGY_INDEX =
      "/route-cleanup-maintenance-route-topology-index";
  public static final String MAINTENANCE_FAIL_CLOSED_POLICY =
      "/route-cleanup-maintenance-fail-closed-policy";

  private RouteCleanupRoutes() {}
}
