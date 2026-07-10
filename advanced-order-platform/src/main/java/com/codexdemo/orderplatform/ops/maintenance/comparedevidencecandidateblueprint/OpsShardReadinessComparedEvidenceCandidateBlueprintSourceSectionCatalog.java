package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateBlueprintSourceSectionCatalog {

  private OpsShardReadinessComparedEvidenceCandidateBlueprintSourceSectionCatalog() {}

  static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection>
      sourceSections() {
    return List.of(
        OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section(
            "source-intake-readiness",
            "Node v1352",
            "source",
            "source intake readiness, evidence artifact shape",
            "operator evidence reviewer",
            OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.SOURCE_ARTIFACT,
            "block-missing-source-intake-readiness"),
        OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section(
            "operator-provenance",
            "Node v1353",
            "source",
            "operator provenance, source evidence handle",
            "operator evidence reviewer",
            OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.SOURCE_ARTIFACT,
            "block-missing-operator-provenance"),
        OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section(
            "manual-submission-reference",
            "Node v1354",
            "source",
            "manual submission reference, operator value handle",
            "operator evidence reviewer",
            OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.SOURCE_ARTIFACT,
            "block-missing-manual-submission-reference"));
  }
}
