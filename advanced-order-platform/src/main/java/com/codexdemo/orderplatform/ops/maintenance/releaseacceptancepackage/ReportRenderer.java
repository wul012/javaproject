package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.ArchiveItem;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.CiEvidence;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.DecisionRecord;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.NextChangeRule;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.ReviewItem;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.RuntimeBoundary;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.SourceSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.VersionLineage;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(PackageCatalog.Evidence evidence) {
    return List.of(
        source(evidence.sourceSnapshots()),
        lineage(evidence.lineage()),
        decisions(evidence.decisions()),
        archive(evidence.archiveItems()),
        review(evidence.reviewItems()),
        ci(evidence.ciEvidence()),
        boundaries(evidence.runtimeBoundaries()),
        nextChanges(evidence.nextChangeRules()),
        scorecard(evidence.scorecard()));
  }

  private static MarkdownSection source(List<SourceSnapshot> entries) {
    return MarkdownSections.mapped(
        "Source Sustainment",
        entries,
        entry ->
            "- "
                + entry.source()
                + " "
                + entry.version()
                + " status="
                + entry.status()
                + " profile="
                + entry.profile(),
        MarkdownSection::new);
  }

  private static MarkdownSection lineage(List<VersionLineage> entries) {
    return MarkdownSections.mapped(
        "Version Lineage",
        entries,
        entry -> "- " + entry.stage() + " " + entry.version() + " status=" + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection decisions(List<DecisionRecord> entries) {
    return MarkdownSections.mapped(
        "Acceptance Decisions",
        entries,
        entry ->
            "- " + entry.decision() + " owner=" + entry.owner() + " accepted=" + entry.accepted(),
        MarkdownSection::new);
  }

  private static MarkdownSection archive(List<ArchiveItem> entries) {
    return MarkdownSections.mapped(
        "Archive Items",
        entries,
        entry ->
            "- " + entry.artifact() + " retention=" + entry.retention() + " ready=" + entry.ready(),
        MarkdownSection::new);
  }

  private static MarkdownSection review(List<ReviewItem> entries) {
    return MarkdownSections.mapped(
        "Review Checklist",
        entries,
        entry ->
            "- "
                + entry.reviewer()
                + " checklist="
                + entry.checklist()
                + " passed="
                + entry.passed(),
        MarkdownSection::new);
  }

  private static MarkdownSection ci(List<CiEvidence> entries) {
    return MarkdownSections.mapped(
        "CI Evidence",
        entries,
        entry -> "- " + entry.gate() + " result=" + entry.result() + " passed=" + entry.passed(),
        MarkdownSection::new);
  }

  private static MarkdownSection boundaries(List<RuntimeBoundary> entries) {
    return MarkdownSections.mapped(
        "Runtime Boundaries",
        entries,
        entry ->
            "- " + entry.boundary() + " policy=" + entry.policy() + " locked=" + entry.locked(),
        MarkdownSection::new);
  }

  private static MarkdownSection nextChanges(List<NextChangeRule> entries) {
    return MarkdownSections.mapped(
        "Next Change Rules",
        entries,
        entry ->
            "- "
                + entry.trigger()
                + " landing="
                + entry.landingZone()
                + " reviewer="
                + entry.reviewer()
                + " ready="
                + entry.ready(),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.mapped(
        "Acceptance Scorecard",
        entries,
        entry ->
            "- " + entry.category() + " passed=" + entry.passed() + " detail=" + entry.detail(),
        MarkdownSection::new);
  }
}
