package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint;

public final class OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths {

  public static final String BASE_PATH = "/api/v1/ops/shard-readiness";

  public static final String COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-blueprint-catalog";
  public static final String COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_SOURCE =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-blueprint-source";
  public static final String COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_COMPARISON =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-blueprint-comparison";
  public static final String COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_POLICY =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-blueprint-policy";
  public static final String COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT =
      "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-blueprint-closeout";

  public static final String CATALOG = BASE_PATH + COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG;
  public static final String SOURCE = BASE_PATH + COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_SOURCE;
  public static final String COMPARISON =
      BASE_PATH + COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_COMPARISON;
  public static final String POLICY = BASE_PATH + COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_POLICY;
  public static final String CLOSEOUT = BASE_PATH + COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT;

  private OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths() {}
}
