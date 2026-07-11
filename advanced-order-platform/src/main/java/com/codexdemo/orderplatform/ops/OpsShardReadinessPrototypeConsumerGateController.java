package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeConsumerGateCatalogResponse;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeConsumerGateEvidenceResponse;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeConsumerGateService;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PrototypeRoutes.BASE_PATH)
public class OpsShardReadinessPrototypeConsumerGateController {

  private final OpsShardReadinessPrototypeConsumerGateService consumerGateService;

  public OpsShardReadinessPrototypeConsumerGateController(
      OpsShardReadinessPrototypeConsumerGateService consumerGateService) {
    this.consumerGateService = consumerGateService;
  }

  @GetMapping(PrototypeRoutes.CONSUMER_CATALOG)
  public OpsShardReadinessPrototypeConsumerGateCatalogResponse catalog() {
    return consumerGateService.catalog();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_SOURCE_INVENTORY)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse sourceInventory() {
    return consumerGateService.sourceInventory();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_FIELD_CHECKLIST)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse minimalFieldChecklist() {
    return consumerGateService.minimalFieldChecklist();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_ROUTE_PREVIEW)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse routeTopologyPreview() {
    return consumerGateService.routeTopologyPreview();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_BOUNDARY_MATRIX)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse boundaryMatrix() {
    return consumerGateService.boundaryMatrix();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_DIGEST_ACCEPTANCE)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse digestAcceptance() {
    return consumerGateService.digestAcceptance();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_CI_PLAN)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse ciBatchPlan() {
    return consumerGateService.ciBatchPlan();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_ARCHIVE_MANIFEST)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse archiveManifest() {
    return consumerGateService.archiveManifest();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_OPERATOR_SIGNOFF)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse operatorSignoff() {
    return consumerGateService.operatorSignoff();
  }

  @GetMapping(PrototypeRoutes.CONSUMER_CLOSEOUT)
  public OpsShardReadinessPrototypeConsumerGateEvidenceResponse closeout() {
    return consumerGateService.closeout();
  }
}
