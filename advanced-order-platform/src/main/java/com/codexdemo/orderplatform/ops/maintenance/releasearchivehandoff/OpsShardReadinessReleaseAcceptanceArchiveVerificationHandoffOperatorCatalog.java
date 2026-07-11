package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffOperatorCatalog {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffOperatorCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.OperatorInstruction>
      instructions(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
              source) {
    return source.operatorPacks().stream()
        .map(
            pack ->
                new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                    .OperatorInstruction(
                    pack.order(),
                    pack.owner(),
                    pack.sourceEvidence(),
                    "verify archive evidence before "
                        + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSupport
                            .ARCHIVE_VERIFICATION_PLAN,
                    pack.ready(),
                    pack.status()))
        .toList();
  }
}
