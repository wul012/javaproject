package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog {

  static final int SLOT_COUNT = 25;

  private OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog() {}

  static List<OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot> allSlots() {
    return List.of(
        slot(
            "VALUE_DRAFT_01_SOURCE_WORKSHEET_CLOSEOUT",
            "IMPORT_PREFLIGHT_CATALOG_SOURCE_WORKSHEET_CLOSEOUT",
            "Prepare a draft note for the source worksheet closeout without entering evidence.",
            "actual worksheet closeout value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT),
        slot(
            "VALUE_DRAFT_02_PREFLIGHT_SLOT_COUNT",
            "IMPORT_PREFLIGHT_CATALOG_SLOT_COUNT",
            "Prepare a draft note for the expected twenty-five slot count.",
            "actual slot count value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT),
        slot(
            "VALUE_DRAFT_03_PREFLIGHT_GATE_COUNT",
            "IMPORT_PREFLIGHT_CATALOG_GATE_COUNT",
            "Prepare a draft note for the twenty-four locked gate count.",
            "actual gate count value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT),
        slot(
            "VALUE_DRAFT_04_NO_VALUE_INGESTION",
            "IMPORT_PREFLIGHT_CATALOG_NO_VALUE_INGESTION",
            "Prepare a draft note that confirms no real, synthetic, or runtime values are present.",
            "actual value ingestion evidence is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT),
        slot(
            "VALUE_DRAFT_05_SLOT_ID_NORMALIZATION",
            "IMPORT_PREFLIGHT_SLOT_ID_NORMALIZATION",
            "Prepare a draft note for normalized slot identity.",
            "actual slot identity value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService.ENDPOINT),
        slot(
            "VALUE_DRAFT_06_BLANK_VALUE_NORMALIZATION",
            "IMPORT_PREFLIGHT_BLANK_VALUE_NORMALIZATION",
            "Prepare a draft note for blank value preservation.",
            "actual blank value replacement is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService.ENDPOINT),
        slot(
            "VALUE_DRAFT_07_SCOPE_NAME_NORMALIZATION",
            "IMPORT_PREFLIGHT_SCOPE_NAME_NORMALIZATION",
            "Prepare a draft note for preview scope naming.",
            "actual scope value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService.ENDPOINT),
        slot(
            "VALUE_DRAFT_08_NOTE_TEXT_NORMALIZATION",
            "IMPORT_PREFLIGHT_NOTE_TEXT_NORMALIZATION",
            "Prepare a draft note that stays descriptive and non-evidentiary.",
            "actual operator note value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService.ENDPOINT),
        slot(
            "VALUE_DRAFT_09_MISSING_MANUAL_VALUE_BLOCKER",
            "IMPORT_PREFLIGHT_MISSING_MANUAL_VALUE_BLOCKER",
            "Prepare a draft note for missing manual value blocking.",
            "actual manual value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService.ENDPOINT),
        slot(
            "VALUE_DRAFT_10_REDACTION_BLOCKER",
            "IMPORT_PREFLIGHT_REDACTION_BLOCKER",
            "Prepare a draft note for redaction blocker handling.",
            "actual redacted value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService.ENDPOINT),
        slot(
            "VALUE_DRAFT_11_RUNTIME_PAYLOAD_BLOCKER",
            "IMPORT_PREFLIGHT_RUNTIME_PAYLOAD_BLOCKER",
            "Prepare a draft note for runtime payload rejection.",
            "actual runtime payload is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService.ENDPOINT),
        slot(
            "VALUE_DRAFT_12_UNMAPPED_SCOPE_BLOCKER",
            "IMPORT_PREFLIGHT_UNMAPPED_SCOPE_BLOCKER",
            "Prepare a draft note for unmapped target scope rejection.",
            "actual target scope value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService.ENDPOINT),
        slot(
            "VALUE_DRAFT_13_MANUAL_ENTRY_LOCK_BLOCKER",
            "IMPORT_PREFLIGHT_MANUAL_ENTRY_LOCK_BLOCKER",
            "Prepare a draft note for the manual entry lock.",
            "actual manual entry value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService.ENDPOINT),
        slot(
            "VALUE_DRAFT_14_CREDENTIAL_REDACTION",
            "IMPORT_PREFLIGHT_CREDENTIAL_REDACTION_PRESERVED",
            "Prepare a draft note for credential redaction preservation.",
            "actual credential value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService.ENDPOINT),
        slot(
            "VALUE_DRAFT_15_RAW_ENDPOINT_REDACTION",
            "IMPORT_PREFLIGHT_RAW_ENDPOINT_REDACTION_PRESERVED",
            "Prepare a draft note for raw endpoint redaction preservation.",
            "actual raw endpoint value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService.ENDPOINT),
        slot(
            "VALUE_DRAFT_16_ABSENCE_MARKER",
            "IMPORT_PREFLIGHT_ABSENCE_MARKER_PRESERVED",
            "Prepare a draft note for missing evidence markers.",
            "actual absence replacement value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService.ENDPOINT),
        slot(
            "VALUE_DRAFT_17_BLANK_SLOT_GUARD",
            "IMPORT_PREFLIGHT_BLANK_SLOT_GUARD",
            "Prepare a draft note that keeps blank slots unresolved.",
            "actual blank slot value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService.ENDPOINT),
        slot(
            "VALUE_DRAFT_18_MISSING_REVIEWER_GUARD",
            "IMPORT_PREFLIGHT_MISSING_REVIEWER_GUARD",
            "Prepare a draft note for missing reviewer ownership.",
            "actual reviewer evidence value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService.ENDPOINT),
        slot(
            "VALUE_DRAFT_19_MISSING_SCOPE_GUARD",
            "IMPORT_PREFLIGHT_MISSING_SCOPE_GUARD",
            "Prepare a draft note for missing scope blocking.",
            "actual scope evidence value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService.ENDPOINT),
        slot(
            "VALUE_DRAFT_20_MISSING_SOURCE_GUARD",
            "IMPORT_PREFLIGHT_MISSING_SOURCE_GUARD",
            "Prepare a draft note for missing source endpoint blocking.",
            "actual source evidence value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService.ENDPOINT),
        slot(
            "VALUE_DRAFT_21_READ_MODEL_SCOPE",
            "IMPORT_PREFLIGHT_READ_MODEL_SCOPE",
            "Prepare a draft note for read-model preview scope.",
            "actual read-model value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService.ENDPOINT),
        slot(
            "VALUE_DRAFT_22_PREVIEW_WINDOW_SCOPE",
            "IMPORT_PREFLIGHT_PREVIEW_WINDOW_SCOPE",
            "Prepare a draft note for preview window scope.",
            "actual preview window value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService.ENDPOINT),
        slot(
            "VALUE_DRAFT_23_REVIEW_PACKAGE_SCOPE",
            "IMPORT_PREFLIGHT_REVIEW_PACKAGE_SCOPE",
            "Prepare a draft note for review package scope.",
            "actual review package value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService.ENDPOINT),
        slot(
            "VALUE_DRAFT_24_OPERATOR_SLOT_SCOPE",
            "IMPORT_PREFLIGHT_OPERATOR_SLOT_SCOPE",
            "Prepare a draft note for operator slot scope.",
            "actual operator slot value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService.ENDPOINT),
        slot(
            "VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD",
            "IMPORT_PREFLIGHT_CLOSEOUT_LOCKS_HELD",
            "Prepare a draft note that all import and execution locks remain held.",
            "actual closeout value is not supplied",
            OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService.ENDPOINT));
  }

  static List<OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot> slots(
      int fromInclusive, int toExclusive) {
    return List.copyOf(allSlots().subList(fromInclusive, toExclusive));
  }

  private static OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot slot(
      String code,
      String sourceSlot,
      String instruction,
      String draftValueBoundary,
      String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueDraftSupport.slot(
        code, sourceSlot, instruction, draftValueBoundary, sourceEndpoint);
  }
}
