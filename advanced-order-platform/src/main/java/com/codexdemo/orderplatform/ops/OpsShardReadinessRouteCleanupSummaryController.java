package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupSummaryController {

    private final OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService;

    public OpsShardReadinessRouteCleanupSummaryController(
            OpsShardReadinessRouteCleanupPhaseSummaryService phaseSummaryService
    ) {
        this.phaseSummaryService = phaseSummaryService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_PHASE_SUMMARY)
    public OpsShardReadinessRouteCleanupPhaseSummaryResponse phaseSummary() {
        return phaseSummaryService.summary();
    }
}
