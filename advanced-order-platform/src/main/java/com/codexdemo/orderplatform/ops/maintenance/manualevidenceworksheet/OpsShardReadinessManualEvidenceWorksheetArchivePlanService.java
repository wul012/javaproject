package com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet;

import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetArchivePlanService {
  public static final String ENDPOINT =
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.BASE_PATH
          + OpsShardReadinessManualEvidenceWorksheetRoutePaths
              .MANUAL_EVIDENCE_WORKSHEET_ARCHIVE_PLAN;
  static final String PROFILE = "java-shard-readiness-manual-evidence-worksheet-archive-plan.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse plan() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v576",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "route-json-capture",
                "archive-reviewer",
                "archive should capture route JSON output externally",
                OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "digest-record",
                "archive-reviewer",
                "future archive records should include digest metadata",
                OpsShardReadinessManualEvidenceWorksheetImporterPreflightService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "artifact-location",
                "release-reviewer",
                "artifact location remains a plan, not a Java file write",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "no-file-write",
                "runtime-boundary-reviewer",
                "service does not create, modify, or verify archive files",
                OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT)),
        List.of(
            "archive-plan-captures-json-externally",
            "archive-plan-does-not-write-files",
            "archive-plan-ready-for-route"));
  }
}
