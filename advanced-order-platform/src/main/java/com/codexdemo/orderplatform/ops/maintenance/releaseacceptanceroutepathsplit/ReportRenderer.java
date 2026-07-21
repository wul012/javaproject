package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<SourceSnapshot> sources,
      List<RoutePathEntry> routes,
      List<CompatibilityCheck> checks,
      List<BoundaryGuard> guards,
      List<ConsumerHandoff> handoffs,
      List<ScorecardEntry> scorecard) {
    return List.of(
        section("Source Handoff", sourceLines(sources)),
        section("Route Path Split", routeLines(routes)),
        section("Compatibility Checks", checkLines(checks)),
        section("Boundary Guards", guardLines(guards)),
        section("Consumer Handoffs", handoffLines(handoffs)),
        section("Scorecard", scorecardLines(scorecard)));
  }

  private static List<String> sourceLines(List<SourceSnapshot> sources) {
    return sources.stream()
        .map(
            source ->
                "- " + source.source() + " " + source.version() + " status=" + source.status())
        .toList();
  }

  private static List<String> routeLines(List<RoutePathEntry> routes) {
    return routes.stream()
        .map(
            route ->
                "- "
                    + route.symbol()
                    + " "
                    + route.path()
                    + " compatible="
                    + route.legacyCompatible())
        .toList();
  }

  private static List<String> checkLines(List<CompatibilityCheck> checks) {
    return checks.stream()
        .map(check -> "- " + check.check() + " matched=" + check.matched())
        .toList();
  }

  private static List<String> guardLines(List<BoundaryGuard> guards) {
    return guards.stream()
        .map(guard -> "- " + guard.boundary() + " locked=" + guard.locked())
        .toList();
  }

  private static List<String> handoffLines(List<ConsumerHandoff> handoffs) {
    return handoffs.stream()
        .map(handoff -> "- " + handoff.consumer() + " status=" + handoff.status())
        .toList();
  }

  private static List<String> scorecardLines(List<ScorecardEntry> scorecard) {
    return scorecard.stream()
        .map(
            entry ->
                "- " + entry.category() + " passed=" + entry.passed() + " detail=" + entry.detail())
        .toList();
  }

  private static MarkdownSection section(String heading, List<String> lines) {
    return new MarkdownSection(heading, List.copyOf(lines));
  }
}
