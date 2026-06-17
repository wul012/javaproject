package com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionLiveReadGateService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService {
  public static final String ENDPOINT =
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.BASE_PATH
          + OpsShardReadinessManualEvidenceWorksheetRoutePaths
              .MANUAL_EVIDENCE_WORKSHEET_OPERATOR_HANDOFF;
  static final String PROFILE =
      "java-shard-readiness-manual-evidence-worksheet-operator-handoff.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse handoff() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v578",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "worksheet-owner",
                "operator-worksheet-maintainer",
                "owns blank slot structure and operator notes",
                OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "validation-owner",
                "validation-reviewer",
                "owns accepted empty state and rejected runtime payload rules",
                OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "archive-owner",
                "archive-reviewer",
                "owns external JSON capture and digest plan",
                OpsShardReadinessManualEvidenceWorksheetArchivePlanService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "importer-owner",
                "importer-reviewer",
                "owns future importer preflight but not execution",
                OpsShardReadinessManualEvidenceWorksheetImporterPreflightService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "runtime-boundary-owner",
                "runtime-boundary-reviewer",
                "keeps live and production execution locked",
                OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT)),
        List.of(
            "operator-handoff-owner-count-5",
            "operator-handoff-no-manual-values",
            "operator-handoff-no-runtime-approval"));
  }
}
