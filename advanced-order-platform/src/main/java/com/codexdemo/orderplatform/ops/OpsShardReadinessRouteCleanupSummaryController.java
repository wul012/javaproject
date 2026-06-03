package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupSummaryController {

    private final OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService;

    private final OpsShardReadinessRouteCleanupDigestService digestService;

    private final OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService;

    public OpsShardReadinessRouteCleanupSummaryController(
            OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService,
            OpsShardReadinessRouteCleanupDigestService digestService,
            OpsShardReadinessRouteCleanupSourcePlanAlignmentService sourcePlanAlignmentService
    ) {
        this.phaseSummaryService = phaseSummaryService;
        this.digestService = digestService;
        this.sourcePlanAlignmentService = sourcePlanAlignmentService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_PHASE_SUMMARY)
    public OpsShardReadinessRouteCleanupPhaseSummaryResponse phaseSummary() {
        return phaseSummaryService.summary();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_DIGEST)
    public OpsShardReadinessRouteCleanupDigestResponse digest() {
        return digestService.digest();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_SOURCE_PLAN_ALIGNMENT)
    public OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse sourcePlanAlignment() {
        return sourcePlanAlignmentService.alignment();
    }
}
