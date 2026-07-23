package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PackageCatalogTests {

  @Test
  void projectsSourceManifestAndAudience() {
    var evidence = evidence();

    assertThat(evidence.sourceDigests())
        .singleElement()
        .satisfies(
            source -> {
              assertThat(source.version()).isEqualTo("Java v1402");
              assertThat(source.status()).isEqualTo("passed");
            });
    assertThat(evidence.manifest())
        .extracting(entry -> entry.name())
        .containsExactly(
            "source-digest-version",
            "source-archive-version",
            "source-digest-state",
            "source-endpoint",
            "source-profile");
    assertThat(evidence.audiences())
        .hasSize(4)
        .allSatisfy(audience -> assertThat(audience.ready()).isTrue());
    assertThat(evidence.sections())
        .hasSize(5)
        .allSatisfy(section -> assertThat(section.ready()).isTrue());
  }

  @Test
  void projectsAcceptanceAndCiOrder() {
    var evidence = evidence();

    assertThat(evidence.criteria())
        .hasSize(5)
        .allSatisfy(criterion -> assertThat(criterion.passed()).isTrue());
    assertThat(evidence.ciMatrix())
        .hasSize(5)
        .allSatisfy(
            entry -> {
              assertThat(entry.readOnly()).isTrue();
              assertThat(entry.sourcePassed()).isTrue();
              assertThat(entry.status()).isEqualTo("passed");
            })
        .extracting(entry -> entry.commandFamily())
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
  }

  @Test
  void projectsLocksChecklistAndScorecard() {
    var evidence = evidence();

    assertThat(evidence.locks())
        .hasSize(8)
        .allSatisfy(lock -> assertThat(lock.locked()).isTrue())
        .extracting(lock -> lock.code())
        .contains("no-java-autostart", "no-mini-kv-autostart", "no-write-routing");
    assertThat(evidence.checklist())
        .hasSize(5)
        .allSatisfy(item -> assertThat(item.ready()).isTrue());
    assertThat(evidence.scorecard())
        .hasSize(8)
        .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"));
  }

  @Test
  void evidenceOwnsAllNineLists() {
    var original = evidence();
    var sources = new ArrayList<>(original.sourceDigests());
    var manifest = new ArrayList<>(original.manifest());
    var audiences = new ArrayList<>(original.audiences());
    var sections = new ArrayList<>(original.sections());
    var criteria = new ArrayList<>(original.criteria());
    var ciMatrix = new ArrayList<>(original.ciMatrix());
    var locks = new ArrayList<>(original.locks());
    var checklist = new ArrayList<>(original.checklist());
    var scorecard = new ArrayList<>(original.scorecard());

    var evidence =
        new PackageCatalog.Evidence(
            sources, manifest, audiences, sections, criteria, ciMatrix, locks, checklist,
            scorecard);

    assertOwned(evidence.sourceDigests(), sources);
    assertOwned(evidence.manifest(), manifest);
    assertOwned(evidence.audiences(), audiences);
    assertOwned(evidence.sections(), sections);
    assertOwned(evidence.criteria(), criteria);
    assertOwned(evidence.ciMatrix(), ciMatrix);
    assertOwned(evidence.locks(), locks);
    assertOwned(evidence.checklist(), checklist);
    assertOwned(evidence.scorecard(), scorecard);
  }

  private static PackageCatalog.Evidence evidence() {
    return PackageCatalog.evidence(ConsumerPackageTestData.sourceDigestService().registry());
  }

  private static <T> void assertOwned(List<T> owned, List<T> source) {
    source.clear();
    assertThat(owned).isNotEmpty();
    assertThatThrownBy(() -> owned.add(owned.getFirst()))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
