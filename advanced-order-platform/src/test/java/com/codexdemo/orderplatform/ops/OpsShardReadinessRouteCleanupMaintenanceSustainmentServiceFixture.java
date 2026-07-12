package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService;

final class OpsShardReadinessRouteCleanupMaintenanceSustainmentServiceFixture {

  private OpsShardReadinessRouteCleanupMaintenanceSustainmentServiceFixture() {}

  static OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService
      handoffDigestService() {
    return new OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService(
        new OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService(),
        new OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService(),
        new OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService());
  }

  static OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService
      archiveRetentionService() {
    return new OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService();
  }

  static OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService
      testEvidenceRollupService() {
    return new OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService();
  }

  static OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService
      operationsScorecardService() {
    return new OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService(
        handoffDigestService(),
        new OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService(),
        archiveRetentionService(),
        testEvidenceRollupService());
  }

  static OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService closeoutService() {
    return new OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService(
        operationsScorecardService(), archiveRetentionService(), testEvidenceRollupService());
  }
}
