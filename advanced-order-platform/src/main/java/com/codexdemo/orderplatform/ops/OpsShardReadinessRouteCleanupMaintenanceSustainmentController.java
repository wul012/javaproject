package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentController {

    private final OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService releaseChecklistService;

    private final OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService remediationQueueService;

    private final OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService freshnessWindowService;

    private final OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService ownershipRegisterService;

    private final OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService riskLedgerService;

    private final OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoffAcceptanceDigestService;

    public OpsShardReadinessRouteCleanupMaintenanceSustainmentController(
            OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService releaseChecklistService,
            OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService remediationQueueService,
            OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService freshnessWindowService,
            OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService ownershipRegisterService,
            OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService riskLedgerService,
            OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoffAcceptanceDigestService
    ) {
        this.releaseChecklistService = releaseChecklistService;
        this.remediationQueueService = remediationQueueService;
        this.freshnessWindowService = freshnessWindowService;
        this.ownershipRegisterService = ownershipRegisterService;
        this.riskLedgerService = riskLedgerService;
        this.handoffAcceptanceDigestService = handoffAcceptanceDigestService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RELEASE_CHECKLIST)
    public OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse releaseChecklist() {
        return releaseChecklistService.checklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_REMEDIATION_QUEUE)
    public OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse remediationQueue() {
        return remediationQueueService.queue();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_FRESHNESS_WINDOW)
    public OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse freshnessWindow() {
        return freshnessWindowService.window();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_OWNERSHIP_REGISTER)
    public OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse ownershipRegister() {
        return ownershipRegisterService.register();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RISK_LEDGER)
    public OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse riskLedger() {
        return riskLedgerService.ledger();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_HANDOFF_ACCEPTANCE_DIGEST)
    public OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse handoffAcceptanceDigest() {
        return handoffAcceptanceDigestService.digest();
    }
}
