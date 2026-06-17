package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetCloseoutService {
  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CLOSEOUT;
  static final String PROFILE = "java-shard-readiness-manual-evidence-worksheet-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse closeout() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v582",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "foundation-complete",
                "release-reviewer",
                "catalog, template, validation, redaction, missing value, and scope are complete",
                OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "assurance-complete",
                "release-reviewer",
                "preflight, route profile, archive plan, handoff, and CI budget are complete",
                OpsShardReadinessManualEvidenceWorksheetCiBudgetService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "execution-locks-held",
                "runtime-boundary-reviewer",
                "manual entry, live execution, and production execution remain locked",
                OpsShardReadinessManualEvidenceWorksheetImporterPreflightService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "node-v861-alignment",
                "operator-worksheet-maintainer",
                "Java mirrors worksheet readiness without consuming fresh Node runtime values",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "final-ci-gate-ready",
                "ci-maintainer",
                "focused checks and full Maven gate are ready for batch closeout",
                OpsShardReadinessManualEvidenceWorksheetCiBudgetService.ENDPOINT)),
        List.of(
            "worksheet-closeout-versions-v559-v583",
            "worksheet-closeout-foundation-and-assurance-split",
            "worksheet-closeout-operator-entry-only"));
  }
}
