package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog {

  static final int SEAL_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
              .ArtifactSeal>
      allSeals() {
    return List.of(
        seal(
            "ARTIFACT_PREFLIGHT_REQUEST_ID_SEAL",
            "request",
            "Seal request id as metadata only.",
            "reject-materialization",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_CAPTURE_PREFLIGHT_DIGEST_SEAL",
            "digest",
            "Seal source capture preflight digest.",
            "reject-capture-bypass",
            "required"),
        seal(
            "ARTIFACT_PREFLIGHT_TEMPLATE_DIGEST_SEAL",
            "digest",
            "Seal signed approval template digest.",
            "reject-template-substitution",
            "required"),
        seal(
            "ARTIFACT_PREFLIGHT_REVIEW_DIGEST_SEAL",
            "digest",
            "Seal approval packet review digest.",
            "reject-review-substitution",
            "required"),
        seal(
            "ARTIFACT_PREFLIGHT_OPERATOR_IDENTITY_SEAL",
            "operator",
            "Seal operator identity as alias-only.",
            "reject-credential-material",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_OPERATOR_ROLE_SEAL",
            "operator",
            "Seal operator role without grant authority.",
            "reject-approval-grant",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_WINDOW_ID_SEAL",
            "capture-window",
            "Seal capture window id as placeholder.",
            "reject-runtime-open",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_CHANNEL_POLICY_SEAL",
            "capture-policy",
            "Seal capture channel policy without write route.",
            "reject-write-route",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_SIGNATURE_ALGORITHM_SEAL",
            "signature",
            "Seal signature algorithm without signature material.",
            "reject-signature-material",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_DETACHED_SIGNATURE_PLACEHOLDER_SEAL",
            "signature",
            "Seal detached signature placeholder.",
            "reject-detached-signature-body",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_SIGNATURE_REDACTION_SEAL",
            "signature",
            "Seal signature material redaction policy.",
            "reject-raw-signature",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_APPROVAL_STATEMENT_DIGEST_SEAL",
            "statement",
            "Seal approval statement digest placeholder.",
            "reject-signed-statement",
            "placeholder-only"),
        seal(
            "ARTIFACT_PREFLIGHT_SOURCE_VERSION_SEAL",
            "evidence",
            "Seal source evidence version without import.",
            "reject-evidence-import",
            "metadata-only"),
        seal(
            "ARTIFACT_PREFLIGHT_SOURCE_FILE_SEAL",
            "evidence",
            "Seal source evidence file id without file read.",
            "reject-file-load",
            "metadata-only"),
        seal(
            "ARTIFACT_PREFLIGHT_SOURCE_SNIPPET_SEAL",
            "evidence",
            "Seal source evidence snippet id without payload import.",
            "reject-payload-import",
            "metadata-only"),
        seal(
            "ARTIFACT_PREFLIGHT_REDACTED_VALUE_DIGEST_SEAL",
            "value",
            "Seal redacted value digest reference.",
            "reject-raw-value-hash",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_VALUE_SHAPE_SEAL",
            "value",
            "Seal value shape without value body.",
            "reject-value-body",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_REDACTION_POLICY_SEAL",
            "policy",
            "Seal redaction policy mirror.",
            "reject-secret-reveal",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_PROVENANCE_POLICY_SEAL",
            "policy",
            "Seal provenance policy mirror.",
            "reject-evidence-import",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_NO_RAW_SECRET_SEAL",
            "lock",
            "Seal raw secret absence.",
            "reject-raw-secret",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_NO_GRANT_SEAL",
            "lock",
            "Seal approval grant absence.",
            "reject-approval-grant",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_ZERO_VALUE_IMPORT_SEAL",
            "lock",
            "Seal zero submitted, accepted, and imported values.",
            "reject-value-count-increase",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_NO_WRITE_ROUTE_SEAL",
            "lock",
            "Seal write route absence.",
            "reject-write-route",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_SIBLING_NON_MUTATION_SEAL",
            "lock",
            "Seal sibling non-mutation.",
            "reject-sibling-mutation",
            "fail-closed"),
        seal(
            "ARTIFACT_PREFLIGHT_CLOSEOUT_SEAL",
            "closeout",
            "Seal closeout boundary before artifact draft.",
            "reject-auto-artifact-draft",
            "required-before-next-step"));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
              .ArtifactSeal>
      seals(int fromInclusive, int toExclusive) {
    return List.copyOf(allSeals().subList(fromInclusive, toExclusive));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
          .ArtifactSeal
      seal(
          String code,
          String category,
          String sealRequirement,
          String rejectionCode,
          String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
        .seal(code, category, sealRequirement, rejectionCode, enforcement);
  }
}
