package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DigestCatalogTests {

  @Test
  void projectsSourceAndDigestSections() {
    var evidence = evidence();

    assertThat(evidence.sourceArchiveSnapshots())
        .singleElement()
        .satisfies(
            source -> {
              assertThat(source.version()).isEqualTo("Java v1377");
              assertThat(source.status()).isEqualTo("passed");
            });
    assertThat(evidence.digestSections())
        .hasSize(6)
        .allSatisfy(digest -> assertThat(digest.status()).isEqualTo("passed"))
        .extracting(digest -> digest.name())
        .containsExactly(
            "source-handoff-snapshot",
            "artifact-verifications",
            "operator-lane-verifications",
            "ci-batch-verifications",
            "boundary-lock-verifications",
            "source-archive-scorecard");
  }

  @Test
  void projectsPacketsAndReplayOrder() {
    var evidence = evidence();

    assertThat(evidence.consumerPackets())
        .hasSize(4)
        .allSatisfy(
            packet -> {
              assertThat(packet.includesDigest()).isTrue();
              assertThat(packet.includesBoundaryLocks()).isTrue();
              assertThat(packet.ready()).isTrue();
              assertThat(packet.status()).isEqualTo("passed");
            })
        .extracting(packet -> packet.packet())
        .containsExactly(
            "operator-runbook-extract",
            "ci-batch-matrix",
            "boundary-lock-manifest",
            "archive-scorecard-summary");
    assertThat(evidence.replayInstructions())
        .hasSize(5)
        .allSatisfy(
            instruction -> {
              assertThat(instruction.readOnly()).isTrue();
              assertThat(instruction.sourcePassed()).isTrue();
              assertThat(instruction.status()).isEqualTo("passed");
              assertThat(instruction.instruction()).contains("reuse archived");
            })
        .extracting(instruction -> instruction.commandFamily())
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
  }

  @Test
  void projectsLockedBoundariesAndScorecard() {
    var evidence = evidence();

    assertThat(evidence.boundaryLocks())
        .hasSize(8)
        .allSatisfy(
            lock -> {
              assertThat(lock.locked()).isTrue();
              assertThat(lock.reason()).isEqualTo("archived boundary remains locked");
            });
    assertThat(evidence.scorecard())
        .hasSize(6)
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"))
        .extracting(score -> score.name())
        .containsExactly(
            "source-archive-status",
            "digest-sections",
            "consumer-packets",
            "read-only-replay-instructions",
            "boundary-locks",
            "source-archive-scorecard");
  }

  @Test
  void evidenceOwnsAllSixLists() {
    var original = evidence();
    var sources = new ArrayList<>(original.sourceArchiveSnapshots());
    var digests = new ArrayList<>(original.digestSections());
    var packets = new ArrayList<>(original.consumerPackets());
    var instructions = new ArrayList<>(original.replayInstructions());
    var locks = new ArrayList<>(original.boundaryLocks());
    var scorecard = new ArrayList<>(original.scorecard());

    var evidence =
        new DigestCatalog.Evidence(sources, digests, packets, instructions, locks, scorecard);

    assertOwned(evidence.sourceArchiveSnapshots(), sources);
    assertOwned(evidence.digestSections(), digests);
    assertOwned(evidence.consumerPackets(), packets);
    assertOwned(evidence.replayInstructions(), instructions);
    assertOwned(evidence.boundaryLocks(), locks);
    assertOwned(evidence.scorecard(), scorecard);
  }

  private static DigestCatalog.Evidence evidence() {
    return DigestCatalog.evidence(ArchiveDigestTestData.sourceArchiveService().registry());
  }

  private static <T> void assertOwned(List<T> owned, List<T> source) {
    source.clear();
    assertThat(owned).isNotEmpty();
    assertThatThrownBy(() -> owned.add(owned.getFirst()))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
