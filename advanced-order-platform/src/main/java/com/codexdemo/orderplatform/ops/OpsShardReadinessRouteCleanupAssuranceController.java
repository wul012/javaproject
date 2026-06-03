package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupAssuranceController {

    private final OpsShardReadinessRouteCleanupAuditTrailService auditTrailService;

    public OpsShardReadinessRouteCleanupAssuranceController(
            OpsShardReadinessRouteCleanupAuditTrailService auditTrailService
    ) {
        this.auditTrailService = auditTrailService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_AUDIT_TRAIL)
    public OpsShardReadinessRouteCleanupAuditTrailResponse auditTrail() {
        return auditTrailService.auditTrail();
    }
}
