package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse;
import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistrySupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367";
  static final String REQUIRED_ARCHIVE_VERIFICATION_PLAN = "Node v368";
  static final String OPERATOR_HANDOFF_PLAN = "Node v369";
  static final String ARCHIVE_REGISTRY_STATE =
      "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-archive-registry-ready";
  static final int EXPECTED_SOURCE_ARCHIVE_SNAPSHOT_COUNT = 1;
  static final int EXPECTED_ARTIFACT_MANIFEST_COUNT = 7;
  static final int EXPECTED_ROUTE_PACKAGE_COUNT = 4;
  static final int EXPECTED_OPERATOR_PACK_COUNT = 4;
  static final int EXPECTED_CI_ATTESTATION_COUNT = 5;
  static final int EXPECTED_BOUNDARY_SEAL_COUNT = 8;
  static final int EXPECTED_RETENTION_WINDOW_COUNT = 5;
  static final int EXPECTED_CLOSEOUT_LEDGER_COUNT = 6;
  static final int EXPECTED_SCORECARD_ENTRY_COUNT = 8;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 9;

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistrySupport() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
      response(
          String version,
          String endpoint,
          String profile,
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
              source,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .SourceArchiveSnapshot>
              sourceArchiveSnapshots,
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
              closeoutLedger,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .ScorecardEntry>
              scorecard,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .MarkdownSection>
              markdownSections) {
    var sourceArchiveCopy = List.copyOf(sourceArchiveSnapshots);
    var artifactManifestCopy = List.copyOf(artifactManifest);
    var routePackageCopy = List.copyOf(routePackages);
    var operatorPackCopy = List.copyOf(operatorPacks);
    var ciAttestationCopy = List.copyOf(ciAttestations);
    var boundarySealCopy = List.copyOf(boundarySeals);
    var retentionWindowCopy = List.copyOf(retentionWindows);
    var closeoutLedgerCopy = List.copyOf(closeoutLedger);
    var scorecardCopy = List.copyOf(scorecard);
    var markdownSectionCopy = List.copyOf(markdownSections);
    int passedArtifactManifestCount = countArtifactManifest(artifactManifestCopy);
    int readyRoutePackageCount = countReadyRoutePackages(routePackageCopy);
    int readyOperatorPackCount = countReadyOperatorPacks(operatorPackCopy);
    int passedCiAttestationCount = countPassedCiAttestations(ciAttestationCopy);
    int lockedBoundarySealCount = countLockedBoundarySeals(boundarySealCopy);
    int readyRetentionWindowCount = countReadyRetentionWindows(retentionWindowCopy);
    int readyCloseoutLedgerCount = countReadyCloseoutLedger(closeoutLedgerCopy);
    int passedScorecardCount = countScorecard(scorecardCopy);

    List<String> checks = new ArrayList<>();
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-source-plan-" + SOURCE_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-required-archive-"
            + REQUIRED_ARCHIVE_VERIFICATION_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-operator-plan-"
            + OPERATOR_HANDOFF_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-source-version-"
            + source.version());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-source-state-"
            + source.releaseAcceptanceState());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-source-count-"
            + sourceArchiveCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-artifact-manifest-count-"
            + artifactManifestCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-passed-artifact-manifest-count-"
            + passedArtifactManifestCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-route-package-count-"
            + routePackageCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-ready-route-package-count-"
            + readyRoutePackageCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-operator-pack-count-"
            + operatorPackCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-ready-operator-pack-count-"
            + readyOperatorPackCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-ci-attestation-count-"
            + ciAttestationCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-passed-ci-attestation-count-"
            + passedCiAttestationCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-boundary-seal-count-"
            + boundarySealCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-locked-boundary-seal-count-"
            + lockedBoundarySealCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-retention-window-count-"
            + retentionWindowCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-ready-retention-window-count-"
            + readyRetentionWindowCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-closeout-ledger-count-"
            + closeoutLedgerCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-ready-closeout-ledger-count-"
            + readyCloseoutLedgerCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-scorecard-count-"
            + scorecardCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-passed-scorecard-count-"
            + passedScorecardCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-markdown-section-count-"
            + markdownSectionCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-consumes-release-acceptance");
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-registry-no-upstream-autostart");
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-registry-no-write-routing");
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-registry-no-secret-value");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-no-raw-endpoint-resolution");
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-registry-no-managed-audit-http");
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-registry-no-runtime-execution");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-registry-no-deployment-rollback");

    String status =
        "passed".equals(source.status())
                && source.readOnly()
                && !source.executionAllowed()
                && !source.startsJavaService()
                && !source.startsMiniKvService()
                && !source.readsCredentialValue()
                && !source.resolvesRawEndpointUrl()
                && !source.managedAuditHttpAllowed()
                && sourceArchiveCopy.size() == EXPECTED_SOURCE_ARCHIVE_SNAPSHOT_COUNT
                && artifactManifestCopy.size() == EXPECTED_ARTIFACT_MANIFEST_COUNT
                && passedArtifactManifestCount == artifactManifestCopy.size()
                && routePackageCopy.size() == EXPECTED_ROUTE_PACKAGE_COUNT
                && readyRoutePackageCount == routePackageCopy.size()
                && operatorPackCopy.size() == EXPECTED_OPERATOR_PACK_COUNT
                && readyOperatorPackCount == operatorPackCopy.size()
                && ciAttestationCopy.size() == EXPECTED_CI_ATTESTATION_COUNT
                && passedCiAttestationCount == ciAttestationCopy.size()
                && boundarySealCopy.size() == EXPECTED_BOUNDARY_SEAL_COUNT
                && lockedBoundarySealCount == boundarySealCopy.size()
                && retentionWindowCopy.size() == EXPECTED_RETENTION_WINDOW_COUNT
                && readyRetentionWindowCount == retentionWindowCopy.size()
                && closeoutLedgerCopy.size() == EXPECTED_CLOSEOUT_LEDGER_COUNT
                && readyCloseoutLedgerCount == closeoutLedgerCopy.size()
                && scorecardCopy.size() == EXPECTED_SCORECARD_ENTRY_COUNT
                && passedScorecardCount == scorecardCopy.size()
                && markdownSectionCopy.size() == EXPECTED_MARKDOWN_SECTION_COUNT
            ? "passed"
            : "blocked";

    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse(
        PROJECT,
        version,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        SOURCE_PLAN,
        REQUIRED_ARCHIVE_VERIFICATION_PLAN,
        OPERATOR_HANDOFF_PLAN,
        source.version(),
        source.endpoint(),
        source.releaseAcceptanceState(),
        ARCHIVE_REGISTRY_STATE,
        sourceArchiveCopy.size(),
        artifactManifestCopy.size(),
        passedArtifactManifestCount,
        routePackageCopy.size(),
        readyRoutePackageCount,
        operatorPackCopy.size(),
        readyOperatorPackCount,
        ciAttestationCopy.size(),
        passedCiAttestationCount,
        boundarySealCopy.size(),
        lockedBoundarySealCount,
        retentionWindowCopy.size(),
        readyRetentionWindowCount,
        closeoutLedgerCopy.size(),
        readyCloseoutLedgerCount,
        scorecardCopy.size(),
        passedScorecardCount,
        markdownSectionCopy.size(),
        sourceArchiveCopy,
        artifactManifestCopy,
        routePackageCopy,
        operatorPackCopy,
        ciAttestationCopy,
        boundarySealCopy,
        retentionWindowCopy,
        closeoutLedgerCopy,
        scorecardCopy,
        markdownSectionCopy,
        List.copyOf(checks),
        status);
  }

  private static int countArtifactManifest(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .ArtifactManifestEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int countReadyRoutePackages(
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

  private static int countReadyOperatorPacks(
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

  private static int countPassedCiAttestations(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .CiAttestationEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int countLockedBoundarySeals(
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

  private static int countReadyRetentionWindows(
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

  private static int countReadyCloseoutLedger(
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

  private static int countScorecard(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                  .ScorecardEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }
}
