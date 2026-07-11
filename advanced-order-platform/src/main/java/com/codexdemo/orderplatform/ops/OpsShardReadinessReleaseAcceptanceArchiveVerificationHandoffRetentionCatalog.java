package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRetentionCatalog {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRetentionCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard>
      guards(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
              source) {
    return source.retentionWindows().stream()
        .map(
            window ->
                new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                    .RetentionGuard(
                    window.name(),
                    window.sourceEvidence(),
                    window.retentionWindow(),
                    window.ready(),
                    window.status()))
        .toList();
  }
}
