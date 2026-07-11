package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import java.util.List;

public
record OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse(
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
    String sourceReleaseAcceptanceVersion,
    String sourceReleaseAcceptanceEndpoint,
    String sourceReleaseAcceptanceState,
    String archiveRegistryState,
    int sourceArchiveSnapshotCount,
    int artifactManifestCount,
    int passedArtifactManifestCount,
    int routePackageCount,
    int readyRoutePackageCount,
    int operatorPackCount,
    int readyOperatorPackCount,
    int ciAttestationCount,
    int passedCiAttestationCount,
    int boundarySealCount,
    int lockedBoundarySealCount,
    int retentionWindowCount,
    int readyRetentionWindowCount,
    int closeoutLedgerCount,
    int readyCloseoutLedgerCount,
    int scorecardEntryCount,
    int passedScorecardEntryCount,
    int markdownSectionCount,
    List<SourceArchiveSnapshot> sourceArchiveSnapshots,
    List<ArtifactManifestEntry> artifactManifest,
    List<RoutePackageEntry> routePackages,
    List<OperatorPackEntry> operatorPacks,
    List<CiAttestationEntry> ciAttestations,
    List<BoundarySealEntry> boundarySeals,
    List<RetentionWindowEntry> retentionWindows,
    List<CloseoutLedgerEntry> closeoutLedger,
    List<ScorecardEntry> scorecard,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record SourceArchiveSnapshot(
      String version,
      String endpoint,
      String profile,
      String releaseAcceptanceVersion,
      String releaseAcceptanceState,
      int readinessGateCount,
      int signoffLaneCount,
      int ciReplayLaneCount,
      int boundaryControlCount,
      String status) {}

  public record ArtifactManifestEntry(String name, String value, boolean required, String status) {}

  public record RoutePackageEntry(
      String receiver, String owner, String packet, boolean ready, String status) {}

  public record OperatorPackEntry(
      int order, String owner, String sourceEvidence, boolean ready, String status) {}

  public record CiAttestationEntry(
      int order,
      String batch,
      String commandFamily,
      boolean readOnly,
      boolean sourcePassed,
      String status) {}

  public record BoundarySealEntry(
      String code, String lockedBehavior, String auditEvidence, boolean locked, String status) {}

  public record RetentionWindowEntry(
      String name, String sourceEvidence, String retentionWindow, boolean ready, String status) {}

  public record CloseoutLedgerEntry(
      int order, String item, String owner, String evidence, boolean ready, String status) {}

  public record ScorecardEntry(String name, int expected, int actual, String status) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
