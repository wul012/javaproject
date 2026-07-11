package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              .ScorecardEntry>
      scorecard(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .ManifestEntry>
              manifest,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .ConsumerAudience>
              audiences,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .PackageSection>
              packageSections,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .AcceptanceCriterion>
              acceptanceCriteria,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .CiMatrixEntry>
              ciMatrix,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .BoundaryLock>
              boundaryLocks,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .HandoffChecklistItem>
              checklist) {
    return List.of(
        score("source-digest-status", 1, "passed".equals(source.status()) ? 1 : 0),
        score(
            "manifest",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport
                .EXPECTED_MANIFEST_ENTRY_COUNT,
            passedManifest(manifest)),
        score(
            "consumer-audiences",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport
                .EXPECTED_CONSUMER_AUDIENCE_COUNT,
            readyAudiences(audiences)),
        score(
            "package-sections",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport
                .EXPECTED_PACKAGE_SECTION_COUNT,
            readySections(packageSections)),
        score(
            "acceptance-criteria",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport
                .EXPECTED_ACCEPTANCE_CRITERION_COUNT,
            passedAcceptance(acceptanceCriteria)),
        score(
            "ci-matrix",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport
                .EXPECTED_CI_MATRIX_ENTRY_COUNT,
            readOnlyCiMatrix(ciMatrix)),
        score(
            "boundary-locks",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport
                .EXPECTED_BOUNDARY_LOCK_COUNT,
            lockedBoundaries(boundaryLocks)),
        score(
            "handoff-checklist",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport
                .EXPECTED_HANDOFF_CHECKLIST_COUNT,
            readyChecklist(checklist)));
  }

  private static int passedManifest(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .ManifestEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int readyAudiences(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .ConsumerAudience>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .ConsumerAudience
                    ::ready)
            .count();
  }

  private static int readySections(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .PackageSection>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .PackageSection
                    ::ready)
            .count();
  }

  private static int passedAcceptance(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .AcceptanceCriterion>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .AcceptanceCriterion
                    ::passed)
            .count();
  }

  private static int readOnlyCiMatrix(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .CiMatrixEntry>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .CiMatrixEntry
                    ::readOnly)
            .count();
  }

  private static int lockedBoundaries(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .BoundaryLock>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .BoundaryLock
                    ::locked)
            .count();
  }

  private static int readyChecklist(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .HandoffChecklistItem>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .HandoffChecklistItem
                    ::ready)
            .count();
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .ScorecardEntry
      score(String name, int expected, int actual) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
        .ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }
}
