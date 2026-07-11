package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierHandoffReceiptRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierHandoffReceiptRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                      .HandoffReceipt>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRendererSupport
        .section(
            "Handoff Receipts",
            entries.stream()
                .map(
                    entry ->
                        entry.receiver()
                            + " | receiptType="
                            + entry.receiptType()
                            + " | evidence="
                            + entry.sourceEvidence()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
