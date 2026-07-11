package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

public record OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean startsJavaService,
    boolean startsMiniKvService,
    boolean readsCredentialValue,
    boolean resolvesRawEndpointUrl,
    boolean managedAuditHttpAllowed,
    String endpoint,
    String profile,
    String sourcePlan,
    String requiredArchiveVerificationPlan,
    String recommendedOperatorPlan,
    String sourceArchiveVersion,
    String sourceArchiveEndpoint,
    String handoffState,
    int sourceArchiveSnapshotCount,
    int operatorLaneCount,
    int readyOperatorLaneCount,
    int ciBatchCount,
    int passedCiBatchCount,
    int boundaryLockCount,
    int lockedBoundaryCount,
    int scorecardEntryCount,
    int passedScorecardEntryCount,
    List<SourceArchiveSnapshot> sourceArchiveSnapshots,
    List<OperatorLane> operatorLanes,
    List<CiBatchPlan> ciBatches,
    List<BoundaryLock> boundaryLocks,
    List<ScorecardEntry> scorecard,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record SourceArchiveSnapshot(
      String version,
      String endpoint,
      String sourcePlan,
      String archiveState,
      int artifactVerificationCount,
      int readTargetVerificationCount,
      int gateCheckVerificationCount,
      int boundaryVerificationCount,
      String status) {}

  public record OperatorLane(
      String lane,
      int order,
      String owner,
      String sourceEvidence,
      boolean ready,
      String instruction) {}

  public record CiBatchPlan(
      String batch,
      int order,
      String commandFamily,
      String scope,
      boolean passed,
      boolean blocksNextBatch) {}

  public record BoundaryLock(String code, String lockedBehavior, boolean locked, String reason) {}

  public record ScorecardEntry(String name, int expected, int actual, String status) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
