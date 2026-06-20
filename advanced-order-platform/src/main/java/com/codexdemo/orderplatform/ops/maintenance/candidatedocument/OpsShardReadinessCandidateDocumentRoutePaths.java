package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths;

public final class OpsShardReadinessCandidateDocumentRoutePaths {

  public static final String BASE_PATH = "/api/v1/ops/shard-readiness";

  public static final String
      OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_CANDIDATE_DOCUMENT_REQUEST_PACKAGE =
          "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-candidate-document-request-package";
  public static final String
      OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG =
          "/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-intake-preflight-catalog";
  public static final String CANDIDATE_DOCUMENT_REQUEST_PACKAGE_HANDOFF =
      "/candidate-document-request-package-handoff";
  public static final String CANDIDATE_DOCUMENT_SUBMISSION_PRECHECK =
      "/candidate-document-submission-precheck";
  public static final String CANDIDATE_DOCUMENT_INTAKE_PACKET = "/candidate-document-intake-packet";
  public static final String CANDIDATE_DOCUMENT_MATERIAL_REQUEST =
      "/candidate-document-material-request";
  public static final String CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK =
      "/candidate-document-material-submission-precheck";
  public static final String CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK_HANDOFF =
      "/candidate-document-material-submission-precheck-handoff";
  public static final String CANDIDATE_DOCUMENT_PROFILE_SECTION_REGISTRY =
      "/candidate-document-profile-section-registry";
  public static final String SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_REGISTRY =
      OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths
          .SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_REGISTRY;
  public static final String SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF =
      "/signed-approval-draft-profile-section-handoff";
  public static final String SIGNED_APPROVAL_DRAFT_TEXT_PACKAGE_PROFILE_SECTION_REGISTRY =
      "/signed-approval-draft-text-package-profile-section-registry";

  private OpsShardReadinessCandidateDocumentRoutePaths() {}
}
