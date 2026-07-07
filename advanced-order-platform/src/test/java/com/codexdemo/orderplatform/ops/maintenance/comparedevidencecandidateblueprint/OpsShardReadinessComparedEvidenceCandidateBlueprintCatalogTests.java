package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogTests {

  @Test
  void catalogGroupsTwentyPreflightRulesIntoTenSectionsAndTenBlockers() {
    var response =
        new OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService().catalog();

    assertThat(response.version()).isEqualTo("Java v1060");
    assertThat(response.candidateSectionCount()).isEqualTo(10);
    assertThat(response.passedCandidateSectionCount()).isEqualTo(10);
    assertThat(response.blockerCount()).isEqualTo(10);
    assertThat(response.passedBlockerCount()).isEqualTo(10);
    assertThat(response.blockers())
        .allSatisfy(
            blocker -> {
              assertThat(blocker.enforcement()).isEqualTo("fail-closed");
              assertThat(blocker.rejectionCode()).startsWith("reject-candidate-blueprint");
            });
  }

  @Test
  void sectionCodesRemainUniqueAndCloseoutAware() {
    assertThat(OpsShardReadinessComparedEvidenceCandidateBlueprintSectionCatalog.allSections())
        .extracting(
            OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection::code)
        .doesNotHaveDuplicates()
        .contains("source-intake-readiness", "candidate-blueprint-closeout");
  }
}
