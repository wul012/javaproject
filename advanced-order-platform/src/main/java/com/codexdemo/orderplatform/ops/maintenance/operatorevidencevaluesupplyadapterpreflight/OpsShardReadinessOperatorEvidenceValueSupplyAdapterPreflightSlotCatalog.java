package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight;

import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyCatalogService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog {

  static final int SLOT_COUNT = 25;
  static final int RULE_COUNT = 18;

  private OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog() {}

  static List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot>
      allSlots() {
    return List.of(
        slot(
            "ADAPTER_PREFLIGHT_01_ENVELOPE_ID_COMPATIBILITY",
            "VALUE_SUPPLY_01_ENVELOPE_ID",
            "compatibility",
            "Verify the future adapter can address an envelope id.",
            "adapter has no implementation target",
            OpsShardReadinessOperatorEvidenceValueSupplyCatalogService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_02_OPERATOR_REFERENCE_COMPATIBILITY",
            "VALUE_SUPPLY_02_OPERATOR_REFERENCE",
            "compatibility",
            "Verify operator references stay metadata-only.",
            "operator reference cannot authorize submission",
            OpsShardReadinessOperatorEvidenceValueSupplyCatalogService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_03_SOURCE_DRAFT_COMPATIBILITY",
            "VALUE_SUPPLY_03_SOURCE_DRAFT_SLOT",
            "compatibility",
            "Verify the adapter maps back to a source draft slot.",
            "source draft mapping is required before implementation",
            OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_04_VALUE_KIND_COMPATIBILITY",
            "VALUE_SUPPLY_04_VALUE_KIND",
            "compatibility",
            "Verify value kind remains a declared type, not a value body.",
            "value body fields remain absent",
            OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_05_REDACTION_CLASSIFICATION",
            "VALUE_SUPPLY_05_REDACTION_CLASSIFICATION",
            "redaction",
            "Require redaction classification before any adapter binding.",
            "unclassified adapter input remains blocked",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_06_CREDENTIAL_VALUE_BLOCK",
            "VALUE_SUPPLY_06_CREDENTIAL_VALUE_BLOCK",
            "redaction",
            "Confirm credential values cannot enter adapter preflight.",
            "credential value handling remains forbidden",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_07_RAW_ENDPOINT_BLOCK",
            "VALUE_SUPPLY_07_RAW_ENDPOINT_BLOCK",
            "redaction",
            "Confirm raw endpoints cannot enter adapter preflight.",
            "raw endpoint parsing remains forbidden",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_08_SECRET_MATERIAL_BLOCK",
            "VALUE_SUPPLY_08_SECRET_MATERIAL_BLOCK",
            "redaction",
            "Confirm secret material cannot enter adapter preflight.",
            "secret material remains absent",
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_09_MISSING_VALUE_REJECTION",
            "VALUE_SUPPLY_09_MISSING_VALUE_POLICY",
            "missing-policy",
            "Reject missing values instead of defaulting them.",
            "missing value cannot be adapted",
            OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_10_BLANK_VALUE_REJECTION",
            "VALUE_SUPPLY_10_BLANK_VALUE_POLICY",
            "missing-policy",
            "Reject blank values before adapter binding.",
            "blank value cannot be normalized into evidence",
            OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_11_MANUAL_ENTRY_LOCK",
            "VALUE_SUPPLY_11_MANUAL_ENTRY_LOCK",
            "missing-policy",
            "Confirm manual entry remains disabled.",
            "manual value entry is still locked",
            OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_12_REVIEWER_REQUIRED",
            "VALUE_SUPPLY_12_REVIEWER_REQUIRED",
            "missing-policy",
            "Require reviewer metadata before adapter design proceeds.",
            "missing reviewer keeps adapter blocked",
            OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_13_PROVENANCE_SOURCE_ID",
            "VALUE_SUPPLY_13_PROVENANCE_SOURCE_ID",
            "provenance",
            "Require a source id for every future adapter input.",
            "source id missing",
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_14_PROVENANCE_EVIDENCE_FILE",
            "VALUE_SUPPLY_14_PROVENANCE_EVIDENCE_FILE",
            "provenance",
            "Require an evidence file id before adapter input review.",
            "evidence file id missing",
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_15_PROVENANCE_SNIPPET_ID",
            "VALUE_SUPPLY_15_PROVENANCE_SNIPPET_ID",
            "provenance",
            "Require a source snippet id before adapter input review.",
            "source snippet id missing",
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_16_ENDPOINT_ALIAS_ONLY",
            "VALUE_SUPPLY_16_SOURCE_ENDPOINT_ALIAS",
            "provenance",
            "Require endpoint aliases instead of raw endpoint values.",
            "raw endpoint alias review incomplete",
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_17_FRESH_SIBLING_REFERENCE",
            "VALUE_SUPPLY_17_FRESH_SIBLING_REFERENCE",
            "source-evidence",
            "Consume Node v936 fresh sibling reference as read-only metadata.",
            "fresh sibling evidence is not an import permission",
            OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_18_HISTORICAL_FALLBACK_MARKER",
            "VALUE_SUPPLY_18_HISTORICAL_FALLBACK_MARKER",
            "source-evidence",
            "Require explicit fallback markers for historical evidence.",
            "fallback cannot synthesize adapter input",
            OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_19_SYNTHETIC_EVIDENCE_BLOCK",
            "VALUE_SUPPLY_19_SYNTHETIC_EVIDENCE_BLOCK",
            "source-evidence",
            "Block synthetic evidence before adapter design.",
            "synthetic evidence remains blocked",
            OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_20_RUNTIME_PAYLOAD_BLOCK",
            "VALUE_SUPPLY_20_RUNTIME_PAYLOAD_BLOCK",
            "payload",
            "Block runtime payloads from adapter preflight.",
            "runtime payload remains locked",
            OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_21_IMPORT_PREVIEW_BLOCK",
            "VALUE_SUPPLY_21_IMPORT_PREVIEW_BLOCK",
            "import",
            "Block import preview until a separate approval packet exists.",
            "import preview remains locked",
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_22_WRITE_SIDE_EFFECT_BLOCK",
            "VALUE_SUPPLY_22_WRITE_SIDE_EFFECT_BLOCK",
            "side-effect",
            "Block state writes and schema mutations.",
            "write side effects remain forbidden",
            OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_23_LIVE_EXECUTION_BLOCK",
            "VALUE_SUPPLY_23_LIVE_EXECUTION_BLOCK",
            "runtime",
            "Block live execution for disabled adapter preflight.",
            "live execution remains locked",
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_24_PRODUCTION_EXECUTION_BLOCK",
            "VALUE_SUPPLY_24_PRODUCTION_EXECUTION_BLOCK",
            "runtime",
            "Block production execution for disabled adapter preflight.",
            "production execution remains locked",
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService.ENDPOINT),
        slot(
            "ADAPTER_PREFLIGHT_25_CLOSEOUT_LOCKS_HELD",
            "VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD",
            "closeout",
            "Close adapter preflight with all locks held.",
            "adapter remains disabled",
            OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService.ENDPOINT));
  }

  static List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot>
      slots(int fromInclusive, int toExclusive) {
    return List.copyOf(allSlots().subList(fromInclusive, toExclusive));
  }

  static List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule>
      allRules() {
    return List.of(
        rule(
            "ADAPTER_RULE_01_DISABLED_IMPLEMENTATION",
            "implementation",
            "The value-supply adapter preflight cannot expose an implementation target.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_02_METADATA_ONLY_COMPATIBILITY",
            "compatibility",
            "Compatibility checks can compare envelope metadata but cannot read value bodies.",
            "metadata-only"),
        rule(
            "ADAPTER_RULE_03_NO_OPERATOR_VALUE_BODY",
            "submission",
            "Operator value body fields remain absent from adapter preflight responses.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_04_NO_APPROVAL_CAPTURE",
            "approval",
            "The preflight can cite Node v986 approval packet draft readiness but cannot capture approval.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_05_NO_CREDENTIAL_VALUE",
            "redaction",
            "Credential values are rejected before any adapter boundary is designed.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_06_NO_RAW_ENDPOINT",
            "redaction",
            "Raw endpoint values stay out of adapter preflight and must remain aliases only.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_07_NO_SECRET_MATERIAL",
            "redaction",
            "Secret material cannot be supplied, echoed, hashed, or persisted by preflight.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_08_PROVENANCE_SOURCE_REQUIRED",
            "provenance",
            "Every future adapter input must bind to a source id before value import can be considered.",
            "required-before-adapter"),
        rule(
            "ADAPTER_RULE_09_PROVENANCE_EVIDENCE_FILE_REQUIRED",
            "provenance",
            "Evidence file ids are required before adapter input review.",
            "required-before-adapter"),
        rule(
            "ADAPTER_RULE_10_PROVENANCE_SNIPPET_REQUIRED",
            "provenance",
            "Evidence snippet ids are required before adapter input review.",
            "required-before-adapter"),
        rule(
            "ADAPTER_RULE_11_MISSING_VALUES_REJECTED",
            "missing-policy",
            "Missing values cannot be defaulted into adapter-ready evidence.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_12_BLANK_VALUES_REJECTED",
            "missing-policy",
            "Blank values are malformed for adapter preflight and cannot become evidence.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_13_NO_AUTOMATIC_SIBLING_IMPORT",
            "source-evidence",
            "Fresh sibling references remain read-only metadata and cannot be auto-imported.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_14_SYNTHETIC_EVIDENCE_BLOCKED",
            "source-evidence",
            "Synthetic evidence cannot satisfy adapter preflight provenance.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_15_RUNTIME_PAYLOAD_BLOCKED",
            "payload",
            "Runtime payload fields remain blocked until a separate live-read gate is proven.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_16_NO_STATE_WRITE",
            "side-effect",
            "Adapter preflight cannot write state, mutate schema, or start services.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_17_NO_IMPORT_OR_LIVE_EXECUTION",
            "runtime",
            "Evidence import, live execution, and production execution remain locked.",
            "fail-closed"),
        rule(
            "ADAPTER_RULE_18_CLOSEOUT_LOCK_SUMMARY_REQUIRED",
            "closeout",
            "Closeout must restate all adapter, submission, import, runtime, and production locks.",
            "required-before-handoff"));
  }

  static List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule>
      rules(int fromInclusive, int toExclusive) {
    return List.copyOf(allRules().subList(fromInclusive, toExclusive));
  }

  private static OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot
      slot(
          String code,
          String sourceSupplySlot,
          String adapterStage,
          String preflightRequirement,
          String blockedReason,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.slot(
        code, sourceSupplySlot, adapterStage, preflightRequirement, blockedReason, sourceEndpoint);
  }

  private static OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule
      rule(String code, String category, String rule, String enforcement) {
    return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.rule(
        code, category, rule, enforcement);
  }
}
