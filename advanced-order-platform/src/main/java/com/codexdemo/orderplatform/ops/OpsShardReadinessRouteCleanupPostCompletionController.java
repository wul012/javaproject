package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupArchiveHandoffReceiptService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupCiRunAttestationResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupCiRunAttestationService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupCompletionAuditDigestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupCompletionAuditDigestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupConsumerSignoffPacketService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFixtureCoverageIndexService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupPostCompletionCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupPostPushCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupPostPushCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupReleaseEvidenceBundleService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupTagManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupTagManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupPostCompletionController {

  private final OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService;

  private final OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService;

  private final OpsShardReadinessRouteCleanupTagManifestService tagManifestService;

  private final OpsShardReadinessRouteCleanupReleaseEvidenceBundleService
      releaseEvidenceBundleService;

  private final OpsShardReadinessRouteCleanupConsumerSignoffPacketService
      consumerSignoffPacketService;

  private final OpsShardReadinessRouteCleanupArchiveHandoffReceiptService
      archiveHandoffReceiptService;

  private final OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService
      maintenanceBoundaryReportService;

  private final OpsShardReadinessRouteCleanupFixtureCoverageIndexService
      fixtureCoverageIndexService;

  private final OpsShardReadinessRouteCleanupCompletionAuditDigestService
      completionAuditDigestService;

  private final OpsShardReadinessRouteCleanupPostCompletionCloseoutService
      postCompletionCloseoutService;

  public OpsShardReadinessRouteCleanupPostCompletionController(
      OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService,
      OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService,
      OpsShardReadinessRouteCleanupTagManifestService tagManifestService,
      OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService,
      OpsShardReadinessRouteCleanupConsumerSignoffPacketService consumerSignoffPacketService,
      OpsShardReadinessRouteCleanupArchiveHandoffReceiptService archiveHandoffReceiptService,
      OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService
          maintenanceBoundaryReportService,
      OpsShardReadinessRouteCleanupFixtureCoverageIndexService fixtureCoverageIndexService,
      OpsShardReadinessRouteCleanupCompletionAuditDigestService completionAuditDigestService,
      OpsShardReadinessRouteCleanupPostCompletionCloseoutService postCompletionCloseoutService) {
    this.postPushCloseoutService = postPushCloseoutService;
    this.ciRunAttestationService = ciRunAttestationService;
    this.tagManifestService = tagManifestService;
    this.releaseEvidenceBundleService = releaseEvidenceBundleService;
    this.consumerSignoffPacketService = consumerSignoffPacketService;
    this.archiveHandoffReceiptService = archiveHandoffReceiptService;
    this.maintenanceBoundaryReportService = maintenanceBoundaryReportService;
    this.fixtureCoverageIndexService = fixtureCoverageIndexService;
    this.completionAuditDigestService = completionAuditDigestService;
    this.postCompletionCloseoutService = postCompletionCloseoutService;
  }

  @GetMapping(RouteCleanupRoutes.POST_PUSH_CLOSEOUT)
  public OpsShardReadinessRouteCleanupPostPushCloseoutResponse postPushCloseout() {
    return postPushCloseoutService.closeout();
  }

  @GetMapping(RouteCleanupRoutes.CI_RUN_ATTESTATION)
  public OpsShardReadinessRouteCleanupCiRunAttestationResponse ciRunAttestation() {
    return ciRunAttestationService.attestation();
  }

  @GetMapping(RouteCleanupRoutes.TAG_MANIFEST)
  public OpsShardReadinessRouteCleanupTagManifestResponse tagManifest() {
    return tagManifestService.manifest();
  }

  @GetMapping(RouteCleanupRoutes.RELEASE_EVIDENCE_BUNDLE)
  public OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse releaseEvidenceBundle() {
    return releaseEvidenceBundleService.bundle();
  }

  @GetMapping(RouteCleanupRoutes.CONSUMER_SIGNOFF_PACKET)
  public OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse consumerSignoffPacket() {
    return consumerSignoffPacketService.packet();
  }

  @GetMapping(RouteCleanupRoutes.ARCHIVE_HANDOFF_RECEIPT)
  public OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse archiveHandoffReceipt() {
    return archiveHandoffReceiptService.receipt();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_BOUNDARY_REPORT)
  public OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse
      maintenanceBoundaryReport() {
    return maintenanceBoundaryReportService.report();
  }

  @GetMapping(RouteCleanupRoutes.FIXTURE_COVERAGE_INDEX)
  public OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse fixtureCoverageIndex() {
    return fixtureCoverageIndexService.index();
  }

  @GetMapping(RouteCleanupRoutes.COMPLETION_AUDIT_DIGEST)
  public OpsShardReadinessRouteCleanupCompletionAuditDigestResponse completionAuditDigest() {
    return completionAuditDigestService.digest();
  }

  @GetMapping(RouteCleanupRoutes.POST_COMPLETION_CLOSEOUT)
  public OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse postCompletionCloseout() {
    return postCompletionCloseoutService.closeout();
  }
}
