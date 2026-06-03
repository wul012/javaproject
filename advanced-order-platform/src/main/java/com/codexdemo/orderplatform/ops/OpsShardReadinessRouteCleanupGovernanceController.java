package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupGovernanceController {

    private final OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService;

    private final OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService;

    public OpsShardReadinessRouteCleanupGovernanceController(
            OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService,
            OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService
    ) {
        this.boundaryMatrixService = boundaryMatrixService;
        this.operatorRunbookService = operatorRunbookService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_BOUNDARY_MATRIX)
    public OpsShardReadinessRouteCleanupBoundaryMatrixResponse boundaryMatrix() {
        return boundaryMatrixService.matrix();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_OPERATOR_RUNBOOK)
    public OpsShardReadinessRouteCleanupOperatorRunbookResponse operatorRunbook() {
        return operatorRunbookService.runbook();
    }
}
