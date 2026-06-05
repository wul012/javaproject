package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController {

    private final OpsShardReadinessRouteCleanupMaintenanceContractFreezeService contractFreezeService;
    private final OpsShardReadinessRouteCleanupMaintenanceGateHandoffService gateHandoffService;
    private final OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService shardFieldMapService;
    private final OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService readWindowEvidenceService;

    public OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController(
            OpsShardReadinessRouteCleanupMaintenanceContractFreezeService contractFreezeService,
            OpsShardReadinessRouteCleanupMaintenanceGateHandoffService gateHandoffService,
            OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService shardFieldMapService,
            OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService readWindowEvidenceService
    ) {
        this.contractFreezeService = contractFreezeService;
        this.gateHandoffService = gateHandoffService;
        this.shardFieldMapService = shardFieldMapService;
        this.readWindowEvidenceService = readWindowEvidenceService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONTRACT_FREEZE)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse contractFreeze() {
        return contractFreezeService.freeze();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_GATE_HANDOFF)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse gateHandoff() {
        return gateHandoffService.handoff();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SHARD_FIELD_MAP)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse shardFieldMap() {
        return shardFieldMapService.fieldMap();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_READ_WINDOW_EVIDENCE)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse readWindowEvidence() {
        return readWindowEvidenceService.evidence();
    }
}
