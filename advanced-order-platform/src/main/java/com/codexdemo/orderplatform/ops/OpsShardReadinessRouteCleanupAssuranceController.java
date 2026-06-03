package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupAssuranceController {

    private final OpsShardReadinessRouteCleanupAuditTrailService auditTrailService;

    private final OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService;

    public OpsShardReadinessRouteCleanupAssuranceController(
            OpsShardReadinessRouteCleanupAuditTrailService auditTrailService,
            OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService
    ) {
        this.auditTrailService = auditTrailService;
        this.acceptanceReceiptService = acceptanceReceiptService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_AUDIT_TRAIL)
    public OpsShardReadinessRouteCleanupAuditTrailResponse auditTrail() {
        return auditTrailService.auditTrail();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ACCEPTANCE_RECEIPT)
    public OpsShardReadinessRouteCleanupAcceptanceReceiptResponse acceptanceReceipt() {
        return acceptanceReceiptService.receipt();
    }
}
