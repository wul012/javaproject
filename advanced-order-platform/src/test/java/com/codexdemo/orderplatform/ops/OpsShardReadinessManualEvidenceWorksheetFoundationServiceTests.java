package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessManualEvidenceWorksheetFoundationServiceTests {

    @Test
    void buildsWorksheetCatalogWithEntryLocks() {
        OpsShardReadinessManualEvidenceWorksheetResponse catalog =
                new OpsShardReadinessManualEvidenceWorksheetCatalogService().catalog();

        assertThat(catalog.version()).isEqualTo("Java v560");
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-catalog");
        assertThat(catalog.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-catalog.v1");
        assertThat(catalog.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(catalog.readyForManualEvidenceEntry()).isFalse();
        assertThat(catalog.readyForLiveExecution()).isFalse();
        assertThat(catalog.readyForProductionExecution()).isFalse();
        assertThat(catalog.itemCount()).isEqualTo(4);
        assertThat(catalog.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "source-review-package",
                        "blank-slot-count",
                        "gate-count",
                        "fail-closed-flags"
                );
        assertThat(catalog.checks()).contains(
                "worksheet-catalog-slot-count-25",
                "worksheet-catalog-gate-count-21",
                "worksheet-catalog-ready-for-operator-entry-only"
        );
        assertThat(catalog.status()).isEqualTo("passed");
    }
}
