package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceUpkeepController {

    private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService;

    private final OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService consumerHandoffMatrixService;

    private final OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService ciExpectationManifestService;

    private final OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService routeTopologyIndexService;

    private final OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService failClosedPolicyService;

    private final OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService archiveDigestLedgerService;

    private final OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService operatorReviewPacketService;

    private final OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService;

    public OpsShardReadinessRouteCleanupMaintenanceUpkeepController(
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService,
            OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService consumerHandoffMatrixService,
            OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService ciExpectationManifestService,
            OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService routeTopologyIndexService,
            OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService failClosedPolicyService,
            OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService archiveDigestLedgerService,
            OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService operatorReviewPacketService,
            OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService
    ) {
        this.upkeepCatalogService = upkeepCatalogService;
        this.consumerHandoffMatrixService = consumerHandoffMatrixService;
        this.ciExpectationManifestService = ciExpectationManifestService;
        this.routeTopologyIndexService = routeTopologyIndexService;
        this.failClosedPolicyService = failClosedPolicyService;
        this.archiveDigestLedgerService = archiveDigestLedgerService;
        this.operatorReviewPacketService = operatorReviewPacketService;
        this.versionLineageService = versionLineageService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_UPKEEP_CATALOG)
    public OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse upkeepCatalog() {
        return upkeepCatalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONSUMER_HANDOFF_MATRIX)
    public OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse consumerHandoffMatrix() {
        return consumerHandoffMatrixService.matrix();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CI_EXPECTATION_MANIFEST)
    public OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse ciExpectationManifest() {
        return ciExpectationManifestService.manifest();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ROUTE_TOPOLOGY_INDEX)
    public OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse routeTopologyIndex() {
        return routeTopologyIndexService.index();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_FAIL_CLOSED_POLICY)
    public OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse failClosedPolicy() {
        return failClosedPolicyService.report();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_DIGEST_LEDGER)
    public OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse archiveDigestLedger() {
        return archiveDigestLedgerService.ledger();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_OPERATOR_REVIEW_PACKET)
    public OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse operatorReviewPacket() {
        return operatorReviewPacketService.packet();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_VERSION_LINEAGE)
    public OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse versionLineage() {
        return versionLineageService.lineage();
    }
}
