package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupPostCompletionController {

    private final OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService;

    private final OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService;

    private final OpsShardReadinessRouteCleanupTagManifestService tagManifestService;

    private final OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService;

    private final OpsShardReadinessRouteCleanupConsumerSignoffPacketService consumerSignoffPacketService;

    private final OpsShardReadinessRouteCleanupArchiveHandoffReceiptService archiveHandoffReceiptService;

    private final OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService maintenanceBoundaryReportService;

    private final OpsShardReadinessRouteCleanupFixtureCoverageIndexService fixtureCoverageIndexService;

    public OpsShardReadinessRouteCleanupPostCompletionController(
            OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService,
            OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService,
            OpsShardReadinessRouteCleanupTagManifestService tagManifestService,
            OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService,
            OpsShardReadinessRouteCleanupConsumerSignoffPacketService consumerSignoffPacketService,
            OpsShardReadinessRouteCleanupArchiveHandoffReceiptService archiveHandoffReceiptService,
            OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService maintenanceBoundaryReportService,
            OpsShardReadinessRouteCleanupFixtureCoverageIndexService fixtureCoverageIndexService
    ) {
        this.postPushCloseoutService = postPushCloseoutService;
        this.ciRunAttestationService = ciRunAttestationService;
        this.tagManifestService = tagManifestService;
        this.releaseEvidenceBundleService = releaseEvidenceBundleService;
        this.consumerSignoffPacketService = consumerSignoffPacketService;
        this.archiveHandoffReceiptService = archiveHandoffReceiptService;
        this.maintenanceBoundaryReportService = maintenanceBoundaryReportService;
        this.fixtureCoverageIndexService = fixtureCoverageIndexService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POST_PUSH_CLOSEOUT)
    public OpsShardReadinessRouteCleanupPostPushCloseoutResponse postPushCloseout() {
        return postPushCloseoutService.closeout();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CI_RUN_ATTESTATION)
    public OpsShardReadinessRouteCleanupCiRunAttestationResponse ciRunAttestation() {
        return ciRunAttestationService.attestation();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_TAG_MANIFEST)
    public OpsShardReadinessRouteCleanupTagManifestResponse tagManifest() {
        return tagManifestService.manifest();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_RELEASE_EVIDENCE_BUNDLE)
    public OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse releaseEvidenceBundle() {
        return releaseEvidenceBundleService.bundle();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONSUMER_SIGNOFF_PACKET)
    public OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse consumerSignoffPacket() {
        return consumerSignoffPacketService.packet();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_HANDOFF_RECEIPT)
    public OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse archiveHandoffReceipt() {
        return archiveHandoffReceiptService.receipt();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_BOUNDARY_REPORT)
    public OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse maintenanceBoundaryReport() {
        return maintenanceBoundaryReportService.report();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FIXTURE_COVERAGE_INDEX)
    public OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse fixtureCoverageIndex() {
        return fixtureCoverageIndexService.index();
    }
}
