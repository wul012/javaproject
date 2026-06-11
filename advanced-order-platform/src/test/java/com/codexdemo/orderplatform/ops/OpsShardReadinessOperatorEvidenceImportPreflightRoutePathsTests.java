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
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_DIGEST_BLUEPRINT,
                        OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ROUTE_PROFILE_SUMMARY,
                        OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ARCHIVE_PLAN,
                        OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_OPERATOR_HANDOFF,
                        OpsShardReadinessOperatorEvidenceImportPreflightOperatorHandoffService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CI_BUDGET,
                        OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService.ENDPOINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CLOSEOUT,
                        OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService.ENDPOINT
                )
        )).allSatisfy((route, endpoint) ->
                assertThat(endpoint).isEqualTo(OpsShardReadinessRoutePaths.BASE_PATH + route));
    }

    @Test
    void operatorEvidenceImportPreflightRoutesDelegateToSplitOwner() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CATALOG,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CATALOG
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_SLOT_NORMALIZATION,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_SLOT_NORMALIZATION
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_IMPORT_BLOCKER_MATRIX,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_IMPORT_BLOCKER_MATRIX
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_REDACTION_PRESERVATION,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_REDACTION_PRESERVATION
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_MISSING_VALUE_GUARD,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_MISSING_VALUE_GUARD
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_TARGET_SCOPE_MAPPING,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_TARGET_SCOPE_MAPPING
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_DIGEST_BLUEPRINT,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_DIGEST_BLUEPRINT
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ROUTE_PROFILE_SUMMARY,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ROUTE_PROFILE_SUMMARY
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ARCHIVE_PLAN,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ARCHIVE_PLAN
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_OPERATOR_HANDOFF,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_OPERATOR_HANDOFF
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CI_BUDGET,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CI_BUDGET
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CLOSEOUT,
                        OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
                                .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CLOSEOUT
                )
        )).allSatisfy((legacy, split) -> assertThat(legacy).isEqualTo(split));
    }
}
