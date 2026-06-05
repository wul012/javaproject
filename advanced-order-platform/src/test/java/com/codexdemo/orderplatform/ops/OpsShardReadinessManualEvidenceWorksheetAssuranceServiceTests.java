package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessManualEvidenceWorksheetAssuranceServiceTests {

    @Test
    void buildsImporterPreflightWithoutImportExecution() {
        OpsShardReadinessManualEvidenceWorksheetResponse preflight =
                new OpsShardReadinessManualEvidenceWorksheetImporterPreflightService().preflight();

        assertThat(preflight.version()).isEqualTo("Java v572");
        assertThat(preflight.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-importer-preflight");
        assertThat(preflight.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-importer-preflight.v1");
        assertThat(preflight.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(preflight.readyForManualEvidenceEntry()).isFalse();
        assertThat(preflight.readyForLiveExecution()).isFalse();
        assertThat(preflight.itemCount()).isEqualTo(5);
        assertThat(preflight.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "worksheet-structure-ready",
                        "manual-values-absent",
                        "validation-rules-present",
                        "target-scopes-present",
                        "import-blocker"
                );
        assertThat(preflight.checks()).contains(
                "importer-preflight-structure-ready",
                "importer-preflight-manual-values-absent",
                "importer-preflight-import-execution-blocked"
        );
        assertThat(preflight.status()).isEqualTo("passed");
    }

    @Test
    void buildsRouteProfileSummaryForWorksheetRoutes() {
        OpsShardReadinessManualEvidenceWorksheetResponse summary =
                new OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService().summary();

        assertThat(summary.version()).isEqualTo("Java v574");
        assertThat(summary.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-route-profile-summary");
        assertThat(summary.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-route-profile-summary.v1");
        assertThat(summary.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(summary.executionAllowed()).isFalse();
        assertThat(summary.itemCount()).isEqualTo(4);
        assertThat(summary.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "foundation-route-profile",
                        "assurance-route-profile",
                        "json-contract-profile",
                        "route-boundary-profile"
                );
        assertThat(summary.checks()).contains(
                "route-profile-summary-foundation-routes-6",
                "route-profile-summary-assurance-routes-started",
                "route-profile-summary-get-only"
        );
        assertThat(summary.status()).isEqualTo("passed");
    }
}
