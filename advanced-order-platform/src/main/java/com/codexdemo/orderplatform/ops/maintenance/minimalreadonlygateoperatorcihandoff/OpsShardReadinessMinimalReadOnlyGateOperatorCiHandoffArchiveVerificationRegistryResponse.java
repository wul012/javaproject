package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

public
record OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse(
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
    String operatorHandoffPlan,
    String sourceHandoffVersion,
    String sourceHandoffEndpoint,
    String sourceHandoffState,
    String archiveState,
    int sourceHandoffSnapshotCount,
    int artifactVerificationCount,
    int passedArtifactVerificationCount,
    int operatorLaneVerificationCount,
    int passedOperatorLaneVerificationCount,
    int ciBatchVerificationCount,
    int passedCiBatchVerificationCount,
    int boundaryVerificationCount,
    int lockedBoundaryVerificationCount,
    int passedBoundaryVerificationCount,
    int scorecardEntryCount,
    int passedScorecardEntryCount,
    int markdownSectionCount,
    List<SourceHandoffSnapshot> sourceHandoffSnapshots,
    List<ArtifactVerification> artifactVerifications,
    List<OperatorLaneVerification> operatorLaneVerifications,
    List<CiBatchVerification> ciBatchVerifications,
    List<BoundaryVerification> boundaryVerifications,
    List<ScorecardEntry> scorecard,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record SourceHandoffSnapshot(
      String version,
      String endpoint,
      String profile,
      String sourcePlan,
      String requiredArchiveVerificationPlan,
      String operatorHandoffPlan,
      String handoffState,
      int operatorLaneCount,
      int ciBatchCount,
      int boundaryLockCount,
      String status) {}

  public record ArtifactVerification(
      String artifact, String producer, String evidence, boolean archived, String status) {}

  public record OperatorLaneVerification(
      String lane, int order, String owner, boolean sourceReady, boolean archived, String status) {}

  public record CiBatchVerification(
      String batch,
      int order,
      String commandFamily,
      boolean sourcePassed,
      boolean archived,
      String status) {}

  public record BoundaryVerification(
      String code, String lockedBehavior, boolean locked, boolean archived, String status) {}

  public record ScorecardEntry(String name, int expected, int actual, String status) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
