package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonSectionCatalog {

  private OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonSectionCatalog() {}

  static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection>
      comparisonSections() {
    return List.of(
        OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section(
            "offline-comparison-result",
            "Node v1355",
            "comparison",
            "offline comparison result, mismatch summary",
            "manual comparison reviewer",
            OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.CATALOG,
            "block-missing-offline-comparison-result"),
        OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section(
            "identity-digest-lineage",
            "Node v1356",
            "comparison",
            "identity binding, digest lineage",
            "identity digest reviewer",
            OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.IDENTITY_DIGEST,
            "block-missing-identity-digest-lineage"),
        OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section(
            "signature-envelope-metadata",
            "Node v1357",
            "comparison",
            "signature envelope metadata, detached signature observation",
            "identity digest reviewer",
            OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.IDENTITY_DIGEST,
            "block-missing-signature-envelope-metadata"));
  }
}
