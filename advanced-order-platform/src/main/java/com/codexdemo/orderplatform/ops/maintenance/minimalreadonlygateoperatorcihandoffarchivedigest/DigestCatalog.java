package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.EvidenceCounts.matching;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.BoundaryLock;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.ConsumerPacket;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.DigestSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.ReplayInstruction;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.SourceArchiveSnapshot;
import java.util.List;

final class DigestCatalog {

  static final int SOURCE_COUNT = 1;
  static final int DIGEST_COUNT = 6;
  static final int PACKET_COUNT = 4;
  static final int REPLAY_COUNT = 5;
  static final int LOCK_COUNT = 8;
  static final int SCORECARD_COUNT = 6;

  private DigestCatalog() {}

  static Evidence evidence(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          source) {
    var digests = digests(source);
    var packets = packets(source);
    var instructions = instructions(source);
    var locks = locks(source);
    return new Evidence(
        snapshots(source),
        digests,
        packets,
        instructions,
        locks,
        scorecard(source, digests, packets, instructions, locks));
  }

  private static List<SourceArchiveSnapshot> snapshots(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          source) {
    return List.of(
        new SourceArchiveSnapshot(
            source.version(),
            source.endpoint(),
            source.profile(),
            source.sourceHandoffVersion(),
            source.archiveState(),
            source.artifactVerificationCount(),
            source.operatorLaneVerificationCount(),
            source.ciBatchVerificationCount(),
            source.boundaryVerificationCount(),
            source.status()));
  }

  private static List<DigestSection> digests(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          source) {
    return List.of(
        digest(
            "source-handoff-snapshot",
            1,
            "passed".equals(source.status()) ? 1 : 0,
            source.sourceHandoffVersion()),
        digest(
            "artifact-verifications",
            source.artifactVerificationCount(),
            source.passedArtifactVerificationCount(),
            "artifacts="
                + source.passedArtifactVerificationCount()
                + "/"
                + source.artifactVerificationCount()),
        digest(
            "operator-lane-verifications",
            source.operatorLaneVerificationCount(),
            source.passedOperatorLaneVerificationCount(),
            "lanes="
                + source.passedOperatorLaneVerificationCount()
                + "/"
                + source.operatorLaneVerificationCount()),
        digest(
            "ci-batch-verifications",
            source.ciBatchVerificationCount(),
            source.passedCiBatchVerificationCount(),
            "ci-batches="
                + source.passedCiBatchVerificationCount()
                + "/"
                + source.ciBatchVerificationCount()),
        digest(
            "boundary-lock-verifications",
            source.boundaryVerificationCount(),
            source.passedBoundaryVerificationCount(),
            "boundaries="
                + source.lockedBoundaryVerificationCount()
                + "/"
                + source.boundaryVerificationCount()),
        digest(
            "source-archive-scorecard",
            source.scorecardEntryCount(),
            source.passedScorecardEntryCount(),
            "scorecard="
                + source.passedScorecardEntryCount()
                + "/"
                + source.scorecardEntryCount()));
  }

  private static DigestSection digest(
      String name, int sourceTotal, int sourcePassed, String evidence) {
    return new DigestSection(
        name,
        sourceTotal,
        sourcePassed,
        evidence,
        sourceTotal == sourcePassed ? "passed" : "blocked");
  }

  private static List<ConsumerPacket> packets(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          source) {
    boolean sourcePassed = "passed".equals(source.status());
    return List.of(
        packet("operator-runbook-extract", "operator", sourcePassed),
        packet("ci-batch-matrix", "ci", sourcePassed),
        packet("boundary-lock-manifest", "operator-ci", sourcePassed),
        packet("archive-scorecard-summary", "release-review", sourcePassed));
  }

  private static ConsumerPacket packet(String name, String owner, boolean ready) {
    return new ConsumerPacket(name, owner, true, true, ready, ready ? "passed" : "blocked");
  }

  private static List<ReplayInstruction> instructions(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          source) {
    return source.ciBatchVerifications().stream().map(DigestCatalog::instruction).toList();
  }

  private static ReplayInstruction instruction(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              .CiBatchVerification
          source) {
    boolean sourcePassed = "passed".equals(source.status());
    return new ReplayInstruction(
        source.order(),
        source.batch(),
        source.commandFamily(),
        sourcePassed,
        true,
        "reuse archived " + source.batch() + " evidence before any rerun",
        sourcePassed ? "passed" : "blocked");
  }

  private static List<BoundaryLock> locks(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          source) {
    return source.boundaryVerifications().stream().map(DigestCatalog::lock).toList();
  }

  private static BoundaryLock lock(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              .BoundaryVerification
          source) {
    return new BoundaryLock(
        source.code(),
        source.lockedBehavior(),
        source.locked(),
        source.archived() ? "archived boundary remains locked" : "missing archived boundary lock");
  }

  private static List<ScorecardEntry> scorecard(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          source,
      List<DigestSection> digests,
      List<ConsumerPacket> packets,
      List<ReplayInstruction> instructions,
      List<BoundaryLock> locks) {
    return List.of(
        score("source-archive-status", 1, "passed".equals(source.status()) ? 1 : 0),
        score(
            "digest-sections",
            DIGEST_COUNT,
            matching(digests, entry -> "passed".equals(entry.status()))),
        score("consumer-packets", PACKET_COUNT, matching(packets, ConsumerPacket::ready)),
        score(
            "read-only-replay-instructions",
            REPLAY_COUNT,
            matching(instructions, ReplayInstruction::readOnly)),
        score("boundary-locks", LOCK_COUNT, matching(locks, BoundaryLock::locked)),
        score(
            "source-archive-scorecard",
            source.scorecardEntryCount(),
            source.passedScorecardEntryCount()));
  }

  private static ScorecardEntry score(String name, int expected, int actual) {
    return new ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }

  record Evidence(
      List<SourceArchiveSnapshot> sourceArchiveSnapshots,
      List<DigestSection> digestSections,
      List<ConsumerPacket> consumerPackets,
      List<ReplayInstruction> replayInstructions,
      List<BoundaryLock> boundaryLocks,
      List<ScorecardEntry> scorecard) {
    Evidence {
      sourceArchiveSnapshots = List.copyOf(sourceArchiveSnapshots);
      digestSections = List.copyOf(digestSections);
      consumerPackets = List.copyOf(consumerPackets);
      replayInstructions = List.copyOf(replayInstructions);
      boundaryLocks = List.copyOf(boundaryLocks);
      scorecard = List.copyOf(scorecard);
    }
  }
}
