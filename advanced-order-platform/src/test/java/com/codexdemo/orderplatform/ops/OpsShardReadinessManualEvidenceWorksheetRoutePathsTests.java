package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetCiBudgetService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetImporterPreflightService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetRedactionRulesService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetSlotTemplateService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetValidationRulesService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessManualEvidenceWorksheetRoutePathsTests {

  @Test
  void manualEvidenceWorksheetEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CATALOG,
                    OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_SLOT_TEMPLATE,
                    OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_VALIDATION_RULES,
                    OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_REDACTION_RULES,
                    OpsShardReadinessManualEvidenceWorksheetRedactionRulesService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_MISSING_VALUE_POLICY,
                    OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_TARGET_SCOPE_REGISTRY,
                    OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_IMPORTER_PREFLIGHT,
                    OpsShardReadinessManualEvidenceWorksheetImporterPreflightService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_ROUTE_PROFILE_SUMMARY,
                    OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_ARCHIVE_PLAN,
                    OpsShardReadinessManualEvidenceWorksheetArchivePlanService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_OPERATOR_HANDOFF,
                    OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CI_BUDGET,
                    OpsShardReadinessManualEvidenceWorksheetCiBudgetService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CLOSEOUT,
                    OpsShardReadinessManualEvidenceWorksheetCloseoutService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
  }

  @Test
  void manualEvidenceWorksheetRoutesDelegateToSplitOwner() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CATALOG,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_CATALOG),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_SLOT_TEMPLATE,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_SLOT_TEMPLATE),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_VALIDATION_RULES,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_VALIDATION_RULES),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_REDACTION_RULES,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_REDACTION_RULES),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_MISSING_VALUE_POLICY,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_MISSING_VALUE_POLICY),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_TARGET_SCOPE_REGISTRY,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_TARGET_SCOPE_REGISTRY),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_IMPORTER_PREFLIGHT,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_IMPORTER_PREFLIGHT),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_ROUTE_PROFILE_SUMMARY,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_ROUTE_PROFILE_SUMMARY),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_ARCHIVE_PLAN,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_ARCHIVE_PLAN),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_OPERATOR_HANDOFF,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_OPERATOR_HANDOFF),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CI_BUDGET,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_CI_BUDGET),
                Map.entry(
                    OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CLOSEOUT,
                    OpsShardReadinessManualEvidenceWorksheetRoutePaths
                        .MANUAL_EVIDENCE_WORKSHEET_CLOSEOUT)))
        .allSatisfy((legacy, split) -> assertThat(legacy).isEqualTo(split));
  }
}
