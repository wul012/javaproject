package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

final class PackageSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367";
  static final String REQUIRED_ARCHIVE_VERIFICATION_PLAN = "Node v368";
  static final String OPERATOR_HANDOFF_PLAN = "Node v369";
  static final String CONSUMER_PACKAGE_STATE =
      "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-ready";
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 9;

  private PackageSupport() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
      response(
          String version,
          String endpoint,
          String profile,
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              sourceDigest,
          PackageCatalog.Evidence evidence,
          List<MarkdownSection> markdownSections) {
    var sourceDigestCopy = evidence.sourceDigests();
    var manifestCopy = evidence.manifest();
    var audienceCopy = evidence.audiences();
    var packageSectionCopy = evidence.sections();
    var acceptanceCopy = evidence.criteria();
    var ciMatrixCopy = evidence.ciMatrix();
    var boundaryLockCopy = evidence.locks();
    var handoffChecklistCopy = evidence.checklist();
    var scorecardCopy = evidence.scorecard();
    var markdownSectionCopy = List.copyOf(markdownSections);
    int passedManifestCount = count(manifestCopy, entry -> "passed".equals(entry.status()));
    int readyAudienceCount = count(audienceCopy, entry -> entry.ready());
    int readyPackageSectionCount = count(packageSectionCopy, entry -> entry.ready());
    int passedAcceptanceCount = count(acceptanceCopy, entry -> entry.passed());
    int readOnlyCiMatrixCount = count(ciMatrixCopy, entry -> entry.readOnly());
    int lockedBoundaryCount = count(boundaryLockCopy, entry -> entry.locked());
    int readyChecklistCount = count(handoffChecklistCopy, entry -> entry.ready());
    int passedScorecardCount = count(scorecardCopy, entry -> "passed".equals(entry.status()));

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
                && sourceDigestCopy.size() == PackageCatalog.SOURCE_COUNT
                && manifestCopy.size() == PackageCatalog.MANIFEST_COUNT
                && passedManifestCount == manifestCopy.size()
                && audienceCopy.size() == PackageCatalog.AUDIENCE_COUNT
                && readyAudienceCount == audienceCopy.size()
                && packageSectionCopy.size() == PackageCatalog.SECTION_COUNT
                && readyPackageSectionCount == packageSectionCopy.size()
                && acceptanceCopy.size() == PackageCatalog.ACCEPTANCE_COUNT
                && passedAcceptanceCount == acceptanceCopy.size()
                && ciMatrixCopy.size() == PackageCatalog.CI_COUNT
                && readOnlyCiMatrixCount == ciMatrixCopy.size()
                && boundaryLockCopy.size() == PackageCatalog.LOCK_COUNT
                && lockedBoundaryCount == boundaryLockCopy.size()
                && handoffChecklistCopy.size() == PackageCatalog.CHECKLIST_COUNT
                && readyChecklistCount == handoffChecklistCopy.size()
                && scorecardCopy.size() == PackageCatalog.SCORECARD_COUNT
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

  private static <T> int count(List<T> entries, Predicate<T> predicate) {
    return (int) entries.stream().filter(predicate).count();
  }
}
