package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog {

  static final int SLOT_COUNT = 25;
  private static final String PROJECT = "advanced-order-platform";
  private static final String SOURCE_PLAN = "Node v936";
  private static final String SOURCE_DRAFT_VERSION = "Java v633";
  private static final String ENVELOPE_STATE = "disabled-design";
  private static final String SUPPLIED_VALUE_STATE = "not-accepted";
  private static final String REDACTION_STATE = "redact-before-storage";
  private static final String PROVENANCE_STATE = "required-before-import";

  private OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog() {}

  static List<OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot> allSlots() {
    return List.of(
        slot(
            "VALUE_SUPPLY_01_ENVELOPE_ID",
            "VALUE_DRAFT_01_SOURCE_WORKSHEET_CLOSEOUT",
            OpsShardReadinessOperatorEvidenceValueDraftCloseoutService.ENDPOINT,
            "Assign a stable envelope id without binding an operator value.",
            "identifier only; no value field is accepted",
            "trace to Java v633 value-draft closeout"),
        slot(
            "VALUE_SUPPLY_02_OPERATOR_REFERENCE",
            "VALUE_DRAFT_02_PREFLIGHT_SLOT_COUNT",
            OpsShardReadinessOperatorEvidenceValueDraftCatalogService.ENDPOINT,
            "Reserve an operator reference field for a future disabled adapter.",
            "operator reference cannot authorize value import",
            "trace to the value-draft catalog slot count"),
        slot(
            "VALUE_SUPPLY_03_SOURCE_DRAFT_SLOT",
            "VALUE_DRAFT_03_PREFLIGHT_GATE_COUNT",
            OpsShardReadinessOperatorEvidenceValueDraftCatalogService.ENDPOINT,
            "Require each envelope entry to name its source draft slot.",
            "source slot is metadata, not a supplied value",
            "trace to the frozen draft gate catalog"),
        slot(
            "VALUE_SUPPLY_04_VALUE_KIND",
            "VALUE_DRAFT_04_NO_VALUE_INGESTION",
            OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService.ENDPOINT,
            "Declare a non-secret value kind before any value can be reviewed.",
            "kind declaration cannot carry the value itself",
            "trace to the no-ingestion draft template"),
        slot(
            "VALUE_SUPPLY_05_REDACTION_CLASSIFICATION",
            "VALUE_DRAFT_10_REDACTION_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService.ENDPOINT,
            "Classify redaction before accepting an envelope field.",
            "unclassified values remain rejected",
            "trace to the draft redaction blocker"),
        slot(
            "VALUE_SUPPLY_06_CREDENTIAL_VALUE_BLOCK",
            "VALUE_DRAFT_14_CREDENTIAL_REDACTION",
            OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService.ENDPOINT,
            "Keep credential values outside the envelope.",
            "credential values are never accepted",
            "trace to credential redaction evidence"),
        slot(
            "VALUE_SUPPLY_07_RAW_ENDPOINT_BLOCK",
            "VALUE_DRAFT_15_RAW_ENDPOINT_REDACTION",
            OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService.ENDPOINT,
            "Keep raw endpoint URLs outside the envelope.",
            "raw endpoint values are never accepted",
            "trace to raw endpoint redaction evidence"),
        slot(
            "VALUE_SUPPLY_08_SECRET_MATERIAL_BLOCK",
            "VALUE_DRAFT_16_ABSENCE_MARKER",
            OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService.ENDPOINT,
            "Require an absence marker instead of secret material.",
            "secret material is replaced by absence metadata",
            "trace to absence marker evidence"),
        slot(
            "VALUE_SUPPLY_09_MISSING_VALUE_POLICY",
            "VALUE_DRAFT_09_MISSING_MANUAL_VALUE_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService.ENDPOINT,
            "Define the missing-value state before any value submission.",
            "missing remains blocked, not defaulted",
            "trace to the missing manual value blocker"),
        slot(
            "VALUE_SUPPLY_10_BLANK_VALUE_POLICY",
            "VALUE_DRAFT_06_BLANK_VALUE_NORMALIZATION",
            OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService.ENDPOINT,
            "Define blank value normalization as a rejection rule.",
            "blank values are rejected, not normalized into evidence",
            "trace to blank value normalization evidence"),
        slot(
            "VALUE_SUPPLY_11_MANUAL_ENTRY_LOCK",
            "VALUE_DRAFT_13_MANUAL_ENTRY_LOCK_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService.ENDPOINT,
            "Keep manual entry disabled until a separate approval exists.",
            "manual entry remains locked",
            "trace to manual entry lock evidence"),
        slot(
            "VALUE_SUPPLY_12_REVIEWER_REQUIRED",
            "VALUE_DRAFT_18_MISSING_REVIEWER_GUARD",
            OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService.ENDPOINT,
            "Require reviewer identity before value import can be designed.",
            "missing reviewer keeps the envelope blocked",
            "trace to missing reviewer guard evidence"),
        slot(
            "VALUE_SUPPLY_13_PROVENANCE_SOURCE_ID",
            "VALUE_DRAFT_21_READ_MODEL_SCOPE",
            OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService.ENDPOINT,
            "Require a source evidence id for each future supplied value.",
            "values without source id are rejected",
            "trace to read-model scope evidence"),
        slot(
            "VALUE_SUPPLY_14_PROVENANCE_EVIDENCE_FILE",
            "VALUE_DRAFT_20_MISSING_SOURCE_GUARD",
            OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService.ENDPOINT,
            "Require a source evidence file reference.",
            "values without evidence file remain blocked",
            "trace to missing source guard evidence"),
        slot(
            "VALUE_SUPPLY_15_PROVENANCE_SNIPPET_ID",
            "VALUE_DRAFT_19_MISSING_SCOPE_GUARD",
            OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService.ENDPOINT,
            "Require a source snippet id for review.",
            "values without snippet id remain blocked",
            "trace to missing scope guard evidence"),
        slot(
            "VALUE_SUPPLY_16_SOURCE_ENDPOINT_ALIAS",
            "VALUE_DRAFT_22_PREVIEW_WINDOW_SCOPE",
            OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService.ENDPOINT,
            "Use endpoint aliases instead of raw endpoints.",
            "raw endpoint strings are rejected",
            "trace to preview window scope evidence"),
        slot(
            "VALUE_SUPPLY_17_FRESH_SIBLING_REFERENCE",
            "VALUE_DRAFT_23_REVIEW_PACKAGE_SCOPE",
            OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService.ENDPOINT,
            "Record Node v936 fresh sibling intake as a provenance precondition.",
            "fresh sibling evidence is read-only metadata",
            "trace to review package scope evidence"),
        slot(
            "VALUE_SUPPLY_18_HISTORICAL_FALLBACK_MARKER",
            "VALUE_DRAFT_24_OPERATOR_SLOT_SCOPE",
            OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService.ENDPOINT,
            "Mark fallback evidence explicitly when fresh evidence is unavailable.",
            "fallback markers cannot synthesize values",
            "trace to operator slot scope evidence"),
        slot(
            "VALUE_SUPPLY_19_SYNTHETIC_EVIDENCE_BLOCK",
            "VALUE_DRAFT_17_BLANK_SLOT_GUARD",
            OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService.ENDPOINT,
            "Reject synthetic evidence inside the value envelope.",
            "synthetic evidence remains blocked",
            "trace to blank slot guard evidence"),
        slot(
            "VALUE_SUPPLY_20_RUNTIME_PAYLOAD_BLOCK",
            "VALUE_DRAFT_11_RUNTIME_PAYLOAD_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService.ENDPOINT,
            "Reject runtime payloads at the envelope boundary.",
            "runtime payloads are never accepted",
            "trace to runtime payload blocker evidence"),
        slot(
            "VALUE_SUPPLY_21_IMPORT_PREVIEW_BLOCK",
            "VALUE_DRAFT_12_UNMAPPED_SCOPE_BLOCKER",
            OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService.ENDPOINT,
            "Keep evidence import preview disabled.",
            "import remains locked",
            "trace to unmapped scope blocker evidence"),
        slot(
            "VALUE_SUPPLY_22_WRITE_SIDE_EFFECT_BLOCK",
            "VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD",
            OpsShardReadinessOperatorEvidenceValueDraftCloseoutService.ENDPOINT,
            "Block write side effects, ledger writes, and schema mutations.",
            "writes are never accepted",
            "trace to closeout locks-held evidence"),
        slot(
            "VALUE_SUPPLY_23_LIVE_EXECUTION_BLOCK",
            "VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD",
            OpsShardReadinessOperatorEvidenceValueDraftCloseoutService.ENDPOINT,
            "Keep live execution blocked for the disabled envelope.",
            "live execution remains false",
            "trace to closeout live lock evidence"),
        slot(
            "VALUE_SUPPLY_24_PRODUCTION_EXECUTION_BLOCK",
            "VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD",
            OpsShardReadinessOperatorEvidenceValueDraftCloseoutService.ENDPOINT,
            "Keep production execution blocked for the disabled envelope.",
            "production execution remains false",
            "trace to closeout production lock evidence"),
        slot(
            "VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD",
            "VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD",
            OpsShardReadinessOperatorEvidenceValueDraftCloseoutService.ENDPOINT,
            "Close the disabled envelope package with all locks held.",
            "no value, import, runtime, or production path is enabled",
            "trace to Java v633 closeout"));
  }

  static List<OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot> slots(
      int fromInclusive, int toExclusive) {
    return List.copyOf(allSlots().subList(fromInclusive, toExclusive));
  }

  static OpsShardReadinessOperatorEvidenceValueSupplyResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot> slots,
      List<String> additionalChecks) {
    List<OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot> slotCopy =
        List.copyOf(slots);
    int passedSlotCount =
        (int) slotCopy.stream().filter(slot -> "passed".equals(slot.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("value-supply-slot-count-" + slotCopy.size());
    checks.add("value-supply-passed-slot-count-" + passedSlotCount);
    checks.add("value-supply-source-plan-" + SOURCE_PLAN);
    checks.add("value-supply-source-draft-" + SOURCE_DRAFT_VERSION);
    checks.add("value-supply-envelope-disabled-design-ready");
    checks.add("value-supply-supplied-value-state-not-accepted");
    checks.add("value-supply-operator-submission-locked");
    checks.add("value-supply-evidence-import-locked");
    checks.add("value-supply-runtime-payload-locked");
    checks.add("value-supply-production-execution-locked");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessOperatorEvidenceValueSupplyResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_DRAFT_VERSION,
        ENVELOPE_STATE,
        SUPPLIED_VALUE_STATE,
        REDACTION_STATE,
        PROVENANCE_STATE,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        slotCopy.size(),
        passedSlotCount,
        slotCopy,
        List.copyOf(checks),
        passedSlotCount == slotCopy.size() ? "passed" : "blocked");
  }

  static OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot slot(
      String code,
      String sourceDraftSlot,
      String evidenceSource,
      String envelopeRequirement,
      String valuePolicy,
      String provenanceRequirement) {
    return new OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot(
        code,
        sourceDraftSlot,
        evidenceSource,
        envelopeRequirement,
        valuePolicy,
        provenanceRequirement,
        "passed");
  }
}
