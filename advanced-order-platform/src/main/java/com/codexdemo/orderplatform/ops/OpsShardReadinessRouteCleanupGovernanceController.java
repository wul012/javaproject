package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupGovernanceController {

    private final OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService;

    private final OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService;

    private final OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService;

    private final OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService;

    public OpsShardReadinessRouteCleanupGovernanceController(
            OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService,
            OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService,
            OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService,
            OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService
    ) {
        this.boundaryMatrixService = boundaryMatrixService;
        this.operatorRunbookService = operatorRunbookService;
        this.readOnlyGateService = readOnlyGateService;
        this.ciEvidenceService = ciEvidenceService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_BOUNDARY_MATRIX)
    public OpsShardReadinessRouteCleanupBoundaryMatrixResponse boundaryMatrix() {
        return boundaryMatrixService.matrix();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_OPERATOR_RUNBOOK)
    public OpsShardReadinessRouteCleanupOperatorRunbookResponse operatorRunbook() {
        return operatorRunbookService.runbook();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_READ_ONLY_GATE)
    public OpsShardReadinessRouteCleanupReadOnlyGateResponse readOnlyGate() {
        return readOnlyGateService.gate();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CI_EVIDENCE)
    public OpsShardReadinessRouteCleanupCiEvidenceResponse ciEvidence() {
        return ciEvidenceService.evidence();
    }
}
