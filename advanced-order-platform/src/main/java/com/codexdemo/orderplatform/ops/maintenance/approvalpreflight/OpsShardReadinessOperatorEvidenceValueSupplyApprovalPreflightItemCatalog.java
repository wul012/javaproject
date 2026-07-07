package com.codexdemo.orderplatform.ops.maintenance.approvalpreflight;

import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog {

  static final int ITEM_COUNT = 25;

  private OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog() {}

  static List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem>
      allItems() {
    return List.of(
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_01_PACKET_ID",
            "VALUE_SUPPLY_01_ENVELOPE_ID",
            "identity",
            "Declare a draft approval packet id without creating a signed record.",
            "approval packet id is metadata only",
            "java-v658-value-supply-closeout",
            "value-supply-envelope-id",
            OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_02_OPERATOR_IDENTITY_ALIAS",
            "VALUE_SUPPLY_02_OPERATOR_REFERENCE",
            "identity",
            "Bind an operator identity alias without storing a credential value.",
            "operator identity cannot authorize submission",
            "java-v658-value-supply-operator-review",
            "operator-reference-alias",
            OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_03_REVIEWER_ROLE",
            "VALUE_SUPPLY_03_SOURCE_DRAFT_SLOT",
            "identity",
            "Record reviewer role requirements before signed approval capture exists.",
            "reviewer role is not an approval grant",
            "node-v986-approval-packet-draft",
            "reviewer-role-policy",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_04_APPROVAL_INTENT",
            "VALUE_SUPPLY_04_VALUE_KIND",
            "identity",
            "Describe approval intent as metadata, not a value body.",
            "approval intent cannot carry operator values",
            "node-v986-approval-packet-draft",
            "approval-intent-metadata",
            OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_05_SIGNED_HUMAN_POLICY",
            "VALUE_SUPPLY_05_REDACTION_CLASSIFICATION",
            "signature",
            "Require a future signed-human policy before value import.",
            "signed approval capture remains locked",
            "node-v986-approval-packet-draft",
            "signed-human-policy",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_06_ISSUED_AT_TIMESTAMP",
            "VALUE_SUPPLY_06_CREDENTIAL_VALUE_BLOCK",
            "timestamp",
            "Require issued-at timestamp shape without accepting a timestamped approval.",
            "timestamp cannot authorize value import",
            "node-v986-approval-packet-draft",
            "issued-at-timestamp-shape",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_07_EXPIRY_WINDOW",
            "VALUE_SUPPLY_07_RAW_ENDPOINT_BLOCK",
            "timestamp",
            "Require expiry window metadata for future approval validation.",
            "expiry window cannot unlock runtime reads",
            "node-v986-approval-packet-draft",
            "expiry-window-shape",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_08_REPLAY_NONCE",
            "VALUE_SUPPLY_08_SECRET_MATERIAL_BLOCK",
            "timestamp",
            "Require replay nonce metadata without storing secret material.",
            "nonce is not a credential",
            "node-v986-approval-packet-draft",
            "replay-nonce-shape",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_09_REDACTION_DIGEST_ID",
            "VALUE_SUPPLY_09_MISSING_VALUE_POLICY",
            "redaction",
            "Require a redaction digest id before approval capture can be designed.",
            "digest id cannot include a value hash",
            "java-v658-value-supply-redaction-policy",
            "redaction-digest-id",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_10_REDACTION_DIGEST_ALGORITHM",
            "VALUE_SUPPLY_10_BLANK_VALUE_POLICY",
            "redaction",
            "Require an approved digest algorithm before capture.",
            "algorithm cannot hash raw value bodies",
            "java-v658-value-supply-redaction-policy",
            "redaction-digest-algorithm",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_11_CREDENTIAL_ABSENCE_PROOF",
            "VALUE_SUPPLY_11_MANUAL_ENTRY_LOCK",
            "redaction",
            "Prove credential values are absent from the packet draft.",
            "credential value remains blocked",
            "java-v658-value-supply-redaction-policy",
            "credential-absence-proof",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_12_RAW_ENDPOINT_ABSENCE_PROOF",
            "VALUE_SUPPLY_12_REVIEWER_REQUIRED",
            "redaction",
            "Prove raw endpoints are absent from the packet draft.",
            "raw endpoint remains alias-only",
            "java-v658-value-supply-redaction-policy",
            "raw-endpoint-absence-proof",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_13_PROVENANCE_SOURCE_ID",
            "VALUE_SUPPLY_13_PROVENANCE_SOURCE_ID",
            "provenance",
            "Require a provenance source id for future value import.",
            "provenance source id cannot trigger import",
            "java-v658-value-supply-provenance",
            "provenance-source-id",
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_14_PROVENANCE_EVIDENCE_FILE",
            "VALUE_SUPPLY_14_PROVENANCE_EVIDENCE_FILE",
            "provenance",
            "Require source evidence file id for future import review.",
            "evidence file id is not an imported payload",
            "java-v658-value-supply-provenance",
            "provenance-evidence-file",
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_15_PROVENANCE_SNIPPET_ID",
            "VALUE_SUPPLY_15_PROVENANCE_SNIPPET_ID",
            "provenance",
            "Require source snippet id for future import review.",
            "snippet id cannot substitute for a signed approval",
            "java-v658-value-supply-provenance",
            "provenance-snippet-id",
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_16_ENDPOINT_ALIAS",
            "VALUE_SUPPLY_16_SOURCE_ENDPOINT_ALIAS",
            "provenance",
            "Require endpoint aliases rather than raw endpoint values.",
            "raw endpoint remains blocked",
            "java-v658-value-supply-provenance",
            "source-endpoint-alias",
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_17_TYPED_VALUE_ENVELOPE_REFERENCE",
            "VALUE_SUPPLY_17_FRESH_SIBLING_REFERENCE",
            "value-envelope",
            "Reference typed value envelope shape without accepting a value.",
            "typed value body remains absent",
            "java-v684-adapter-preflight-closeout",
            "typed-value-envelope-reference",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_18_MALFORMED_VALUE_REJECTION",
            "VALUE_SUPPLY_18_HISTORICAL_FALLBACK_MARKER",
            "rejection",
            "Reject malformed values before approval packet import exists.",
            "malformed value cannot be normalized",
            "java-v684-adapter-preflight-closeout",
            "malformed-value-rejection",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_19_MISSING_VALUE_REJECTION",
            "VALUE_SUPPLY_19_SYNTHETIC_EVIDENCE_BLOCK",
            "rejection",
            "Reject missing values before approval packet import exists.",
            "missing value cannot be synthesized",
            "java-v684-adapter-preflight-closeout",
            "missing-value-rejection",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_20_ZERO_SUPPLIED_VALUE_COUNT",
            "VALUE_SUPPLY_20_RUNTIME_PAYLOAD_BLOCK",
            "zero-count",
            "Prove supplied value count stays zero in approval preflight.",
            "supplied value count cannot increase",
            "java-v658-value-supply-validation",
            "zero-supplied-value-count",
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_21_ZERO_ACCEPTED_VALUE_COUNT",
            "VALUE_SUPPLY_21_IMPORT_PREVIEW_BLOCK",
            "zero-count",
            "Prove accepted value count stays zero in approval preflight.",
            "accepted value count cannot increase",
            "java-v658-value-supply-validation",
            "zero-accepted-value-count",
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_22_ZERO_IMPORTED_VALUE_COUNT",
            "VALUE_SUPPLY_22_WRITE_SIDE_EFFECT_BLOCK",
            "zero-count",
            "Prove imported value count stays zero in approval preflight.",
            "imported value count cannot increase",
            "java-v658-value-supply-validation",
            "zero-imported-value-count",
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_23_CLEANUP_RECEIPT_ID",
            "VALUE_SUPPLY_23_LIVE_EXECUTION_BLOCK",
            "receipt",
            "Require cleanup receipt id before any future import run.",
            "cleanup receipt cannot start a process",
            "java-v684-adapter-preflight-closeout",
            "cleanup-receipt-id",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_24_RUNTIME_PAYLOAD_BLOCK",
            "VALUE_SUPPLY_24_PRODUCTION_EXECUTION_BLOCK",
            "runtime",
            "Block runtime payloads from approval preflight.",
            "runtime payload remains locked",
            "java-v684-adapter-preflight-closeout",
            "runtime-payload-block",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT),
        item(
            "VALUE_SUPPLY_APPROVAL_PACKET_25_CLOSEOUT_LOCKS_HELD",
            "VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD",
            "closeout",
            "Close approval preflight with all locks held.",
            "approval preflight remains draft-only",
            "java-v684-adapter-preflight-closeout",
            "approval-preflight-closeout-locks",
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT));
  }

  static List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem>
      items(int fromInclusive, int toExclusive) {
    return List.copyOf(allItems().subList(fromInclusive, toExclusive));
  }

  private static OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem
      item(
          String code,
          String sourceEnvelopeSlot,
          String packetStage,
          String packetRequirement,
          String blockedReason,
          String evidenceFileId,
          String evidenceSnippetId,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.item(
        code,
        sourceEnvelopeSlot,
        packetStage,
        packetRequirement,
        blockedReason,
        evidenceFileId,
        evidenceSnippetId,
        sourceEndpoint);
  }
}

final class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog {

  static final int POLICY_COUNT = 20;

  private OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog() {}

  static List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy>
      allPolicies() {
    return List.of(
        policy(
            "APPROVAL_PREFLIGHT_POLICY_01_IDENTITY_ALIAS_ONLY",
            "identity",
            "Operator identity is represented by alias only; credential values are not accepted.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_02_REVIEWER_ROLE_REQUIRED",
            "identity",
            "Reviewer role metadata must exist before approval packet design can proceed.",
            "required-before-capture"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_03_SIGNED_HUMAN_APPROVAL_REQUIRED",
            "approval",
            "Signed human approval is required later, but this preflight cannot capture it.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_04_NO_APPROVAL_CAPTURE",
            "approval",
            "Approval capture, approval grant, and approval persistence remain locked.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_05_NO_OPERATOR_VALUE_BODY",
            "approval",
            "Approval packet preflight cannot contain operator value body fields.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_06_ISSUED_AT_REQUIRED",
            "timestamp",
            "Issued-at timestamp is required in the future packet shape.",
            "required-before-capture"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_07_EXPIRY_WINDOW_REQUIRED",
            "timestamp",
            "Expiry window is required before a signed approval can be considered.",
            "required-before-capture"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_08_REPLAY_NONCE_REQUIRED",
            "timestamp",
            "Replay nonce metadata is required without storing secret material.",
            "required-before-capture"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_09_REDACTION_DIGEST_REQUIRED",
            "redaction",
            "Redaction digest must exist before future approval capture.",
            "required-before-capture"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_10_NO_CREDENTIAL_OR_RAW_ENDPOINT",
            "redaction",
            "Credential values and raw endpoints remain blocked from approval preflight.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_11_PROVENANCE_SOURCE_REQUIRED",
            "provenance",
            "Provenance source id is required before future import review.",
            "required-before-import"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_12_PROVENANCE_FILE_AND_SNIPPET_REQUIRED",
            "provenance",
            "Evidence file and snippet ids are required before future import review.",
            "required-before-import"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_13_TYPED_VALUE_ENVELOPE_REFERENCE_ONLY",
            "value-envelope",
            "Typed value envelope references cannot include supplied value bodies.",
            "metadata-only"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_14_MALFORMED_VALUES_REJECTED",
            "rejection",
            "Malformed values are rejected before import preflight can be designed.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_15_MISSING_VALUES_REJECTED",
            "rejection",
            "Missing values are rejected and cannot be synthesized.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_16_ZERO_VALUE_COUNTS_REQUIRED",
            "zero-count",
            "Supplied, accepted, and imported value counts must remain zero.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_17_CLEANUP_RECEIPT_REQUIRED",
            "receipt",
            "Cleanup receipt metadata is required before future import work.",
            "required-before-import"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_18_IMPORT_FIREWALL_LOCKED",
            "import",
            "Import preview and evidence import remain locked.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_19_RUNTIME_EXECUTION_LOCKED",
            "runtime",
            "Runtime payload, live execution, and production execution remain locked.",
            "fail-closed"),
        policy(
            "APPROVAL_PREFLIGHT_POLICY_20_CLOSEOUT_LOCK_SUMMARY_REQUIRED",
            "closeout",
            "Closeout must restate approval, value, import, runtime, and production locks.",
            "required-before-handoff"));
  }

  static List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy>
      policies(int fromInclusive, int toExclusive) {
    return List.copyOf(allPolicies().subList(fromInclusive, toExclusive));
  }

  private static OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse
          .ApprovalPolicy
      policy(String code, String category, String policy, String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.policy(
        code, category, policy, enforcement);
  }
}
