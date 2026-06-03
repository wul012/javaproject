package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupContinuityReportService {

    static final String PROFILE = "java-shard-readiness-route-cleanup-continuity-report.v1";

    private static final int FIRST_VERSION = 326;

    private final OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService;

    private final OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService;

    public OpsShardReadinessRouteCleanupContinuityReportService(
            OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService,
            OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService
    ) {
        this.endpointManifestService = endpointManifestService;
        this.phaseSummaryService = phaseSummaryService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupContinuityReportResponse report() {
        int latest = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();
        boolean versionsContinuous = OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous();
        boolean boundaryHeld = OpsShardReadinessRouteCleanupEvidenceAnalyzer.allEntriesKeepReadOnlyBoundary();
        return new OpsShardReadinessRouteCleanupContinuityReportResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                PROFILE,
                FIRST_VERSION,
                latest,
                latest - FIRST_VERSION + 1,
                endpointManifestService.manifest().endpointCount(),
                phaseSummaryService.summary().phaseCount(),
                versionsContinuous,
                boundaryHeld,
                versionsContinuous && boundaryHeld ? "passed" : "blocked"
        );
    }
}
