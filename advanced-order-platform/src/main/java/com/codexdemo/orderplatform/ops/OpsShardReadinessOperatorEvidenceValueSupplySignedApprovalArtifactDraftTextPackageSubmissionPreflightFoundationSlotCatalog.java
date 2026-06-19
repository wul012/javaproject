package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightFoundationSlotCatalog {

  static final int FOUNDATION_SLOT_COUNT = 11;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightFoundationSlotCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
              .SubmissionSlot>
      foundationSlots() {
    return List.of(
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_REQUEST_MANIFEST_SLOT",
            "Node v1262-v1265",
            "requestManifestId",
            "Can submission be tied to the intake request manifest?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_CORRELATION_SLOT",
            "Node v1262-v1265",
            "requestCorrelationId",
            "Can submission correlate request and package?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_OPERATOR_ID_SLOT",
            "Node v1262-v1265",
            "operatorId",
            "Can submission name the offline operator?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_PACKAGE_ID_SLOT",
            "Node v1262-v1265",
            "draftTextPackageId",
            "Can submission identify the package without accepting it?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_INSTRUCTION_DIGEST_SLOT",
            "Node v1266-v1269",
            "instructionPreflightDigest",
            "Can submission compare the instruction digest?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_AUTHORING_DIGEST_SLOT",
            "Node v1266-v1269",
            "authoringReadinessDigest",
            "Can submission compare authoring readiness digest?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_ARTIFACT_DIGEST_SLOT",
            "Node v1266-v1269",
            "artifactPreflightDigest",
            "Can submission compare artifact preflight digest?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_FIELD_MAP_DIGEST_SLOT",
            "Node v1266-v1269",
            "fieldMapDigest",
            "Can submission compare field map digest?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_SIGNATURE_ENVELOPE_SLOT",
            "Node v1270-v1272",
            "signatureEnvelopeId",
            "Can signature envelope metadata be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_SIGNATURE_ALGORITHM_SLOT",
            "Node v1270-v1272",
            "signatureAlgorithmPolicy",
            "Can signature algorithm policy be compared?"),
        slot(
            "DRAFT_TEXT_PACKAGE_SUBMISSION_SIGNATURE_REDACTION_SLOT",
            "Node v1270-v1272",
            "signatureRedactionPolicy",
            "Can signature redaction policy be compared?"));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
          .SubmissionSlot
      slot(String code, String versionRange, String submissionSlot, String question) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
        .slot(
            code,
            versionRange,
            submissionSlot,
            question,
            "compare only; do not accept submitted material",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService
                .ENDPOINT);
  }
}
