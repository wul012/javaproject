package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint;

import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog {

  private OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog() {}

  static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker>
      allBlockers() {
    return OpsShardReadinessComparedEvidenceCandidateBlueprintSectionCatalog.allSections().stream()
        .map(
            section ->
                OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.blocker(
                    section.blockerCode(),
                    section.section(),
                    "Block blueprint readiness when " + section.requiredFields() + " are absent.",
                    "reject-candidate-blueprint-" + section.code()))
        .toList();
  }

  static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker>
      sourceBlockers() {
    return allBlockers().subList(0, 3);
  }

  static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker>
      comparisonBlockers() {
    return allBlockers().subList(3, 6);
  }

  static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker>
      policyBlockers() {
    return allBlockers().subList(6, 8);
  }

  static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateBlocker>
      closeoutBlockers() {
    return allBlockers().subList(8, 10);
  }
}
