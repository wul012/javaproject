package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import java.util.List;

public record OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse(
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
    String archiveVerificationPlan,
    String operatorHandoffPlan,
    String sourceArchiveVersion,
    String sourceArchiveEndpoint,
    String sourceArchiveState,
    String handoffState,
    int sourceArchiveSnapshotCount,
    int verificationRequirementCount,
    int passedVerificationRequirementCount,
    int artifactCrossCheckCount,
    int passedArtifactCrossCheckCount,
    int routeHandoffCount,
    int readyRouteHandoffCount,
    int operatorInstructionCount,
    int readyOperatorInstructionCount,
    int ciProofCount,
    int passedCiProofCount,
    int boundaryGuardCount,
    int lockedBoundaryGuardCount,
    int retentionGuardCount,
    int readyRetentionGuardCount,
    int closeoutHandoffCount,
    int readyCloseoutHandoffCount,
    int scorecardEntryCount,
    int passedScorecardEntryCount,
    int markdownSectionCount,
    List<SourceArchiveSnapshot> sourceArchiveSnapshots,
    List<VerificationRequirement> verificationRequirements,
    List<ArtifactCrossCheck> artifactCrossChecks,
    List<RouteHandoff> routeHandoffs,
    List<OperatorInstruction> operatorInstructions,
    List<CiProof> ciProofs,
    List<BoundaryGuard> boundaryGuards,
    List<RetentionGuard> retentionGuards,
    List<CloseoutHandoff> closeoutHandoffs,
    List<ScorecardEntry> scorecard,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record SourceArchiveSnapshot(
      String version,
      String endpoint,
      String profile,
      String archiveRegistryState,
      int artifactManifestCount,
      int routePackageCount,
      int operatorPackCount,
      int ciAttestationCount,
      int boundarySealCount,
      String status) {}

  public record VerificationRequirement(
      String code, String evidence, int expected, int actual, boolean passed, String status) {}

  public record ArtifactCrossCheck(
      String name, String sourceValue, String expectedEvidence, boolean matched, String status) {}

  public record RouteHandoff(
      String receiver, String owner, String packet, boolean ready, String status) {}

  public record OperatorInstruction(
      int order,
      String owner,
      String sourceEvidence,
      String instruction,
      boolean ready,
      String status) {}

  public record CiProof(
      int order,
      String batch,
      String commandFamily,
      boolean readOnly,
      boolean sourcePassed,
      String status) {}

  public record BoundaryGuard(
      String code, String lockedBehavior, String auditEvidence, boolean locked, String status) {}

  public record RetentionGuard(
      String name, String sourceEvidence, String retentionWindow, boolean ready, String status) {}

  public record CloseoutHandoff(
      int order, String item, String owner, String evidence, boolean ready, String status) {}

  public record ScorecardEntry(String name, int expected, int actual, String status) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
