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

    @Test
    void buildsArchivePlanWithoutWritingFiles() {
        OpsShardReadinessManualEvidenceWorksheetResponse plan =
                new OpsShardReadinessManualEvidenceWorksheetArchivePlanService().plan();

        assertThat(plan.version()).isEqualTo("Java v576");
        assertThat(plan.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-archive-plan");
        assertThat(plan.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-archive-plan.v1");
        assertThat(plan.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(plan.executionAllowed()).isFalse();
        assertThat(plan.itemCount()).isEqualTo(4);
        assertThat(plan.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "route-json-capture",
                        "digest-record",
                        "artifact-location",
                        "no-file-write"
                );
        assertThat(plan.checks()).contains(
                "archive-plan-captures-json-externally",
                "archive-plan-does-not-write-files",
                "archive-plan-ready-for-route"
        );
        assertThat(plan.status()).isEqualTo("passed");
    }

    @Test
    void buildsOperatorHandoffWithSeparatedOwners() {
        OpsShardReadinessManualEvidenceWorksheetResponse handoff =
                new OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService().handoff();

        assertThat(handoff.version()).isEqualTo("Java v578");
        assertThat(handoff.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-operator-handoff");
        assertThat(handoff.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-operator-handoff.v1");
        assertThat(handoff.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(handoff.readyForManualEvidenceEntry()).isFalse();
        assertThat(handoff.itemCount()).isEqualTo(5);
        assertThat(handoff.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "worksheet-owner",
                        "validation-owner",
                        "archive-owner",
                        "importer-owner",
                        "runtime-boundary-owner"
                );
        assertThat(handoff.checks()).contains(
                "operator-handoff-owner-count-5",
                "operator-handoff-no-manual-values",
                "operator-handoff-no-runtime-approval"
        );
        assertThat(handoff.status()).isEqualTo("passed");
    }
}
