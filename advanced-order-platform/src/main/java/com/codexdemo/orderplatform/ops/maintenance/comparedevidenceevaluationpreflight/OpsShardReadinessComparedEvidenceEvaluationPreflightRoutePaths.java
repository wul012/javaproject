package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

public final class OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths {

  public static final String BASE_PATH = "/api/v1/ops/shard-readiness";

  public static final String COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_CATALOG =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight-catalog";
  public static final String COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_SOURCE_ARTIFACT =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight-source-artifact";
  public static final String COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_IDENTITY_DIGEST =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight-identity-digest";
  public static final String COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_POLICY_RUNTIME =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight-policy-runtime";
  public static final String COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_EXCLUSION_CLOSEOUT =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight-exclusion-closeout";

  public static final String CATALOG = BASE_PATH + COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_CATALOG;
  public static final String SOURCE_ARTIFACT =
      BASE_PATH + COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_SOURCE_ARTIFACT;
  public static final String IDENTITY_DIGEST =
      BASE_PATH + COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_IDENTITY_DIGEST;
  public static final String POLICY_RUNTIME =
      BASE_PATH + COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_POLICY_RUNTIME;
  public static final String EXCLUSION_CLOSEOUT =
      BASE_PATH + COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_EXCLUSION_CLOSEOUT;

  private OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths() {}
}
