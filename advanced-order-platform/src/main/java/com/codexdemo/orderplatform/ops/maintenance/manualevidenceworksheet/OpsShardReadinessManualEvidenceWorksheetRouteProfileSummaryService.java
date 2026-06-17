package com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService {
  public static final String ENDPOINT =
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.BASE_PATH
          + OpsShardReadinessManualEvidenceWorksheetRoutePaths
              .MANUAL_EVIDENCE_WORKSHEET_ROUTE_PROFILE_SUMMARY;
  static final String PROFILE =
      "java-shard-readiness-manual-evidence-worksheet-route-profile-summary.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessManualEvidenceWorksheetResponse summary() {
    return OpsShardReadinessManualEvidenceWorksheetSupport.response(
        "Java v574",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "foundation-route-profile",
                "route-catalog-maintainer",
                "foundation routes cover catalog, template, rules, policy, and scope",
                OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "assurance-route-profile",
                "route-catalog-maintainer",
                "assurance routes start with importer preflight and remain read-only",
                OpsShardReadinessManualEvidenceWorksheetImporterPreflightService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "json-contract-profile",
                "contract-maintainer",
                "JSON fields expose readiness flags and item checks",
                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
            OpsShardReadinessManualEvidenceWorksheetSupport.item(
                "route-boundary-profile",
                "runtime-boundary-reviewer",
                "routes are GET-only evidence and do not accept payloads",
                OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT)),
        List.of(
            "route-profile-summary-foundation-routes-6",
            "route-profile-summary-assurance-routes-started",
            "route-profile-summary-get-only"));
  }
}
