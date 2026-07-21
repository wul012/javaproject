package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.BoundaryGuard;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ConsumerHandoff;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.OwnershipRule;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.SourceSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<SourceSnapshot> snapshots,
      List<OwnershipRule> ownership,
      List<DriftGuard> drift,
      List<BoundaryGuard> boundaries,
      List<CiGate> ciGates,
      List<ConsumerHandoff> consumers,
      List<ScorecardEntry> scorecard) {
    return List.of(
        source(snapshots),
        ownership(ownership),
        drift(drift),
        boundaries(boundaries),
        ci(ciGates),
        consumers(consumers),
        scorecard(scorecard));
  }

  private static MarkdownSection source(List<SourceSnapshot> entries) {
    return MarkdownSections.mapped(
        "Source Closeout",
        entries,
        entry ->
            "- "
                + entry.source()
                + " "
                + entry.version()
                + " status="
                + entry.status()
                + " owner="
                + entry.ownership(),
        MarkdownSection::new);
  }

  private static MarkdownSection ownership(List<OwnershipRule> entries) {
    return MarkdownSections.mapped(
        "Ownership Rules",
        entries,
        entry ->
            "- "
                + entry.component()
                + " owner="
                + entry.owner()
                + " landing="
                + entry.landingZone()
                + " enforced="
                + entry.enforced(),
        MarkdownSection::new);
  }

  private static MarkdownSection drift(List<DriftGuard> entries) {
    return MarkdownSections.mapped(
        "Drift Guards",
        entries,
        entry ->
            "- "
                + entry.guard()
                + " signal="
                + entry.signal()
                + " expected="
                + entry.expected()
                + " locked="
                + entry.locked(),
        MarkdownSection::new);
  }

  private static MarkdownSection boundaries(List<BoundaryGuard> entries) {
    return MarkdownSections.mapped(
        "Boundary Guards",
        entries,
        entry ->
            "- " + entry.boundary() + " locked=" + entry.locked() + " evidence=" + entry.evidence(),
        MarkdownSection::new);
  }

  private static MarkdownSection ci(List<CiGate> entries) {
    return MarkdownSections.mapped(
        "CI Gates",
        entries,
        entry -> "- " + entry.gate() + " scope=" + entry.scope() + " required=" + entry.required(),
        MarkdownSection::new);
  }

  private static MarkdownSection consumers(List<ConsumerHandoff> entries) {
    return MarkdownSections.mapped(
        "Consumer Handoffs",
        entries,
        entry ->
            "- " + entry.consumer() + " use=" + entry.expectedUse() + " ready=" + entry.ready(),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.mapped(
        "Sustainment Scorecard",
        entries,
        entry ->
            "- " + entry.category() + " passed=" + entry.passed() + " detail=" + entry.detail(),
        MarkdownSection::new);
  }
}
