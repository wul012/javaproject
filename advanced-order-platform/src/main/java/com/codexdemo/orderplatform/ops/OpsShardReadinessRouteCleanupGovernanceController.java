package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupGovernanceController {

    private final OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService;

    public OpsShardReadinessRouteCleanupGovernanceController(
            OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService
    ) {
        this.boundaryMatrixService = boundaryMatrixService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_BOUNDARY_MATRIX)
    public OpsShardReadinessRouteCleanupBoundaryMatrixResponse boundaryMatrix() {
        return boundaryMatrixService.matrix();
    }
}
