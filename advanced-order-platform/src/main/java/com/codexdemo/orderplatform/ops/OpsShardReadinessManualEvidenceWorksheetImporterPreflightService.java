package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetImporterPreflightService {
  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_IMPORTER_PREFLIGHT;
  static final String PROFILE =
      "java-shard-readiness-manual-evidence-worksheet-importer-preflight.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse preflight() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v572",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "worksheet-structure-ready",
                "importer-reviewer",
                "catalog and slot template are present for a future importer",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "manual-values-absent",
                "runtime-boundary-reviewer",
                "manual evidence values are still absent and locked",
                OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "validation-rules-present",
                "validation-reviewer",
                "future importer can reuse validation rules without inventing them",
                OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "target-scopes-present",
                "target-scope-maintainer",
                "target scopes are named without activating routing",
                OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "import-blocker",
                "release-reviewer",
                "preflight explicitly blocks importer execution",
                OpsShardReadinessManualEvidenceWorksheetRedactionRulesService.ENDPOINT)),
        List.of(
            "importer-preflight-structure-ready",
            "importer-preflight-manual-values-absent",
            "importer-preflight-import-execution-blocked"));
  }
}
