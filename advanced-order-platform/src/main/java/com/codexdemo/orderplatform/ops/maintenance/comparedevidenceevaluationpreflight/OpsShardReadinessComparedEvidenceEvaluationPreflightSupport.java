package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessComparedEvidenceEvaluationPreflightSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1351";
  static final String SOURCE_NODE_EVIDENCE_INTAKE_VERSION = "Node v1331";
  static final String SOURCE_JAVA_REVIEW_HANDOFF_VERSION = "Java v1044";
  static final String EVALUATION_CONTRACT_STATE = "rule-contract-only";
  static final String CANDIDATE_EVIDENCE_STATE = "candidate-absent";
  static final String EVIDENCE_ACCEPTANCE_STATE = "not-accepted";
  static final String APPROVAL_CAPTURE_STATE = "not-captured";
  static final String RUNTIME_PAYLOAD_STATE = "locked";
  static final String SIBLING_MUTATION_STATE = "locked";

  private OpsShardReadinessComparedEvidenceEvaluationPreflightSupport() {}

  static OpsShardReadinessComparedEvidenceEvaluationPreflightResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule> rules,
      List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard> guards,
      List<String> additionalChecks) {
    var ruleCopy = List.copyOf(rules);
    var guardCopy = List.copyOf(guards);
    int passedRuleCount =
        (int) ruleCopy.stream().filter(rule -> "passed".equals(rule.status())).count();
    int passedGuardCount =
        (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("compared-evidence-evaluation-preflight-rule-count-" + ruleCopy.size());
    checks.add("compared-evidence-evaluation-preflight-guard-count-" + guardCopy.size());
    checks.add("compared-evidence-evaluation-preflight-source-plan-" + SOURCE_PLAN);
    checks.add(
        "compared-evidence-evaluation-preflight-source-node-"
            + SOURCE_NODE_EVIDENCE_INTAKE_VERSION);
    checks.add(
        "compared-evidence-evaluation-preflight-source-java-" + SOURCE_JAVA_REVIEW_HANDOFF_VERSION);
    checks.add("compared-evidence-evaluation-preflight-no-candidate-fabrication");
    checks.add("compared-evidence-evaluation-preflight-no-synthetic-evidence-acceptance");
    checks.add("compared-evidence-evaluation-preflight-no-approval-capture");
    checks.add("compared-evidence-evaluation-preflight-no-runtime-payload");
    checks.add("compared-evidence-evaluation-preflight-no-sibling-mutation");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessComparedEvidenceEvaluationPreflightResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_EVIDENCE_INTAKE_VERSION,
        SOURCE_JAVA_REVIEW_HANDOFF_VERSION,
        EVALUATION_CONTRACT_STATE,
        CANDIDATE_EVIDENCE_STATE,
        EVIDENCE_ACCEPTANCE_STATE,
        APPROVAL_CAPTURE_STATE,
        RUNTIME_PAYLOAD_STATE,
        SIBLING_MUTATION_STATE,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        ruleCopy.size(),
        passedRuleCount,
        guardCopy.size(),
        passedGuardCount,
        ruleCopy,
        guardCopy,
        List.copyOf(checks),
        passedRuleCount == ruleCopy.size() && passedGuardCount == guardCopy.size()
            ? "passed"
            : "blocked");
  }

  static OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule rule(
      String code,
      String sourceNodeVersion,
      String evaluationArea,
      String rule,
      String missingCandidateGuard,
      String sourceEndpoint) {
    return new OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule(
        code,
        sourceNodeVersion,
        evaluationArea,
        rule,
        missingCandidateGuard,
        sourceEndpoint,
        "passed");
  }

  static OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard guard(
      String code, String category, String guard, String rejectionCode) {
    return new OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard(
        code, category, guard, rejectionCode, "fail-closed", "passed");
  }
}
