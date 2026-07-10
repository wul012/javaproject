package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview.OpsShardReadinessComparedPackageReviewRoutePaths;
import java.util.List;

final class OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeRuleCatalog {

  private OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeRuleCatalog() {}

  static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule>
      policyRuntimeRules() {
    return List.of(
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "policy-assertion",
            "Node v1342",
            "policy runtime",
            "Require policy assertion before evaluation can be rendered complete.",
            "reject-evaluation-policy-assertion-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.POLICY_ARCHIVE),
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "execution-lock",
            "Node v1343",
            "policy runtime",
            "Require execution lock evidence and keep runtime execution disabled.",
            "reject-evaluation-execution-lock-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.POLICY_ARCHIVE),
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "approval-grant-separation",
            "Node v1344",
            "policy runtime",
            "Require approval grant separation before any future approval flow.",
            "reject-evaluation-approval-grant-separation-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.POLICY_ARCHIVE),
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "archive-reference",
            "Node v1345",
            "policy runtime",
            "Require archive reference while archive writes stay disabled.",
            "reject-evaluation-archive-reference-missing",
            OpsShardReadinessComparedPackageReviewRoutePaths.HANDOFF_CLOSEOUT),
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule(
            "secret-value-exclusion",
            "Node v1346",
            "policy runtime",
            "Reject candidate evidence that includes credential, token, or secret value material.",
            "reject-evaluation-secret-value-present",
            OpsShardReadinessComparedPackageReviewRoutePaths.POLICY_ARCHIVE));
  }
}
