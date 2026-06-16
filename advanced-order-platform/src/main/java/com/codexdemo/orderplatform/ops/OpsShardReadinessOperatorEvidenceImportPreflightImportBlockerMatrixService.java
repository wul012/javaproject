package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_IMPORT_BLOCKER_MATRIX;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-import-blocker-matrix.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse matrix() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v589",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "missing-manual-value-blocker",
                "operator-reviewer",
                "blank worksheet values block evidence import",
                OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "redaction-blocker",
                "security-reviewer",
                "credential and raw endpoint placeholders block import",
                OpsShardReadinessManualEvidenceWorksheetRedactionRulesService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "runtime-payload-blocker",
                "runtime-boundary-reviewer",
                "runtime payloads are not accepted by import preflight",
                OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "unmapped-scope-blocker",
                "target-scope-reviewer",
                "unmapped target scopes block operator evidence import",
                OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "manual-entry-lock-blocker",
                "release-reviewer",
                "manual evidence entry remains locked after preflight readiness",
                OpsShardReadinessManualEvidenceWorksheetImporterPreflightService.ENDPOINT)),
        List.of(
            "import-blocker-matrix-blocker-count-5",
            "import-blocker-matrix-blocks-runtime-payload",
            "import-blocker-matrix-keeps-import-locked"));
  }
}
