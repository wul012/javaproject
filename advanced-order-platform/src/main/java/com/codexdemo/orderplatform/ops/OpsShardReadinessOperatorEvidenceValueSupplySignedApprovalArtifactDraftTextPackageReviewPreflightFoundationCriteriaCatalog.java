package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeDigestBindingService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSignatureEnvelopeService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationCriteriaCatalog {

  static final int FOUNDATION_CRITERION_COUNT = 11;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationCriteriaCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
              .ReviewCriterion>
      foundationCriteria() {
    return List.of(
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REQUEST_MANIFEST_CRITERION",
            "Node v1237-v1240",
            "requestManifestId matches intake field.",
            "Is the request manifest id stable?",
            "reject missing or changed requestManifestId",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_CORRELATION_CRITERION",
            "Node v1237-v1240",
            "requestCorrelationId matches intake field.",
            "Does correlation bind request and package?",
            "reject missing or changed requestCorrelationId",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_OPERATOR_ID_CRITERION",
            "Node v1237-v1240",
            "operatorId matches expected offline package owner.",
            "Is the operator identity reviewable?",
            "reject missing operatorId",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_PACKAGE_ID_CRITERION",
            "Node v1237-v1240",
            "draftTextPackageId is stable.",
            "Can the package identity be reviewed without accepting text?",
            "reject missing draftTextPackageId",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_INSTRUCTION_DIGEST_CRITERION",
            "Node v1241-v1244",
            "instructionPreflightDigest matches Java v909.",
            "Does the package pin instruction preflight?",
            "reject digest mismatch",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeDigestBindingService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_AUTHORING_DIGEST_CRITERION",
            "Node v1241-v1244",
            "authoringReadinessDigest matches Java v884.",
            "Does authoring readiness remain pinned?",
            "reject authoring digest mismatch",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeDigestBindingService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_ARTIFACT_DIGEST_CRITERION",
            "Node v1241-v1244",
            "artifactPreflightDigest matches prerequisite artifact evidence.",
            "Is artifact evidence pinned?",
            "reject artifact digest mismatch",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeDigestBindingService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_FIELD_MAP_DIGEST_CRITERION",
            "Node v1241-v1244",
            "fieldMapDigest matches the expected intake map.",
            "Does the field map match v1236?",
            "reject field map digest mismatch",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeDigestBindingService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SIGNATURE_ENVELOPE_CRITERION",
            "Node v1245-v1247",
            "signatureEnvelopeId is metadata-only.",
            "Is the envelope id reviewable without payload?",
            "reject envelope payload material",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSignatureEnvelopeService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SIGNATURE_ALGORITHM_CRITERION",
            "Node v1245-v1247",
            "signatureAlgorithmPolicy matches intake policy.",
            "Is signature algorithm policy pinned?",
            "reject signature algorithm policy mismatch",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSignatureEnvelopeService
                .ENDPOINT),
        criterion(
            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SIGNATURE_REDACTION_CRITERION",
            "Node v1245-v1247",
            "signatureRedactionPolicy blocks raw signature payloads.",
            "Is signature redaction reviewable?",
            "reject raw detached signature text",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSignatureEnvelopeService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
          .ReviewCriterion
      criterion(
          String code,
          String versionRange,
          String reviewCriterion,
          String reviewQuestion,
          String materialRejectionControl,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
        .criterion(
            code,
            versionRange,
            reviewCriterion,
            reviewQuestion,
            materialRejectionControl,
            sourceEndpoint);
  }
}
