package com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight;

import com.codexdemo.orderplatform.ops.OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService {
  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
              .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ROUTE_PROFILE_SUMMARY;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-import-preflight-route-profile-summary.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse summary() {
    return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
        "Java v599",
        ENDPOINT,
        PROFILE,
        List.of(
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "foundation-route-profile",
                "import-preflight-maintainer",
                "six foundation routes cover catalog, normalization, blockers, redaction, missing values, and scopes",
                OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "assurance-route-profile",
                "release-reviewer",
                "six assurance routes cover digest, profile, archive, handoff, ci, and closeout",
                OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "json-contract-profile",
                "consumer-contract-reviewer",
                "all routes return the operator evidence import preflight response contract",
                OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT),
            OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                "get-only-boundary",
                "runtime-boundary-reviewer",
                "routes are GET-only and do not accept request payloads",
                OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService.ENDPOINT)),
        List.of(
            "import-preflight-route-profile-foundation-routes-6",
            "import-preflight-route-profile-assurance-routes-6",
            "import-preflight-route-profile-get-only"));
  }
}
