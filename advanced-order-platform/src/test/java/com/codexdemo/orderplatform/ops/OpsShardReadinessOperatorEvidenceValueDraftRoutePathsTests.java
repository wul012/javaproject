package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftOperatorHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueDraftRoutePathsTests {

  @Test
  void operatorEvidenceValueDraftEndpointsUseSharedRouteConstants() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueDraftCatalogService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SLOT_TEMPLATE,
                    OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_VALUE_BOUNDARY,
                    OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_INSTRUCTION_SET,
                    OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SAFETY_GATE_MATRIX,
                    OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_SOURCE_MAPPING_REGISTRY,
                    OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService
                        .ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_BLOCKED_REASON_LEDGER,
                    OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_DIGEST_BLUEPRINT,
                    OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_ROUTE_PROFILE_SUMMARY,
                    OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_ARCHIVE_PLAN,
                    OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_OPERATOR_HANDOFF,
                    OpsShardReadinessOperatorEvidenceValueDraftOperatorHandoffService.ENDPOINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueDraftCloseoutService.ENDPOINT)))
        .allSatisfy(
            (route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
  }

  @Test
  void operatorEvidenceValueDraftRoutesDelegateToSplitOwner() {
    assertThat(
            Map.ofEntries(
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SLOT_TEMPLATE,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_SLOT_TEMPLATE),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_VALUE_BOUNDARY,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_VALUE_BOUNDARY),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_INSTRUCTION_SET,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_INSTRUCTION_SET),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SAFETY_GATE_MATRIX,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_SAFETY_GATE_MATRIX),
                Map.entry(
                    OpsShardReadinessRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_SOURCE_MAPPING_REGISTRY,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_SOURCE_MAPPING_REGISTRY),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_BLOCKED_REASON_LEDGER,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_BLOCKED_REASON_LEDGER),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_DIGEST_BLUEPRINT,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_DIGEST_BLUEPRINT),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_ROUTE_PROFILE_SUMMARY,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_ROUTE_PROFILE_SUMMARY),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_ARCHIVE_PLAN,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_ARCHIVE_PLAN),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_OPERATOR_HANDOFF,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_OPERATOR_HANDOFF),
                Map.entry(
                    OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CLOSEOUT,
                    OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
                        .OPERATOR_EVIDENCE_VALUE_DRAFT_CLOSEOUT)))
        .allSatisfy((legacy, split) -> assertThat(legacy).isEqualTo(split));
  }
}
