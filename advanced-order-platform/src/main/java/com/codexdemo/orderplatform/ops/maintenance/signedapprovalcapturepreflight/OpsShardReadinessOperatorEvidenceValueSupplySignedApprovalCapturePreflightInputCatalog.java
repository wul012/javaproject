package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight;

import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog {

  static final int INPUT_COUNT = 25;

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
              .CaptureInput>
      allInputs() {
    return List.of(
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_01_REQUEST_ID",
            "template.capture.requestId",
            "identity",
            "Require a capture preflight request id derived from template metadata.",
            "request id cannot create a signed approval",
            "node-v1061-capture-preflight",
            "request-id-input",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService.ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_02_TEMPLATE_DIGEST",
            "template.digest",
            "template-binding",
            "Bind the source signed approval template digest.",
            "template digest cannot substitute for signed approval material",
            "node-v1036-template",
            "source-template-digest",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_03_PACKET_REVIEW_DIGEST",
            "review.digest",
            "review-binding",
            "Bind the source approval packet review digest.",
            "review digest cannot emit approval grant",
            "node-v1011-packet-review",
            "source-review-digest",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService.ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_04_OPERATOR_IDENTITY_ALIAS",
            "operator.identityAlias",
            "operator",
            "Mirror the operator identity alias without credentials.",
            "identity alias cannot authorize value submission",
            "node-v1061-capture-preflight",
            "operator-identity-alias",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_05_OPERATOR_ROLE",
            "operator.role",
            "operator",
            "Mirror the operator role requirement from the approval packet review.",
            "operator role cannot grant approval",
            "node-v1061-capture-preflight",
            "operator-role-mirror",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_06_TIMESTAMP_SOURCE",
            "capture.timestampSource",
            "time",
            "Declare timestamp source placeholder before capture artifact design.",
            "timestamp placeholder cannot timestamp an approval",
            "node-v1061-capture-preflight",
            "timestamp-source-placeholder",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_07_MANUAL_WINDOW_ID",
            "capture.manualWindowId",
            "time",
            "Declare manual capture window id placeholder.",
            "manual window cannot open live execution",
            "node-v1061-capture-preflight",
            "manual-capture-window-id",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_08_CAPTURE_CHANNEL_POLICY",
            "capture.channelPolicy",
            "channel",
            "Declare capture channel policy placeholder.",
            "channel policy cannot route raw endpoints",
            "node-v1061-capture-preflight",
            "capture-channel-policy",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_09_SIGNATURE_ALGORITHM_POLICY",
            "signature.algorithmPolicy",
            "signature",
            "Declare signature algorithm policy placeholder.",
            "algorithm policy cannot contain signature material",
            "node-v1061-capture-preflight",
            "signature-algorithm-policy",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_10_SIGNATURE_REDACTION_POLICY",
            "signature.materialRedaction",
            "signature",
            "Declare signature material redaction policy.",
            "signature material remains absent",
            "node-v1061-capture-preflight",
            "signature-material-redaction",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_11_APPROVAL_STATEMENT_PLACEHOLDER",
            "approval.statementPlaceholder",
            "statement",
            "Declare approval statement placeholder without signed text.",
            "placeholder cannot be treated as signed approval",
            "node-v1061-capture-preflight",
            "approval-statement-placeholder",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService.ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_12_OPERATOR_JUSTIFICATION",
            "operator.justificationMirror",
            "statement",
            "Mirror operator justification metadata without value body.",
            "justification mirror cannot submit values",
            "node-v1061-capture-preflight",
            "operator-justification-mirror",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService.ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_13_SOURCE_EVIDENCE_VERSION",
            "evidence.version",
            "evidence",
            "Mirror the source evidence version.",
            "evidence version cannot import evidence",
            "node-v1061-capture-preflight",
            "source-evidence-version",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_14_SOURCE_EVIDENCE_FILE_ID",
            "evidence.fileId",
            "evidence",
            "Mirror the source evidence file id.",
            "file id cannot load file contents",
            "node-v1061-capture-preflight",
            "source-evidence-file-id",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_15_SOURCE_EVIDENCE_SNIPPET_ID",
            "evidence.snippetId",
            "evidence",
            "Mirror the source evidence snippet id.",
            "snippet id cannot import payload",
            "node-v1061-capture-preflight",
            "source-evidence-snippet-id",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_16_REDACTED_VALUE_DIGEST_REFERENCE",
            "value.redactedDigest",
            "redaction",
            "Reference redacted value digest metadata only.",
            "redacted digest cannot be raw value hash",
            "node-v1061-capture-preflight",
            "redacted-value-digest-reference",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_17_VALUE_SHAPE_BINDING",
            "value.shapeBinding",
            "value-shape",
            "Bind value shape metadata without accepting a value body.",
            "value shape cannot normalize operator values",
            "node-v1061-capture-preflight",
            "value-shape-binding",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_18_REDACTION_POLICY_MIRROR",
            "policy.redaction",
            "redaction",
            "Mirror redaction policy before any capture artifact exists.",
            "redaction mirror cannot reveal secret material",
            "node-v1061-capture-preflight",
            "redaction-policy-mirror",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_19_PROVENANCE_POLICY_MIRROR",
            "policy.provenance",
            "provenance",
            "Mirror provenance policy before import preflight.",
            "provenance mirror cannot import evidence",
            "node-v1061-capture-preflight",
            "provenance-policy-mirror",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_20_RAW_SECRET_SIGNATURE_LOCK",
            "locks.rawSecretSignature",
            "lock",
            "Lock raw secret and raw signature material.",
            "raw secret or signature material cannot be stored",
            "node-v1061-capture-preflight",
            "raw-secret-signature-lock",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_21_APPROVAL_GRANT_LOCK",
            "locks.approvalGrant",
            "lock",
            "Lock approval grant emission.",
            "approval grant remains not emitted",
            "node-v1061-capture-preflight",
            "approval-grant-lock",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService.ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_22_ZERO_VALUE_COUNTS",
            "locks.zeroValueCounts",
            "lock",
            "Prove submitted, accepted, and imported value counts stay zero.",
            "value counters cannot increase",
            "node-v1061-capture-preflight",
            "zero-value-counts",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_23_NO_WRITE_ROUTE_LOCK",
            "locks.noWriteRoute",
            "lock",
            "Lock write route exposure.",
            "write route remains unavailable",
            "node-v1061-capture-preflight",
            "no-write-route-lock",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_24_SIBLING_NON_MUTATION",
            "locks.siblingMutation",
            "lock",
            "Lock sibling service mutation.",
            "sibling services cannot be started or mutated",
            "node-v1061-capture-preflight",
            "sibling-non-mutation-lock",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService
                .ENDPOINT),
        input(
            "SIGNED_CAPTURE_PREFLIGHT_INPUT_25_CLOSEOUT_BOUNDARY",
            "closeout.nextStepBoundary",
            "closeout",
            "Close capture preflight at artifact-preflight boundary.",
            "next step needs separate approval artifact plan",
            "node-v1061-capture-preflight",
            "capture-preflight-closeout-boundary",
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService.ENDPOINT));
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
              .CaptureInput>
      inputs(int fromInclusive, int toExclusive) {
    return List.copyOf(allInputs().subList(fromInclusive, toExclusive));
  }

  private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
          .CaptureInput
      input(
          String code,
          String sourceTemplateField,
          String captureStage,
          String inputRequirement,
          String blockedReason,
          String evidenceFileId,
          String evidenceSnippetId,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport.input(
        code,
        sourceTemplateField,
        captureStage,
        inputRequirement,
        blockedReason,
        evidenceFileId,
        evidenceSnippetId,
        sourceEndpoint);
  }
}
