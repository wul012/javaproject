package com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight;

import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetSlotTemplateService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetValidationRulesService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
              .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_SLOT_NORMALIZATION;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-slot-normalization.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse normalization() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v587",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "slot-id-normalization",
                "import-preflight-maintainer",
                "slot ids are normalized from worksheet structure only",
                OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "blank-value-normalization",
                "security-reviewer",
                "blank value markers stay blank and are never replaced",
                OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "scope-name-normalization",
                "target-scope-reviewer",
                "scope names map to preview scopes without write routing",
                OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "note-text-normalization",
                "operator-reviewer",
                "operator notes stay descriptive and do not become evidence values",
                OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT)),
        List.of(
            "slot-normalization-preserves-blank-values",
            "slot-normalization-does-not-trim-secrets",
            "slot-normalization-does-not-import-values"));
  }
}
