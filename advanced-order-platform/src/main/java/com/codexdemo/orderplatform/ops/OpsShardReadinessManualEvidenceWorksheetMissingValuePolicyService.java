package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService {
  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_MISSING_VALUE_POLICY;
  static final String PROFILE =
      "java-shard-readiness-manual-evidence-worksheet-missing-value-policy.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse policy() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v568",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "missing-manual-value",
                "operator-worksheet-maintainer",
                "blank manual value keeps worksheet ready but evidence entry locked",
                OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "missing-owner-review",
                "release-reviewer",
                "missing reviewer blocks import readiness",
                OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "missing-target-scope",
                "target-scope-maintainer",
                "missing scope blocks any future importer mapping",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "missing-import-source",
                "runtime-boundary-reviewer",
                "no import source is accepted in the worksheet-only phase",
                OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT)),
        List.of(
            "missing-value-policy-keeps-worksheet-ready",
            "missing-value-policy-blocks-manual-entry",
            "missing-value-policy-blocks-importer"));
  }
}
