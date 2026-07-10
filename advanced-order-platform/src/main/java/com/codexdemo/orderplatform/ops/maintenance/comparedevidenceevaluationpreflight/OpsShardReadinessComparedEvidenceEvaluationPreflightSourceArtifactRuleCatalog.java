package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewRoutePaths;
import java.util.List;

final class OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactRuleCatalog {

  private OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactRuleCatalog() {}

  static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule>
      sourceArtifactRules() {
    return List.of(
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "source-intake-readiness",
            "Node v1332",
            "source artifact",
            "Evaluate whether source intake readiness exists before any candidate is considered.",
            "reject-evaluation-source-intake-readiness-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.CATALOG),
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "evidence-artifact-shape",
            "Node v1333",
            "source artifact",
            "Require a named artifact shape without storing compared evidence material.",
            "reject-evaluation-artifact-shape-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.SOURCE_EVIDENCE),
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "operator-provenance",
            "Node v1334",
            "source artifact",
            "Require operator provenance as a handle, not an identity switch.",
            "reject-evaluation-operator-provenance-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.SOURCE_EVIDENCE),
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "manual-submission-reference",
            "Node v1335",
            "source artifact",
            "Require manual submission reference before offline comparison evaluation.",
            "reject-evaluation-manual-submission-reference-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.SOURCE_EVIDENCE),
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "offline-comparison-result",
            "Node v1336",
            "source artifact",
            "Require offline comparison result as a reference-only candidate field.",
            "reject-evaluation-offline-comparison-result-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.COMPARISON_OUTCOME));
  }
}
