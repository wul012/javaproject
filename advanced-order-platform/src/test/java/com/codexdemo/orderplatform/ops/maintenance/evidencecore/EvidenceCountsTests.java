package com.codexdemo.orderplatform.ops.maintenance.evidencecore;

import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.EvidenceCounts.matching;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class EvidenceCountsTests {

  @Test
  void countsOnlyMatchingEntries() {
    var entries = List.of("passed", "blocked", "passed");

    assertThat(matching(entries, "passed"::equals)).isEqualTo(2);
    assertThat(entries).containsExactly("passed", "blocked", "passed");
  }
}
