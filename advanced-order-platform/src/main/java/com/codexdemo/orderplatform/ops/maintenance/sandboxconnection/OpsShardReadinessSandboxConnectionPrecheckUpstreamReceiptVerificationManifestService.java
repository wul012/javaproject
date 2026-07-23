package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.OpsEvidenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService {

  static final String RESPONSE_VERSION = "Java v1707";
  static final String ENDPOINT =
      OpsShardReadinessSandboxConnectionRoutePaths.BASE_PATH
          + OpsShardReadinessSandboxConnectionRoutePaths
              .SANDBOX_CONNECTION_PRECHECK_UPSTREAM_RECEIPT_VERIFICATION_MANIFEST;

  private final OpsEvidenceService opsEvidenceService;

  public OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService(
      OpsEvidenceService opsEvidenceService) {
    this.opsEvidenceService = opsEvidenceService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
      manifest() {
    var rehearsal = opsEvidenceService.releaseApprovalRehearsal();
    var evidence = ManifestCatalog.evidence(rehearsal);
    var markdownSections = ManifestRenderer.render(evidence);
    return ManifestSupport.response(RESPONSE_VERSION, ENDPOINT, evidence, markdownSections);
  }
}
