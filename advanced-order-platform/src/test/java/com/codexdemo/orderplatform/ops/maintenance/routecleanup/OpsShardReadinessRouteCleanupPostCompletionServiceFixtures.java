package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

public final class OpsShardReadinessRouteCleanupPostCompletionServiceFixtures {

  private OpsShardReadinessRouteCleanupPostCompletionServiceFixtures() {}

  static OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService() {
    return new OpsShardReadinessRouteCleanupPostPushCloseoutService(
        OpsShardReadinessRouteCleanupServiceFixtures.completionCertificateService(),
        OpsShardReadinessRouteCleanupServiceFixtures.ciEvidenceService());
  }

  static OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService() {
    return new OpsShardReadinessRouteCleanupCiRunAttestationService(
        postPushCloseoutService(),
        OpsShardReadinessRouteCleanupServiceFixtures.ciEvidenceService());
  }

  static OpsShardReadinessRouteCleanupTagManifestService tagManifestService() {
    return new OpsShardReadinessRouteCleanupTagManifestService(ciRunAttestationService());
  }

  static OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService() {
    return new OpsShardReadinessRouteCleanupReleaseEvidenceBundleService(
        OpsShardReadinessRouteCleanupServiceFixtures.completionCertificateService(),
        ciRunAttestationService(),
        tagManifestService());
  }

  static OpsShardReadinessRouteCleanupConsumerSignoffPacketService consumerSignoffPacketService() {
    return new OpsShardReadinessRouteCleanupConsumerSignoffPacketService(
        releaseEvidenceBundleService(),
        OpsShardReadinessRouteCleanupServiceFixtures.policyGuardService(),
        OpsShardReadinessRouteCleanupServiceFixtures.acceptanceReceiptService());
  }

  static OpsShardReadinessRouteCleanupArchiveHandoffReceiptService archiveHandoffReceiptService() {
    return new OpsShardReadinessRouteCleanupArchiveHandoffReceiptService(
        OpsShardReadinessRouteCleanupServiceFixtures.finalArchivePlanService(),
        consumerSignoffPacketService(),
        postPushCloseoutService());
  }

  static OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService
      maintenanceBoundaryReportService() {
    return new OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService(
        archiveHandoffReceiptService(),
        OpsShardReadinessRouteCleanupServiceFixtures.policyGuardService());
  }

  static OpsShardReadinessRouteCleanupFixtureCoverageIndexService fixtureCoverageIndexService() {
    return new OpsShardReadinessRouteCleanupFixtureCoverageIndexService(
        maintenanceBoundaryReportService(), releaseEvidenceBundleService());
  }

  static OpsShardReadinessRouteCleanupCompletionAuditDigestService completionAuditDigestService() {
    return new OpsShardReadinessRouteCleanupCompletionAuditDigestService(
        fixtureCoverageIndexService(), tagManifestService(), archiveHandoffReceiptService());
  }

  public static OpsShardReadinessRouteCleanupPostCompletionCloseoutService
      postCompletionCloseoutService() {
    return new OpsShardReadinessRouteCleanupPostCompletionCloseoutService(
        completionAuditDigestService(),
        maintenanceBoundaryReportService(),
        archiveHandoffReceiptService(),
        ciRunAttestationService());
  }
}
