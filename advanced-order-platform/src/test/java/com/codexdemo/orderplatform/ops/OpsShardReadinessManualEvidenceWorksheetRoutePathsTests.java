package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessManualEvidenceWorksheetRoutePathsTests {

    @Test
    void manualEvidenceWorksheetEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CATALOG,
                        OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_SLOT_TEMPLATE,
                        OpsShardReadinessManualEvidenceWorksheetSlotTemplateService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_VALIDATION_RULES,
                        OpsShardReadinessManualEvidenceWorksheetValidationRulesService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_REDACTION_RULES,
                        OpsShardReadinessManualEvidenceWorksheetRedactionRulesService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_MISSING_VALUE_POLICY,
                        OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_TARGET_SCOPE_REGISTRY,
                        OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT
                )
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }
}
