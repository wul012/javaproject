package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceImportPreflightFoundationServiceTests {

    @Test
    void buildsImportPreflightCatalogWithoutImportedValues() {
        OpsShardReadinessOperatorEvidenceImportPreflightResponse catalog =
                new OpsShardReadinessOperatorEvidenceImportPreflightCatalogService().catalog();

        assertThat(catalog.version()).isEqualTo("Java v585");
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-catalog");
        assertThat(catalog.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-import-preflight-catalog.v1");
        assertThat(catalog.readyForOperatorEvidenceImportPreflight()).isTrue();
        assertThat(catalog.readyForEvidenceImport()).isFalse();
        assertThat(catalog.readyForManualEvidenceEntry()).isFalse();
        assertThat(catalog.readyForLiveExecution()).isFalse();
        assertThat(catalog.readyForProductionExecution()).isFalse();
        assertThat(catalog.itemCount()).isEqualTo(5);
        assertThat(catalog.items())
                .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
                .containsExactly(
                        "source-worksheet-closeout",
                        "preflight-slot-count",
                        "gate-count",
                        "import-locks",
                        "no-value-ingestion"
                );
        assertThat(catalog.checks()).contains(
                "import-preflight-catalog-slot-count-25",
                "import-preflight-catalog-gate-count-24",
                "import-preflight-catalog-imports-no-values"
        );
        assertThat(catalog.status()).isEqualTo("passed");
    }
}
