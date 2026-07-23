package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse;
import java.util.ArrayList;
import java.util.List;

final class DigestSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367";
  static final String REQUIRED_ARCHIVE_VERIFICATION_PLAN = "Node v368";
  static final String OPERATOR_HANDOFF_PLAN = "Node v369";
  static final String DIGEST_STATE =
      "minimal-read-only-gate-operator-ci-handoff-archive-digest-ready";
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 6;

  private DigestSupport() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
      response(
          String version,
          String endpoint,
          String profile,
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              sourceArchive,
          DigestCatalog.Evidence evidence,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                      .MarkdownSection>
              markdownSections) {
    var sourceArchiveCopy = evidence.sourceArchiveSnapshots();
    var digestSectionCopy = evidence.digestSections();
    var consumerPacketCopy = evidence.consumerPackets();
    var replayInstructionCopy = evidence.replayInstructions();
    var boundaryLockCopy = evidence.boundaryLocks();
    var scorecardCopy = evidence.scorecard();
    var markdownSectionCopy = List.copyOf(markdownSections);
    int passedDigestSectionCount = countDigestSections(digestSectionCopy);
    int readyConsumerPacketCount = countReadyConsumerPackets(consumerPacketCopy);
    int readOnlyReplayInstructionCount = countReadOnlyReplayInstructions(replayInstructionCopy);
    int lockedBoundaryCount =
        (int)
            boundaryLockCopy.stream()
                .filter(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                            .BoundaryLock
                        ::locked)
                .count();
    int passedScorecardCount = countScorecard(scorecardCopy);

    List<String> checks = new ArrayList<>();
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-source-plan-" + SOURCE_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-required-archive-"
            + REQUIRED_ARCHIVE_VERIFICATION_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-operator-plan-"
            + OPERATOR_HANDOFF_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-source-archive-version-"
            + sourceArchive.version());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-source-archive-status-"
            + sourceArchive.status());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-source-archive-count-"
            + sourceArchiveCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-section-count-"
            + digestSectionCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-passed-section-count-"
            + passedDigestSectionCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-packet-count-"
            + consumerPacketCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-ready-consumer-packet-count-"
            + readyConsumerPacketCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-replay-instruction-count-"
            + replayInstructionCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-read-only-replay-count-"
            + readOnlyReplayInstructionCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-boundary-lock-count-"
            + boundaryLockCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-locked-boundary-count-"
            + lockedBoundaryCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-scorecard-count-"
            + scorecardCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-passed-scorecard-count-"
            + passedScorecardCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-markdown-section-count-"
            + markdownSectionCopy.size());
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-digest-no-upstream-autostart");
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-digest-no-write-routing");
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-digest-no-secret-value");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-archive-digest-no-raw-endpoint-resolution");
    checks.add("minimal-read-only-gate-operator-ci-handoff-archive-digest-no-managed-audit-http");

    String status =
        "passed".equals(sourceArchive.status())
                && sourceArchiveCopy.size() == DigestCatalog.SOURCE_COUNT
                && digestSectionCopy.size() == DigestCatalog.DIGEST_COUNT
                && passedDigestSectionCount == digestSectionCopy.size()
                && consumerPacketCopy.size() == DigestCatalog.PACKET_COUNT
                && readyConsumerPacketCount == consumerPacketCopy.size()
                && replayInstructionCopy.size() == DigestCatalog.REPLAY_COUNT
                && readOnlyReplayInstructionCount == replayInstructionCopy.size()
                && boundaryLockCopy.size() == DigestCatalog.LOCK_COUNT
                && lockedBoundaryCount == boundaryLockCopy.size()
                && scorecardCopy.size() == DigestCatalog.SCORECARD_COUNT
                && passedScorecardCount == scorecardCopy.size()
                && markdownSectionCopy.size() == EXPECTED_MARKDOWN_SECTION_COUNT
            ? "passed"
            : "blocked";

    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse(
        PROJECT,
        version,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        SOURCE_PLAN,
        REQUIRED_ARCHIVE_VERIFICATION_PLAN,
        OPERATOR_HANDOFF_PLAN,
        sourceArchive.version(),
        sourceArchive.endpoint(),
        sourceArchive.archiveState(),
        DIGEST_STATE,
        sourceArchiveCopy.size(),
        digestSectionCopy.size(),
        passedDigestSectionCount,
        consumerPacketCopy.size(),
        readyConsumerPacketCount,
        replayInstructionCopy.size(),
        readOnlyReplayInstructionCount,
        boundaryLockCopy.size(),
        lockedBoundaryCount,
        scorecardCopy.size(),
        passedScorecardCount,
        markdownSectionCopy.size(),
        sourceArchiveCopy,
        digestSectionCopy,
        consumerPacketCopy,
        replayInstructionCopy,
        boundaryLockCopy,
        scorecardCopy,
        markdownSectionCopy,
        List.copyOf(checks),
        status);
  }

  private static int countDigestSections(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                  .DigestSection>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }

  private static int countReadyConsumerPackets(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                  .ConsumerPacket>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                        .ConsumerPacket
                    ::ready)
            .count();
  }

  private static int countReadOnlyReplayInstructions(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                  .ReplayInstruction>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                        .ReplayInstruction
                    ::readOnly)
            .count();
  }

  private static int countScorecard(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                  .ScorecardEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }
}
