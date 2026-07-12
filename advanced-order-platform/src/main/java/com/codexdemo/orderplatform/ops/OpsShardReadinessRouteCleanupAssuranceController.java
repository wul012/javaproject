package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupAcceptanceReceiptResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupAcceptanceReceiptService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupAuditTrailResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupAuditTrailService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceRegisterResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceRegisterService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupOperationalSnapshotResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupOperationalSnapshotService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupAssuranceController {

  private final OpsShardReadinessRouteCleanupAuditTrailService auditTrailService;

  private final OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService;

  private final OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService;

  private final OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService;

  public OpsShardReadinessRouteCleanupAssuranceController(
      OpsShardReadinessRouteCleanupAuditTrailService auditTrailService,
      OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService,
      OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService,
      OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService) {
    this.auditTrailService = auditTrailService;
    this.acceptanceReceiptService = acceptanceReceiptService;
    this.evidenceRegisterService = evidenceRegisterService;
    this.operationalSnapshotService = operationalSnapshotService;
  }

  @GetMapping(RouteCleanupRoutes.AUDIT_TRAIL)
  public OpsShardReadinessRouteCleanupAuditTrailResponse auditTrail() {
    return auditTrailService.auditTrail();
  }

  @GetMapping(RouteCleanupRoutes.ACCEPTANCE_RECEIPT)
  public OpsShardReadinessRouteCleanupAcceptanceReceiptResponse acceptanceReceipt() {
    return acceptanceReceiptService.receipt();
  }

  @GetMapping(RouteCleanupRoutes.EVIDENCE_REGISTER)
  public OpsShardReadinessRouteCleanupEvidenceRegisterResponse evidenceRegister() {
    return evidenceRegisterService.register();
  }

  @GetMapping(RouteCleanupRoutes.OPERATIONAL_SNAPSHOT)
  public OpsShardReadinessRouteCleanupOperationalSnapshotResponse operationalSnapshot() {
    return operationalSnapshotService.snapshot();
  }
}
