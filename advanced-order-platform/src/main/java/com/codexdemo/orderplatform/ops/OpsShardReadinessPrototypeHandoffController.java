package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeHandoffCatalogResponse;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeHandoffEvidenceResponse;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeHandoffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PrototypeRoutes.BASE_PATH)
public class OpsShardReadinessPrototypeHandoffController {

  private final OpsShardReadinessPrototypeHandoffService handoffService;

  public OpsShardReadinessPrototypeHandoffController(
      OpsShardReadinessPrototypeHandoffService handoffService) {
    this.handoffService = handoffService;
  }

  @GetMapping(PrototypeRoutes.HANDOFF_CATALOG)
  public OpsShardReadinessPrototypeHandoffCatalogResponse catalog() {
    return handoffService.catalog();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_ENDPOINT_INVENTORY)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse endpointInventory() {
    return handoffService.endpointInventory();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_BOUNDARY_MATRIX)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse boundaryMatrix() {
    return handoffService.boundaryMatrix();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_CONSUMER_CHECKLIST)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse consumerVerificationChecklist() {
    return handoffService.consumerVerificationChecklist();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_READ_WINDOW_CHECKLIST)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse readWindowChecklist() {
    return handoffService.readWindowChecklist();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_DIGEST_MANIFEST)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse digestManifest() {
    return handoffService.digestManifest();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_CI_MANIFEST)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse ciManifest() {
    return handoffService.ciManifest();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_ARCHIVE_MANIFEST)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse archiveManifest() {
    return handoffService.archiveManifest();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_OPERATOR_SIGNOFF)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse operatorSignoffPacket() {
    return handoffService.operatorSignoffPacket();
  }

  @GetMapping(PrototypeRoutes.HANDOFF_CLOSEOUT)
  public OpsShardReadinessPrototypeHandoffEvidenceResponse closeout() {
    return handoffService.closeout();
  }
}
