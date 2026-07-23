package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ArchiveCatalogTests {

  @Test
  void projectsSourceAndArtifacts() {
    var evidence = evidence();

    assertThat(evidence.sourceHandoffSnapshots())
        .singleElement()
        .satisfies(
            source -> {
              assertThat(source.version()).isEqualTo("Java v1352");
              assertThat(source.status()).isEqualTo("passed");
            });
    assertThat(evidence.artifactVerifications())
        .hasSize(6)
        .allSatisfy(artifact -> assertThat(artifact.status()).isEqualTo("passed"))
        .extracting(artifact -> artifact.artifact())
        .containsExactly(
            "source-response-json",
            "markdown-section-rendering",
            "operator-lane-plan",
            "ci-batch-plan",
            "boundary-lock-plan",
            "source-scorecard-summary");
  }

  @Test
  void projectsLanesAndBatchesInSourceOrder() {
    var evidence = evidence();

    assertThat(evidence.operatorLaneVerifications())
        .hasSize(4)
        .allSatisfy(lane -> assertThat(lane.status()).isEqualTo("passed"))
        .extracting(lane -> lane.lane())
        .containsExactly("focused", "grouped", "build", "smoke");
    assertThat(evidence.ciBatchVerifications())
        .hasSize(5)
        .allSatisfy(batch -> assertThat(batch.status()).isEqualTo("passed"))
        .extracting(batch -> batch.commandFamily())
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
  }

  @Test
  void projectsLockedBoundariesAndScorecard() {
    var evidence = evidence();

    assertThat(evidence.boundaryVerifications())
        .hasSize(8)
        .allSatisfy(
            boundary -> {
              assertThat(boundary.locked()).isTrue();
              assertThat(boundary.archived()).isTrue();
              assertThat(boundary.status()).isEqualTo("passed");
            })
        .extracting(boundary -> boundary.code())
        .contains("no-java-autostart", "no-write-routing", "no-managed-audit-http");
    assertThat(evidence.scorecard())
        .hasSize(6)
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"))
        .extracting(score -> score.name())
        .containsExactly(
            "source-handoff-status",
            "artifact-verifications",
            "operator-lane-verifications",
            "ci-batch-verifications",
            "boundary-lock-verifications",
            "source-handoff-scorecard");
  }

  @Test
  void evidenceOwnsAllSixLists() {
    var original = evidence();
    var sources = new ArrayList<>(original.sourceHandoffSnapshots());
    var artifacts = new ArrayList<>(original.artifactVerifications());
    var lanes = new ArrayList<>(original.operatorLaneVerifications());
    var batches = new ArrayList<>(original.ciBatchVerifications());
    var boundaries = new ArrayList<>(original.boundaryVerifications());
    var scorecard = new ArrayList<>(original.scorecard());

    var evidence =
        new ArchiveCatalog.Evidence(sources, artifacts, lanes, batches, boundaries, scorecard);

    assertOwned(evidence.sourceHandoffSnapshots(), sources);
    assertOwned(evidence.artifactVerifications(), artifacts);
    assertOwned(evidence.operatorLaneVerifications(), lanes);
    assertOwned(evidence.ciBatchVerifications(), batches);
    assertOwned(evidence.boundaryVerifications(), boundaries);
    assertOwned(evidence.scorecard(), scorecard);
  }

  private static ArchiveCatalog.Evidence evidence() {
    return ArchiveCatalog.evidence(ArchiveTestData.sourceHandoffService().registry());
  }

  private static <T> void assertOwned(List<T> owned, List<T> source) {
    source.clear();
    assertThat(owned).isNotEmpty();
    assertThatThrownBy(() -> owned.add(owned.getFirst()))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
