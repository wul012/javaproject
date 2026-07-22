package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class HandoffCatalogTests {

  @Test
  void projectsSourceAndOrderedLanes() {
    var evidence = evidence();

    assertThat(evidence.sourceArchiveSnapshots())
        .singleElement()
        .satisfies(
            snapshot -> {
              assertThat(snapshot.version()).isEqualTo("Java v1337");
              assertThat(snapshot.status()).isEqualTo("passed");
            });
    assertThat(evidence.operatorLanes())
        .hasSize(4)
        .allSatisfy(lane -> assertThat(lane.ready()).isTrue())
        .extracting(lane -> lane.lane())
        .containsExactly("focused", "grouped", "build", "smoke");
  }

  @Test
  void projectsBatchesAndLockedBoundaries() {
    var evidence = evidence();

    assertThat(evidence.ciBatches())
        .hasSize(5)
        .allSatisfy(batch -> assertThat(batch.passed()).isTrue())
        .extracting(batch -> batch.commandFamily())
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
    assertThat(evidence.boundaryLocks())
        .hasSize(8)
        .allSatisfy(lock -> assertThat(lock.locked()).isTrue())
        .extracting(lock -> lock.code())
        .contains("no-java-autostart", "no-write-routing", "no-credential-value");
  }

  @Test
  void derivesCompletePassedScorecard() {
    var evidence = evidence();

    assertThat(evidence.scorecard())
        .hasSize(5)
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"));
  }

  @Test
  void evidenceOwnsAllFiveLists() {
    var original = evidence();
    var snapshots = new ArrayList<>(original.sourceArchiveSnapshots());
    var lanes = new ArrayList<>(original.operatorLanes());
    var batches = new ArrayList<>(original.ciBatches());
    var locks = new ArrayList<>(original.boundaryLocks());
    var scorecard = new ArrayList<>(original.scorecard());

    var evidence = new HandoffCatalog.Evidence(snapshots, lanes, batches, locks, scorecard);

    assertOwned(evidence.sourceArchiveSnapshots(), snapshots);
    assertOwned(evidence.operatorLanes(), lanes);
    assertOwned(evidence.ciBatches(), batches);
    assertOwned(evidence.boundaryLocks(), locks);
    assertOwned(evidence.scorecard(), scorecard);
  }

  private static HandoffCatalog.Evidence evidence() {
    return HandoffCatalog.evidence(HandoffTestData.sourceArchiveService().registry());
  }

  private static <T> void assertOwned(List<T> owned, List<T> source) {
    source.clear();
    assertThat(owned).isNotEmpty();
    assertThatThrownBy(() -> owned.add(owned.getFirst()))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
