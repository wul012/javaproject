package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueDraftRoutePathsTests {

    @Test
    void operatorEvidenceValueDraftEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG,
                        OpsShardReadinessOperatorEvidenceValueDraftCatalogService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SLOT_TEMPLATE,
                        OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_VALUE_BOUNDARY,
                        OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_INSTRUCTION_SET,
                        OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SAFETY_GATE_MATRIX,
                        OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SOURCE_MAPPING_REGISTRY,
                        OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_BLOCKED_REASON_LEDGER,
                        OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_DIGEST_BLUEPRINT,
                        OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_ROUTE_PROFILE_SUMMARY,
                        OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService.ENDPOINT
                )
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }
}
