package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightCatalogService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CATALOG;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-catalog.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse catalog() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v585",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "source-worksheet-closeout",
                "import-preflight-maintainer",
                "manual evidence worksheet closeout is the only source contract",
                OpsShardReadinessManualEvidenceWorksheetCloseoutService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "preflight-slot-count",
                "import-preflight-maintainer",
                "twenty-five import-preflight slots are expected",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "gate-count",
                "runtime-boundary-reviewer",
                "twenty-four import preflight gates stay locked",
                OpsShardReadinessManualEvidenceWorksheetImporterPreflightService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "import-locks",
                "release-reviewer",
                "evidence import, manual entry, live execution, and production execution remain locked",
                OpsShardReadinessManualEvidenceWorksheetCloseoutService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "no-value-ingestion",
                "security-reviewer",
                "preflight catalog carries no real, synthetic, secret, or runtime values",
                OpsShardReadinessManualEvidenceWorksheetRedactionRulesService.ENDPOINT)),
        List.of(
            "import-preflight-catalog-slot-count-25",
            "import-preflight-catalog-gate-count-24",
            "import-preflight-catalog-imports-no-values"));
  }
}
