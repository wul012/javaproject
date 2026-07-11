package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367";
  static final String REQUIRED_ARCHIVE_VERIFICATION_PLAN = "Node v368";
  static final String OPERATOR_HANDOFF_PLAN = "Node v369";
  static final String CONSUMER_PACKAGE_STATE =
      "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-ready";
  static final int EXPECTED_SOURCE_DIGEST_SNAPSHOT_COUNT = 1;
  static final int EXPECTED_MANIFEST_ENTRY_COUNT = 5;
  static final int EXPECTED_CONSUMER_AUDIENCE_COUNT = 4;
  static final int EXPECTED_PACKAGE_SECTION_COUNT = 5;
  static final int EXPECTED_ACCEPTANCE_CRITERION_COUNT = 5;
  static final int EXPECTED_CI_MATRIX_ENTRY_COUNT = 5;
  static final int EXPECTED_BOUNDARY_LOCK_COUNT = 8;
  static final int EXPECTED_HANDOFF_CHECKLIST_COUNT = 5;
  static final int EXPECTED_SCORECARD_ENTRY_COUNT = 8;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 9;

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
      response(
          String version,
          String endpoint,
          String profile,
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              sourceDigest,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .SourceDigestSnapshot>
              sourceDigests,
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
              handoffChecklist,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .ScorecardEntry>
              scorecard,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .MarkdownSection>
              markdownSections) {
    var sourceDigestCopy = List.copyOf(sourceDigests);
    var manifestCopy = List.copyOf(manifest);
    var audienceCopy = List.copyOf(audiences);
    var packageSectionCopy = List.copyOf(packageSections);
    var acceptanceCopy = List.copyOf(acceptanceCriteria);
    var ciMatrixCopy = List.copyOf(ciMatrix);
    var boundaryLockCopy = List.copyOf(boundaryLocks);
    var handoffChecklistCopy = List.copyOf(handoffChecklist);
    var scorecardCopy = List.copyOf(scorecard);
    var markdownSectionCopy = List.copyOf(markdownSections);
    int passedManifestCount = countManifest(manifestCopy);
    int readyAudienceCount = countReadyAudiences(audienceCopy);
    int readyPackageSectionCount = countReadySections(packageSectionCopy);
    int passedAcceptanceCount = countPassedAcceptance(acceptanceCopy);
    int readOnlyCiMatrixCount = countReadOnlyCiMatrix(ciMatrixCopy);
    int lockedBoundaryCount = countLockedBoundaries(boundaryLockCopy);
    int readyChecklistCount = countReadyChecklist(handoffChecklistCopy);
    int passedScorecardCount = countScorecard(scorecardCopy);

    List<String> checks = new ArrayList<>();
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-source-plan-" + SOURCE_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-required-archive-"
            + REQUIRED_ARCHIVE_VERIFICATION_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-operator-plan-"
            + OPERATOR_HANDOFF_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-source-digest-version-"
            + sourceDigest.version());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-source-digest-status-"
            + sourceDigest.status());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-source-digest-count-"
            + sourceDigestCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-manifest-count-"
            + manifestCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-passed-manifest-count-"
            + passedManifestCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-audience-count-"
            + audienceCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-ready-audience-count-"
            + readyAudienceCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-section-count-"
            + packageSectionCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-ready-section-count-"
            + readyPackageSectionCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-acceptance-count-"
            + acceptanceCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-passed-acceptance-count-"
            + passedAcceptanceCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-ci-matrix-count-"
            + ciMatrixCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-read-only-ci-matrix-count-"
            + readOnlyCiMatrixCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-boundary-lock-count-"
            + boundaryLockCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-locked-boundary-count-"
            + lockedBoundaryCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-checklist-count-"
            + handoffChecklistCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-ready-checklist-count-"
            + readyChecklistCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-scorecard-count-"
            + scorecardCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-passed-scorecard-count-"
            + passedScorecardCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-markdown-section-count-"
            + markdownSectionCopy.size());
    checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-no-upstream-autostart");
    checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-no-write-routing");
    checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-no-secret-value");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-consumer-package-no-raw-endpoint-resolution");
    checks.add("minimal-read-only-gate-operator-ci-handoff-consumer-package-no-managed-audit-http");

    String status =
        "passed".equals(sourceDigest.status())
                && sourceDigestCopy.size() == EXPECTED_SOURCE_DIGEST_SNAPSHOT_COUNT
                && manifestCopy.size() == EXPECTED_MANIFEST_ENTRY_COUNT
                && passedManifestCount == manifestCopy.size()
                && audienceCopy.size() == EXPECTED_CONSUMER_AUDIENCE_COUNT
                && readyAudienceCount == audienceCopy.size()
                && packageSectionCopy.size() == EXPECTED_PACKAGE_SECTION_COUNT
                && readyPackageSectionCount == packageSectionCopy.size()
                && acceptanceCopy.size() == EXPECTED_ACCEPTANCE_CRITERION_COUNT
                && passedAcceptanceCount == acceptanceCopy.size()
                && ciMatrixCopy.size() == EXPECTED_CI_MATRIX_ENTRY_COUNT
                && readOnlyCiMatrixCount == ciMatrixCopy.size()
                && boundaryLockCopy.size() == EXPECTED_BOUNDARY_LOCK_COUNT
                && lockedBoundaryCount == boundaryLockCopy.size()
                && handoffChecklistCopy.size() == EXPECTED_HANDOFF_CHECKLIST_COUNT
                && readyChecklistCount == handoffChecklistCopy.size()
                && scorecardCopy.size() == EXPECTED_SCORECARD_ENTRY_COUNT
                && passedScorecardCount == scorecardCopy.size()
                && markdownSectionCopy.size() == EXPECTED_MARKDOWN_SECTION_COUNT
            ? "passed"
            : "blocked";

    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse(
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
        sourceDigest.version(),
        sourceDigest.endpoint(),
        sourceDigest.digestState(),
        CONSUMER_PACKAGE_STATE,
        sourceDigestCopy.size(),
        manifestCopy.size(),
        passedManifestCount,
        audienceCopy.size(),
        readyAudienceCount,
        packageSectionCopy.size(),
        readyPackageSectionCount,
        acceptanceCopy.size(),
        passedAcceptanceCount,
        ciMatrixCopy.size(),
        readOnlyCiMatrixCount,
        boundaryLockCopy.size(),
        lockedBoundaryCount,
        handoffChecklistCopy.size(),
        readyChecklistCount,
        scorecardCopy.size(),
        passedScorecardCount,
        markdownSectionCopy.size(),
        sourceDigestCopy,
        manifestCopy,
        audienceCopy,
        packageSectionCopy,
        acceptanceCopy,
        ciMatrixCopy,
        boundaryLockCopy,
        handoffChecklistCopy,
        scorecardCopy,
        markdownSectionCopy,
        List.copyOf(checks),
        status);
  }

  private static int countManifest(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .ManifestEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int countReadyAudiences(
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

  private static int countReadySections(
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

  private static int countPassedAcceptance(
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

  private static int countReadOnlyCiMatrix(
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

  private static int countLockedBoundaries(
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

  private static int countReadyChecklist(
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

  private static int countScorecard(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .ScorecardEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }
}
