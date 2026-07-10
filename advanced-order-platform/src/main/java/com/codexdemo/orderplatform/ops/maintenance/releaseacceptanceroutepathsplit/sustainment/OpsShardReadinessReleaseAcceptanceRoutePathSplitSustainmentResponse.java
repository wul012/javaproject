package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import java.util.List;

public record OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String sourcePlan,
    String nodeParallelPlan,
    String sourceCloseoutVersion,
    String sourceCloseoutEndpoint,
    String sourceSplitVersion,
    String sourceSplitEndpoint,
    String endpoint,
    String profile,
    int sourceSnapshotCount,
    int ownershipRuleCount,
    int driftGuardCount,
    int boundaryGuardCount,
    int ciGateCount,
    int consumerHandoffCount,
    int scorecardEntryCount,
    int markdownSectionCount,
    List<SourceSnapshot> sourceSnapshots,
    List<OwnershipRule> ownershipRules,
    List<DriftGuard> driftGuards,
    List<BoundaryGuard> boundaryGuards,
    List<CiGate> ciGates,
    List<ConsumerHandoff> consumerHandoffs,
    List<ScorecardEntry> scorecard,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record SourceSnapshot(
      String source, String version, String endpoint, String status, String ownership) {}

  public record OwnershipRule(
      String component, String owner, String rule, String landingZone, boolean enforced) {}

  public record DriftGuard(String guard, String signal, String expected, boolean locked) {}

  public record BoundaryGuard(String boundary, boolean locked, String evidence) {}

  public record CiGate(String gate, String command, String scope, boolean required) {}

  public record ConsumerHandoff(
      String consumer, String handoffRule, String expectedUse, boolean ready) {}

  public record ScorecardEntry(String category, boolean passed, String detail) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
