package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupEvidenceController {

    private final OpsShardReadinessRouteCleanupEvidenceService routeCleanupEvidenceService;

    public OpsShardReadinessRouteCleanupEvidenceController(
            OpsShardReadinessRouteCleanupEvidenceService routeCleanupEvidenceService
    ) {
        this.routeCleanupEvidenceService = routeCleanupEvidenceService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EVIDENCE_CATALOG)
    public OpsShardReadinessRouteCleanupEvidenceResponse routeCleanupEvidenceCatalog() {
        return routeCleanupEvidenceService.catalog();
    }
}
