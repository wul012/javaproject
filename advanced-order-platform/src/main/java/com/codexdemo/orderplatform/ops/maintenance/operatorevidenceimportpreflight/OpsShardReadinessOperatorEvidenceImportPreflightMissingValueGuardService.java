package com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight;

import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetSlotTemplateService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
              .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_MISSING_VALUE_GUARD;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-missing-value-guard.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse guard() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v593",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "blank-slot-guard",
                "operator-reviewer",
                "blank evidence slots remain unresolved",
                OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "missing-reviewer-guard",
                "release-reviewer",
                "missing reviewer ownership blocks import approval",
                OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "missing-scope-guard",
                "target-scope-reviewer",
                "missing target scope blocks preview mapping",
                OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "missing-source-guard",
                "import-preflight-maintainer",
                "missing source endpoint blocks evidence import",
                OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService.ENDPOINT)),
        List.of(
            "missing-value-guard-keeps-preflight-ready",
            "missing-value-guard-keeps-import-locked",
            "missing-value-guard-no-synthetic-values"));
  }
}
