package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCiCatalog {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCiCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof> proofs(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
          source) {
    return source.ciAttestations().stream()
        .map(
            ci ->
                new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof(
                    ci.order(),
                    ci.batch(),
                    ci.commandFamily(),
                    ci.readOnly(),
                    ci.sourcePassed(),
                    ci.status()))
        .toList();
  }
}
