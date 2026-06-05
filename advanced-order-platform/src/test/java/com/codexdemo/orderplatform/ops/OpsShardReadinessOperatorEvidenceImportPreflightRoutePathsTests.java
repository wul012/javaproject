package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceImportPreflightRoutePathsTests {

    @Test
    void operatorEvidenceImportPreflightEndpointsUseSharedRouteConstants() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CATALOG,
                        OpsShardReadinessOperatorEvidenceImportPreflightCatalogService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_SLOT_NORMALIZATION,
                        OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_IMPORT_BLOCKER_MATRIX,
                        OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_REDACTION_PRESERVATION,
                        OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_MISSING_VALUE_GUARD,
                        OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_TARGET_SCOPE_MAPPING,
                        OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService.ENDPOINT
                )
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }
}
