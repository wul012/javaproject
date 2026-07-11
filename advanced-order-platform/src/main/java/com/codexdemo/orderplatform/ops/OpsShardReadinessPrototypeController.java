package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeCatalogResponse;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceResponse;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PrototypeRoutes.BASE_PATH)
public class OpsShardReadinessPrototypeController {

  private final OpsShardReadinessPrototypeEvidenceService prototypeEvidenceService;

  public OpsShardReadinessPrototypeController(
      OpsShardReadinessPrototypeEvidenceService prototypeEvidenceService) {
    this.prototypeEvidenceService = prototypeEvidenceService;
  }

  @GetMapping(PrototypeRoutes.CATALOG)
  public OpsShardReadinessPrototypeCatalogResponse catalog() {
    return prototypeEvidenceService.catalog();
  }

  @GetMapping(PrototypeRoutes.FIXTURE_ECHO)
  public OpsShardReadinessPrototypeEvidenceResponse fixtureEcho() {
    return prototypeEvidenceService.fixtureEcho();
  }

  @GetMapping(PrototypeRoutes.FIELD_ALIGNMENT)
  public OpsShardReadinessPrototypeEvidenceResponse fieldAlignment() {
    return prototypeEvidenceService.fieldAlignment();
  }

  @GetMapping(PrototypeRoutes.READ_ONLY_BRIDGE)
  public OpsShardReadinessPrototypeEvidenceResponse readOnlyIntegrationBridge() {
    return prototypeEvidenceService.readOnlyIntegrationBridge();
  }

  @GetMapping(PrototypeRoutes.CLEANUP_BRIDGE)
  public OpsShardReadinessPrototypeEvidenceResponse routeCleanupBridge() {
    return prototypeEvidenceService.routeCleanupBridge();
  }

  @GetMapping(PrototypeRoutes.READ_WINDOW_HANDOFF)
  public OpsShardReadinessPrototypeEvidenceResponse readWindowHandoff() {
    return prototypeEvidenceService.readWindowHandoff();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_GATE_PACKET)
  public OpsShardReadinessPrototypeEvidenceResponse consumerGatePacket() {
    return prototypeEvidenceService.consumerGatePacket();
  }

  @GetMapping(PrototypeRoutes.OPERATOR_CI_HANDOFF)
  public OpsShardReadinessPrototypeEvidenceResponse operatorCiHandoff() {
    return prototypeEvidenceService.operatorCiHandoff();
  }

  @GetMapping(PrototypeRoutes.AUDIT_DIGEST)
  public OpsShardReadinessPrototypeEvidenceResponse auditDigest() {
    return prototypeEvidenceService.auditDigest();
  }

  @GetMapping(PrototypeRoutes.CLOSEOUT)
  public OpsShardReadinessPrototypeEvidenceResponse closeout() {
    return prototypeEvidenceService.closeout();
  }
}
