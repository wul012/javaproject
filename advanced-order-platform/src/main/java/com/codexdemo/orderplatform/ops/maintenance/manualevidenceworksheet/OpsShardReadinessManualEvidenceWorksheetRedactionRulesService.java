package com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetRedactionRulesService {
  public static final String ENDPOINT =
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.BASE_PATH
          + OpsShardReadinessManualEvidenceWorksheetRoutePaths
              .MANUAL_EVIDENCE_WORKSHEET_REDACTION_RULES;
  static final String PROFILE = "java-shard-readiness-manual-evidence-worksheet-redaction-rules.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse rules() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v566",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "credential-value-ban",
                "security-reviewer",
                "credential values are never worksheet fields",
                OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "raw-endpoint-ban",
                "runtime-boundary-reviewer",
                "raw endpoint values stay outside the worksheet",
                OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "placeholder-policy",
                "operator-worksheet-maintainer",
                "redaction placeholders describe absence, not masked secrets",
                OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "review-text-boundary",
                "release-reviewer",
                "review text may describe redaction rules but not secret content",
                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
                    .ENDPOINT)),
        List.of(
            "redaction-rules-ban-credential-values",
            "redaction-rules-ban-raw-endpoints",
            "redaction-rules-placeholders-are-absence-markers"));
  }
}
