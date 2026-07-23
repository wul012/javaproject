package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.OpsEvidenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService {

  static final String RESPONSE_VERSION = "Java v1687";
  static final String ENDPOINT =
      OpsShardReadinessSandboxConnectionRoutePaths.BASE_PATH
          + OpsShardReadinessSandboxConnectionRoutePaths
              .SANDBOX_CONNECTION_BLOCKED_EXECUTION_CONTEXT_NORMALIZATION_DOSSIER;

  private final OpsEvidenceService opsEvidenceService;

  public OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService(
      OpsEvidenceService opsEvidenceService) {
    this.opsEvidenceService = opsEvidenceService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse dossier() {
    var rehearsal = opsEvidenceService.releaseApprovalRehearsal();
    var evidence = DossierCatalog.evidence(rehearsal);
    return DossierSupport.response(
        RESPONSE_VERSION, ENDPOINT, rehearsal, evidence, DossierRenderer.render(evidence));
  }
}
