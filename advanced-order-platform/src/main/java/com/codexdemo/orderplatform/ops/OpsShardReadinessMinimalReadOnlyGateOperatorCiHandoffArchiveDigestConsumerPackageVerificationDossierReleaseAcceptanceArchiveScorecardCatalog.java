package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveScorecardCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveScorecardCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
              .ScorecardEntry>
      scorecard(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
              source,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .ArtifactManifestEntry>
              artifactManifest,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .RoutePackageEntry>
              routePackages,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .OperatorPackEntry>
              operatorPacks,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .CiAttestationEntry>
              ciAttestations,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .BoundarySealEntry>
              boundarySeals,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .RetentionWindowEntry>
              retentionWindows,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .CloseoutLedgerEntry>
              closeoutLedger) {
    return List.of(
        score("source-release-acceptance-status", 1, "passed".equals(source.status()) ? 1 : 0),
        score("artifact-manifest", 7, passedArtifactManifest(artifactManifest)),
        score("route-packages", 4, readyRoutePackages(routePackages)),
        score("operator-packs", 4, readyOperatorPacks(operatorPacks)),
        score("ci-attestations", 5, passedCiAttestations(ciAttestations)),
        score("boundary-seals", 8, lockedBoundarySeals(boundarySeals)),
        score("retention-windows", 5, readyRetentionWindows(retentionWindows)),
        score("closeout-ledger", 6, readyCloseoutLedger(closeoutLedger)));
  }

  private static int passedArtifactManifest(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .ArtifactManifestEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int readyRoutePackages(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .RoutePackageEntry>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .RoutePackageEntry
                    ::ready)
            .count();
  }

  private static int readyOperatorPacks(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .OperatorPackEntry>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .OperatorPackEntry
                    ::ready)
            .count();
  }

  private static int passedCiAttestations(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .CiAttestationEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int lockedBoundarySeals(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .BoundarySealEntry>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .BoundarySealEntry
                    ::locked)
            .count();
  }

  private static int readyRetentionWindows(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .RetentionWindowEntry>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .RetentionWindowEntry
                    ::ready)
            .count();
  }

  private static int readyCloseoutLedger(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .CloseoutLedgerEntry>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .CloseoutLedgerEntry
                    ::ready)
            .count();
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
          .ScorecardEntry
      score(String name, int expected, int actual) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
        .ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }
}
