package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupAssuranceController {

    private final OpsShardReadinessRouteCleanupAuditTrailService auditTrailService;

    private final OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService;

    private final OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService;

    public OpsShardReadinessRouteCleanupAssuranceController(
            OpsShardReadinessRouteCleanupAuditTrailService auditTrailService,
            OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService,
            OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService
    ) {
        this.auditTrailService = auditTrailService;
        this.acceptanceReceiptService = acceptanceReceiptService;
        this.evidenceRegisterService = evidenceRegisterService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_AUDIT_TRAIL)
    public OpsShardReadinessRouteCleanupAuditTrailResponse auditTrail() {
        return auditTrailService.auditTrail();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ACCEPTANCE_RECEIPT)
    public OpsShardReadinessRouteCleanupAcceptanceReceiptResponse acceptanceReceipt() {
        return acceptanceReceiptService.receipt();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EVIDENCE_REGISTER)
    public OpsShardReadinessRouteCleanupEvidenceRegisterResponse evidenceRegister() {
        return evidenceRegisterService.register();
    }
}
