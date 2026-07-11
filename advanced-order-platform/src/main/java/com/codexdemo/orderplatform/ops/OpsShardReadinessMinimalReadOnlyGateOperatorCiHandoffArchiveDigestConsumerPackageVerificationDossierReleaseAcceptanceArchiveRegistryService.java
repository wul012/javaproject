package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryService {

  static final String RESPONSE_VERSION = "Java v1522";
  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry.v1";

  private final
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryService
      sourceRegistryService;

  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryService(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryService
          sourceRegistryService) {
    this.sourceRegistryService = sourceRegistryService;
  }

  @Transactional(readOnly = true)
  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
      registry() {
    var source = sourceRegistryService.registry();
    var sourceArchiveSnapshots =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveSourceCatalog
            .snapshots(source);
    var artifactManifest =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveArtifactManifestCatalog
            .manifest(source);
    var routePackages =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRoutePackageCatalog
            .routes(source);
    var operatorPacks =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveOperatorPackCatalog
            .packs(source);
    var ciAttestations =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveCiAttestationCatalog
            .attestations(source);
    var boundarySeals =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveBoundarySealCatalog
            .seals(source);
    var retentionWindows =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRetentionWindowCatalog
            .windows(source);
    var closeoutLedger =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveCloseoutLedgerCatalog
            .ledger(source);
    var scorecard =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveScorecardCatalog
            .scorecard(
                source,
                artifactManifest,
                routePackages,
                operatorPacks,
                ciAttestations,
                boundarySeals,
                retentionWindows,
                closeoutLedger);
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistrySupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            PROFILE,
            source,
            sourceArchiveSnapshots,
            artifactManifest,
            routePackages,
            operatorPacks,
            ciAttestations,
            boundarySeals,
            retentionWindows,
            closeoutLedger,
            scorecard,
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryRenderer
                .render(
                    sourceArchiveSnapshots,
                    artifactManifest,
                    routePackages,
                    operatorPacks,
                    ciAttestations,
                    boundarySeals,
                    retentionWindows,
                    closeoutLedger,
                    scorecard));
  }
}
