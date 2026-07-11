package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierHandoffReceiptCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierHandoffReceiptCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              .HandoffReceipt>
      receipts(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              source) {
    return List.of(
        receipt("operator-ci-handoff-owner", source.version(), "consumer-package-source", source),
        receipt(
            "node-v368-archive-verifier",
            source.sourceDigestVersion(),
            "archive-verification-input",
            source),
        receipt("node-v369-operator-ci", source.profile(), "operator-ci-handoff-input", source),
        receipt(
            "java-read-only-boundary-owner",
            source.endpoint(),
            "read-only-boundary-continuity",
            source));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .HandoffReceipt
      receipt(
          String receiver,
          String sourceEvidence,
          String receiptType,
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              source) {
    boolean ready =
        "passed".equals(source.status()) && sourceEvidence != null && !sourceEvidence.isBlank();
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
        .HandoffReceipt(receiver, sourceEvidence, receiptType, ready, ready ? "passed" : "blocked");
  }
}
