package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCloseoutCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCloseoutCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
              .CloseoutCheckpoint>
      checkpoints(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              source) {
    boolean ready = "passed".equals(source.status());
    return List.of(
        checkpoint(1, "read-verification-dossier", "release-review", source.version(), ready),
        checkpoint(
            2,
            "verify-readiness-gates",
            "release-review",
            "scorecard=" + source.passedScorecardEntryCount(),
            ready),
        checkpoint(
            3,
            "confirm-boundary-controls",
            "operator",
            "boundaries=" + source.lockedBoundaryAuditCount(),
            ready),
        checkpoint(
            4, "record-ci-replay-lanes", "ci", "ci-lanes=" + source.readOnlyCiLaneCount(), ready),
        checkpoint(
            5,
            "archive-release-evidence",
            "release-review",
            "markdown=" + source.markdownSectionCount(),
            ready),
        checkpoint(
            6,
            "handoff-release-acceptance",
            "operator-ci",
            "receipts=" + source.readyHandoffReceiptCount(),
            ready));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
          .CloseoutCheckpoint
      checkpoint(int order, String item, String owner, String evidence, boolean ready) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
        .CloseoutCheckpoint(order, item, owner, evidence, ready, ready ? "passed" : "blocked");
  }
}
