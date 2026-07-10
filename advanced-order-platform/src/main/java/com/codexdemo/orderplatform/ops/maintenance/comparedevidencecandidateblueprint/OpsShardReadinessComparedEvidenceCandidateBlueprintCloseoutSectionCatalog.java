package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutSectionCatalog {

  private OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutSectionCatalog() {}

  static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection>
      closeoutSections() {
    return List.of(
        OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section(
            "exclusion-boundary",
            "Node v1360",
            "closeout",
            "secret exclusion, synthetic exclusion, runtime exclusion, sibling mutation exclusion",
            "archive closeout owner",
            OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.EXCLUSION_CLOSEOUT,
            "block-missing-exclusion-boundary"),
        OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section(
            "candidate-blueprint-closeout",
            "Node v1361",
            "closeout",
            "reviewer traceability, candidate blueprint closeout",
            "archive closeout owner",
            OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.EXCLUSION_CLOSEOUT,
            "block-missing-candidate-blueprint-closeout"));
  }
}
