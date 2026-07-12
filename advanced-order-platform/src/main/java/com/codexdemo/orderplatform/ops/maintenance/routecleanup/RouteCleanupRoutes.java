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

  private RouteCleanupRoutes() {}
}
