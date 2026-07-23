package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.EvidenceCounts.matching;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.ArtifactVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.BoundaryVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.CiBatchVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.OperatorLaneVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.SourceHandoffSnapshot;
import java.util.List;

final class ArchiveCatalog {

  private ArchiveCatalog() {}

  static Evidence evidence(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    var artifacts = artifacts(source);
    var lanes = lanes(source);
    var batches = batches(source);
    var boundaries = boundaries(source);
    return new Evidence(
        snapshots(source),
        artifacts,
        lanes,
        batches,
        boundaries,
        scorecard(source, artifacts, lanes, batches, boundaries));
  }

  private static List<SourceHandoffSnapshot> snapshots(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return List.of(
        new SourceHandoffSnapshot(
            source.version(),
            source.endpoint(),
            source.profile(),
            source.sourcePlan(),
            source.requiredArchiveVerificationPlan(),
            source.recommendedOperatorPlan(),
            source.handoffState(),
            source.operatorLaneCount(),
            source.ciBatchCount(),
            source.boundaryLockCount(),
            source.status()));
  }

  private static List<ArtifactVerification> artifacts(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return List.of(
        artifact(
            "source-response-json",
            source.version(),
            source.endpoint(),
            "passed".equals(source.status())),
        artifact(
            "markdown-section-rendering",
            source.version(),
            "markdown-sections=" + source.markdownSections().size(),
            source.markdownSections().size()
                == OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                        .EXPECTED_MARKDOWN_SECTION_COUNT
                    - 1),
        artifact(
            "operator-lane-plan",
            source.version(),
            "operator-lanes=" + source.readyOperatorLaneCount() + "/" + source.operatorLaneCount(),
            source.readyOperatorLaneCount() == source.operatorLaneCount()),
        artifact(
            "ci-batch-plan",
            source.version(),
            "ci-batches=" + source.passedCiBatchCount() + "/" + source.ciBatchCount(),
            source.passedCiBatchCount() == source.ciBatchCount()),
        artifact(
            "boundary-lock-plan",
            source.version(),
            "boundary-locks=" + source.lockedBoundaryCount() + "/" + source.boundaryLockCount(),
            source.lockedBoundaryCount() == source.boundaryLockCount()),
        artifact(
            "source-scorecard-summary",
            source.version(),
            "scorecard=" + source.passedScorecardEntryCount() + "/" + source.scorecardEntryCount(),
            source.passedScorecardEntryCount() == source.scorecardEntryCount()));
  }

  private static ArtifactVerification artifact(
      String name, String producer, String evidence, boolean archived) {
    return new ArtifactVerification(
        name, producer, evidence, archived, archived ? "passed" : "blocked");
  }

  private static List<OperatorLaneVerification> lanes(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return source.operatorLanes().stream().map(ArchiveCatalog::lane).toList();
  }

  private static OperatorLaneVerification lane(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.OperatorLane source) {
    boolean archived = source.ready();
    return new OperatorLaneVerification(
        source.lane(),
        source.order(),
        source.owner(),
        source.ready(),
        archived,
        archived ? "passed" : "blocked");
  }

  private static List<CiBatchVerification> batches(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return source.ciBatches().stream().map(ArchiveCatalog::batch).toList();
  }

  private static CiBatchVerification batch(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.CiBatchPlan source) {
    boolean archived = source.passed();
    return new CiBatchVerification(
        source.batch(),
        source.order(),
        source.commandFamily(),
        source.passed(),
        archived,
        archived ? "passed" : "blocked");
  }

  private static List<BoundaryVerification> boundaries(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return source.boundaryLocks().stream().map(ArchiveCatalog::boundary).toList();
  }

  private static BoundaryVerification boundary(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.BoundaryLock source) {
    boolean archived = source.locked();
    return new BoundaryVerification(
        source.code(),
        source.lockedBehavior(),
        source.locked(),
        archived,
        archived ? "passed" : "blocked");
  }

  private static List<ScorecardEntry> scorecard(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source,
      List<ArtifactVerification> artifacts,
      List<OperatorLaneVerification> lanes,
      List<CiBatchVerification> batches,
      List<BoundaryVerification> boundaries) {
    return List.of(
        score("source-handoff-status", 1, "passed".equals(source.status()) ? 1 : 0),
        score(
            "artifact-verifications",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .EXPECTED_ARTIFACT_VERIFICATION_COUNT,
            matching(artifacts, entry -> "passed".equals(entry.status()))),
        score(
            "operator-lane-verifications",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .EXPECTED_OPERATOR_LANE_VERIFICATION_COUNT,
            matching(lanes, entry -> "passed".equals(entry.status()))),
        score(
            "ci-batch-verifications",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .EXPECTED_CI_BATCH_VERIFICATION_COUNT,
            matching(batches, entry -> "passed".equals(entry.status()))),
        score(
            "boundary-lock-verifications",
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
                .EXPECTED_BOUNDARY_VERIFICATION_COUNT,
            matching(boundaries, BoundaryVerification::locked)),
        score(
            "source-handoff-scorecard",
            source.scorecardEntryCount(),
            source.passedScorecardEntryCount()));
  }

  private static ScorecardEntry score(String name, int expected, int actual) {
    return new ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }

  record Evidence(
      List<SourceHandoffSnapshot> sourceHandoffSnapshots,
      List<ArtifactVerification> artifactVerifications,
      List<OperatorLaneVerification> operatorLaneVerifications,
      List<CiBatchVerification> ciBatchVerifications,
      List<BoundaryVerification> boundaryVerifications,
      List<ScorecardEntry> scorecard) {
    Evidence {
      sourceHandoffSnapshots = List.copyOf(sourceHandoffSnapshots);
      artifactVerifications = List.copyOf(artifactVerifications);
      operatorLaneVerifications = List.copyOf(operatorLaneVerifications);
      ciBatchVerifications = List.copyOf(ciBatchVerifications);
      boundaryVerifications = List.copyOf(boundaryVerifications);
      scorecard = List.copyOf(scorecard);
    }
  }
}
