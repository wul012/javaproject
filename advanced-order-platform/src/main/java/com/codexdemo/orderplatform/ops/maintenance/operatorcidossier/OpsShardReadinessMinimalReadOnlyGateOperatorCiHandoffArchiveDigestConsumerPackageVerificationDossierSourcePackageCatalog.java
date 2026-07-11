package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSourcePackageCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSourcePackageCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              .SourcePackageSnapshot>
      snapshots(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              source) {
    return List.of(
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .SourcePackageSnapshot(
            source.version(),
            source.endpoint(),
            source.profile(),
            source.consumerPackageState(),
            source.manifestEntryCount(),
            source.packageSectionCount(),
            source.ciMatrixEntryCount(),
            source.boundaryLockCount(),
            source.handoffChecklistCount(),
            source.status()));
  }
}
