package com.codexdemo.orderplatform.ops.maintenance.approvalpreflight;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v986";
  static final String SOURCE_ENVELOPE_VERSION = "Node v961";
  static final String SOURCE_VALUE_SUPPLY_VERSION = "Java v658";
  static final String SOURCE_ADAPTER_PREFLIGHT_VERSION = "Java v684";
  static final String APPROVAL_PACKET_STATE = "draft-preflight";
  static final String APPROVAL_CAPTURE_STATE = "not-captured";
  static final String ACCEPTED_VALUE_STATE = "not-accepted";
  static final String IMPORT_STATE = "locked";
  static final String REDACTION_DIGEST_STATE = "required-before-capture";
  static final String PROVENANCE_STATE = "required-before-import";
  static final String MALFORMED_VALUE_STATE = "rejected";
  static final String RECEIPT_STATE = "required-before-import";

  private OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport() {}

  static OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem>
          items,
      List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy>
          policies,
      List<String> additionalChecks) {
    List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem>
        itemCopy = List.copyOf(items);
    List<OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy>
        policyCopy = List.copyOf(policies);
    int passedItemCount =
        (int) itemCopy.stream().filter(item -> "passed".equals(item.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("value-supply-approval-preflight-item-count-" + itemCopy.size());
    checks.add("value-supply-approval-preflight-passed-item-count-" + passedItemCount);
    checks.add("value-supply-approval-preflight-policy-count-" + policyCopy.size());
    checks.add("value-supply-approval-preflight-source-plan-" + SOURCE_PLAN);
    checks.add("value-supply-approval-preflight-source-envelope-" + SOURCE_ENVELOPE_VERSION);
    checks.add(
        "value-supply-approval-preflight-source-value-supply-" + SOURCE_VALUE_SUPPLY_VERSION);
    checks.add(
        "value-supply-approval-preflight-source-adapter-preflight-"
            + SOURCE_ADAPTER_PREFLIGHT_VERSION);
    checks.add("value-supply-approval-preflight-draft-only");
    checks.add("value-supply-approval-preflight-approval-not-captured");
    checks.add("value-supply-approval-preflight-approval-grant-locked");
    checks.add("value-supply-approval-preflight-values-not-accepted");
    checks.add("value-supply-approval-preflight-import-locked");
    checks.add("value-supply-approval-preflight-runtime-locked");
    checks.add("value-supply-approval-preflight-live-execution-locked");
    checks.add("value-supply-approval-preflight-production-locked");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_ENVELOPE_VERSION,
        SOURCE_VALUE_SUPPLY_VERSION,
        SOURCE_ADAPTER_PREFLIGHT_VERSION,
        APPROVAL_PACKET_STATE,
        APPROVAL_CAPTURE_STATE,
        ACCEPTED_VALUE_STATE,
        IMPORT_STATE,
        REDACTION_DIGEST_STATE,
        PROVENANCE_STATE,
        MALFORMED_VALUE_STATE,
        RECEIPT_STATE,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        itemCopy.size(),
        passedItemCount,
        policyCopy.size(),
        itemCopy,
        policyCopy,
        List.copyOf(checks),
        passedItemCount == itemCopy.size() ? "passed" : "blocked");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem item(
      String code,
      String sourceEnvelopeSlot,
      String packetStage,
      String packetRequirement,
      String blockedReason,
      String evidenceFileId,
      String evidenceSnippetId,
      String sourceEndpoint) {
    return new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem(
        code,
        sourceEnvelopeSlot,
        packetStage,
        packetRequirement,
        blockedReason,
        evidenceFileId,
        evidenceSnippetId,
        sourceEndpoint,
        "passed");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy
      policy(String code, String category, String policy, String enforcement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy(
        code, category, policy, enforcement);
  }
}
