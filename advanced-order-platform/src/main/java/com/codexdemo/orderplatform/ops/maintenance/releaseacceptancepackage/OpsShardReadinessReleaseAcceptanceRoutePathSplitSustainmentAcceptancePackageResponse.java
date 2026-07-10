package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

public record OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String sourcePlan,
    String nodeParallelPlan,
    String sourceSustainmentVersion,
    String sourceSustainmentEndpoint,
    String sourceCloseoutVersion,
    String sourceSplitVersion,
    String endpoint,
    String profile,
    int sourceSnapshotCount,
    int lineageEntryCount,
    int decisionRecordCount,
    int archiveItemCount,
    int reviewItemCount,
    int ciEvidenceCount,
    int runtimeBoundaryCount,
    int nextChangeRuleCount,
    int scorecardEntryCount,
    int markdownSectionCount,
    List<SourceSnapshot> sourceSnapshots,
    List<VersionLineage> lineage,
    List<DecisionRecord> decisions,
    List<ArchiveItem> archiveItems,
    List<ReviewItem> reviewItems,
    List<CiEvidence> ciEvidence,
    List<RuntimeBoundary> runtimeBoundaries,
    List<NextChangeRule> nextChangeRules,
    List<ScorecardEntry> scorecard,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record SourceSnapshot(
      String source, String version, String endpoint, String status, String profile) {}

  public record VersionLineage(String stage, String version, String endpoint, String status) {}

  public record DecisionRecord(String decision, String owner, String rationale, boolean accepted) {}

  public record ArchiveItem(String artifact, String retention, String evidence, boolean ready) {}

  public record ReviewItem(String reviewer, String checklist, String expectation, boolean passed) {}

  public record CiEvidence(String gate, String command, String result, boolean passed) {}

  public record RuntimeBoundary(String boundary, String policy, String evidence, boolean locked) {}

  public record NextChangeRule(
      String trigger, String landingZone, String reviewer, boolean ready) {}

  public record ScorecardEntry(String category, boolean passed, String detail) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
