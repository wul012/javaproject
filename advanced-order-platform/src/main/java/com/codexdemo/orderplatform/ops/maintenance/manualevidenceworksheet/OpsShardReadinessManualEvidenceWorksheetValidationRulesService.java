package com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionLiveReadGateService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetValidationRulesService {
  public static final String ENDPOINT =
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.BASE_PATH
          + OpsShardReadinessManualEvidenceWorksheetRoutePaths
              .MANUAL_EVIDENCE_WORKSHEET_VALIDATION_RULES;
  static final String PROFILE =
      "java-shard-readiness-manual-evidence-worksheet-validation-rules.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse rules() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v564",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "required-slot-id",
                "validation-reviewer",
                "each worksheet slot must keep a stable id",
                OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "accepted-empty-state",
                "validation-reviewer",
                "blank manual value is valid for worksheet readiness",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "rejected-runtime-value",
                "runtime-boundary-reviewer",
                "runtime probe result is not an accepted worksheet value",
                OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "reviewer-note-length",
                "operator-worksheet-maintainer",
                "notes are bounded descriptive text and not execution input",
                OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.ENDPOINT)),
        List.of(
            "validation-rules-allow-empty-manual-value",
            "validation-rules-reject-runtime-payload",
            "validation-rules-do-not-import-values"));
  }
}
