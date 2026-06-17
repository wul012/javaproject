package com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight;

import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetRedactionRulesService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
              .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_REDACTION_PRESERVATION;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-redaction-preservation.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse preservation() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v591",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "credential-redaction-preserved",
                "security-reviewer",
                "credential placeholders remain redacted and never become values",
                OpsShardReadinessManualEvidenceWorksheetRedactionRulesService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "raw-endpoint-redaction-preserved",
                "security-reviewer",
                "raw endpoint placeholders remain descriptive only",
                OpsShardReadinessManualEvidenceWorksheetRedactionRulesService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "absence-marker-preserved",
                "operator-reviewer",
                "missing evidence markers remain absence markers",
                OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "review-text-boundary-preserved",
                "release-reviewer",
                "review text explains why import stays blocked",
                OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService.ENDPOINT)),
        List.of(
            "redaction-preservation-no-secret-material",
            "redaction-preservation-no-raw-endpoints",
            "redaction-preservation-absence-markers-only"));
  }
}
