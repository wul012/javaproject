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
    var sourceReceipts =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSourceCatalog
            .receipts(rehearsal);
    var splitModules =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSplitCatalog
            .modules();
    var evidenceReferences =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestReferenceCatalog
            .references(rehearsal);
    var precheckFields =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestFieldCatalog
            .fields(rehearsal);
    var boundaryGuards =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestBoundaryCatalog
            .guards(rehearsal);
    var codeHealthGates =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestCodeHealthCatalog
            .gates();
    var verificationGates =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestVerificationCatalog
            .gates(
                sourceReceipts,
                splitModules,
                evidenceReferences,
                precheckFields,
                boundaryGuards,
                codeHealthGates);
    var handoffNotes =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestHandoffCatalog
            .notes();
    var markdownSections =
        ManifestRenderer.render(
            sourceReceipts,
            splitModules,
            evidenceReferences,
            precheckFields,
            boundaryGuards,
            codeHealthGates,
            verificationGates,
            handoffNotes);
    return OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            sourceReceipts,
            splitModules,
            evidenceReferences,
            precheckFields,
            boundaryGuards,
            codeHealthGates,
            verificationGates,
            handoffNotes,
            markdownSections);
  }
}
