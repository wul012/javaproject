package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupSuiteCloseoutService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_SUITE_CLOSEOUT;

    static final String PROFILE = "java-shard-readiness-route-cleanup-suite-closeout.v1";

    private static final int FIRST_SUITE_VERSION = 326;

    private final OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService;

    private final OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService;

    private final OpsShardReadinessRouteCleanupDigestService digestService;

    public OpsShardReadinessRouteCleanupSuiteCloseoutService(
            OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService,
            OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService,
            OpsShardReadinessRouteCleanupDigestService digestService
    ) {
        this.releaseHandoffService = releaseHandoffService;
        this.readOnlyGateService = readOnlyGateService;
        this.digestService = digestService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupSuiteCloseoutResponse closeout() {
        OpsShardReadinessRouteCleanupReleaseHandoffResponse releaseHandoff = releaseHandoffService.handoff();
        OpsShardReadinessRouteCleanupReadOnlyGateResponse readOnlyGate = readOnlyGateService.gate();
        OpsShardReadinessRouteCleanupDigestResponse digest = digestService.digest();
        List<String> endpoints = publishedEndpoints();
        int latestVersion = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();
        boolean passed = releaseHandoff.status().equals("passed")
                && readOnlyGate.status().equals("passed")
                && digest.status().equals("passed")
                && OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus().equals("passed");
        return new OpsShardReadinessRouteCleanupSuiteCloseoutResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                FIRST_SUITE_VERSION,
                latestVersion,
                latestVersion - FIRST_SUITE_VERSION + 1,
                endpoints.size(),
                endpoints,
                OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT,
                OpsShardReadinessRouteCleanupReadOnlyGateService.ENDPOINT,
                digest.digestValue(),
                passed ? "closeout-ready-for-read-only-consumer-handoff" : "blocked",
                passed ? "passed" : "blocked"
        );
    }

    private List<String> publishedEndpoints() {
        return List.of(
                OpsShardReadinessRouteCleanupEvidenceService.ENDPOINT,
                OpsShardReadinessRouteCleanupPhaseSummaryService.ENDPOINT,
                OpsShardReadinessRouteCleanupBoundaryMatrixService.ENDPOINT,
                OpsShardReadinessRouteCleanupHandoffChecklistService.ENDPOINT,
                OpsShardReadinessRouteCleanupArchivePlanService.ENDPOINT,
                OpsShardReadinessRouteCleanupDigestService.ENDPOINT,
                OpsShardReadinessRouteCleanupSourcePlanAlignmentService.ENDPOINT,
                OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT,
                OpsShardReadinessRouteCleanupOperatorRunbookService.ENDPOINT,
                OpsShardReadinessRouteCleanupReadOnlyGateService.ENDPOINT,
                ENDPOINT
        );
    }
}
