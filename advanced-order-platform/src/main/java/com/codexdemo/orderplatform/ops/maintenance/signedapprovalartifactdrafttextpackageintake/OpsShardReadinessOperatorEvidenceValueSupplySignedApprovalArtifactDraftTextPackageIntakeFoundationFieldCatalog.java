package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightOperatorInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationFieldCatalog {

  static final int FOUNDATION_FIELD_COUNT = 11;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationFieldCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
              .IntakeField>
      foundationFields() {
    return List.of(
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_MANIFEST_ID_FIELD",
            "Node v1212-v1215",
            "requestManifestId",
            "Bind package to the frozen request manifest.",
            "Field is expected only; package is not accepted.",
            "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_MANIFEST_ID_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_CORRELATION_ID_FIELD",
            "Node v1212-v1215",
            "requestCorrelationId",
            "Bind request, package, and review correlation.",
            "Correlation is not used to open execution.",
            "DRAFT_TEXT_PACKAGE_INTAKE_REQUEST_CORRELATION_ID_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_ID_FIELD",
            "Node v1212-v1215",
            "operatorId",
            "Name the offline package owner.",
            "Identity is metadata only and grants no approval.",
            "DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_ID_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightOperatorInstructionService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_PACKAGE_IDENTITY_FIELD",
            "Node v1212-v1215",
            "draftTextPackageId",
            "Provide stable package identity before review.",
            "Package identity does not store draft text.",
            "DRAFT_TEXT_PACKAGE_INTAKE_PACKAGE_IDENTITY_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_INSTRUCTION_PREFLIGHT_DIGEST_FIELD",
            "Node v1216-v1219",
            "instructionPreflightDigest",
            "Pin the instruction preflight snapshot.",
            "Digest pin is read-only and does not materialize instructions.",
            "DRAFT_TEXT_PACKAGE_INTAKE_INSTRUCTION_PREFLIGHT_DIGEST_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_AUTHORING_READINESS_DIGEST_FIELD",
            "Node v1216-v1219",
            "authoringReadinessDigest",
            "Bind the package to Java v884 authoring readiness.",
            "Digest pin cannot approve the package.",
            "DRAFT_TEXT_PACKAGE_INTAKE_AUTHORING_READINESS_DIGEST_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_ARTIFACT_PREFLIGHT_DIGEST_FIELD",
            "Node v1216-v1219",
            "artifactPreflightDigest",
            "Bind the package to artifact preflight evidence.",
            "Digest pin cannot accept an artifact.",
            "DRAFT_TEXT_PACKAGE_INTAKE_ARTIFACT_PREFLIGHT_DIGEST_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_FIELD_MAP_DIGEST_FIELD",
            "Node v1216-v1219",
            "fieldMapDigest",
            "Pin the expected field map for later review.",
            "Field map stays expected-fields-only.",
            "DRAFT_TEXT_PACKAGE_INTAKE_FIELD_MAP_DIGEST_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ENVELOPE_ID_FIELD",
            "Node v1220-v1222",
            "signatureEnvelopeId",
            "Name a future detached signature envelope.",
            "Envelope identity cannot carry signature material.",
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ENVELOPE_ID_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ALGORITHM_POLICY_FIELD",
            "Node v1220-v1222",
            "signatureAlgorithmPolicy",
            "Bind the expected signature algorithm policy.",
            "Policy reference cannot capture a signature.",
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_ALGORITHM_POLICY_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService
                .ENDPOINT),
        field(
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_REDACTION_POLICY_FIELD",
            "Node v1220-v1222",
            "signatureRedactionPolicy",
            "Declare redaction requirements for future signature review.",
            "Redaction policy cannot accept detached signature text.",
            "DRAFT_TEXT_PACKAGE_INTAKE_SIGNATURE_REDACTION_POLICY_GUARD",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
          .IntakeField
      field(
          String code,
          String versionRange,
          String expectedField,
          String intakePurpose,
          String materializationBlocker,
          String guardCode,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
        .field(
            code,
            versionRange,
            expectedField,
            intakePurpose,
            materializationBlocker,
            guardCode,
            sourceEndpoint);
  }
}
