package com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceImportPreflightFoundationServiceTests {

  @Test
  void buildsImportPreflightCatalogWithoutImportedValues() {
    OpsShardReadinessOperatorEvidenceImportPreflightResponse catalog =
        new OpsShardReadinessOperatorEvidenceImportPreflightCatalogService().catalog();

    assertThat(catalog.version()).isEqualTo("Java v585");
    assertThat(catalog.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/operator-evidence-import-preflight-catalog");
    assertThat(catalog.profile())
        .isEqualTo("java-shard-readiness-operator-evidence-import-preflight-catalog.v1");
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
            "no-value-ingestion");
    assertThat(catalog.checks())
        .contains(
            "import-preflight-catalog-slot-count-25",
            "import-preflight-catalog-gate-count-24",
            "import-preflight-catalog-imports-no-values");
    assertThat(catalog.status()).isEqualTo("passed");
  }

  @Test
  void buildsSlotNormalizationWithoutValueImport() {
    OpsShardReadinessOperatorEvidenceImportPreflightResponse normalization =
        new OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService()
            .normalization();

    assertThat(normalization.version()).isEqualTo("Java v587");
    assertThat(normalization.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-slot-normalization");
    assertThat(normalization.profile())
        .isEqualTo("java-shard-readiness-operator-evidence-import-preflight-slot-normalization.v1");
    assertThat(normalization.readyForOperatorEvidenceImportPreflight()).isTrue();
    assertThat(normalization.readyForEvidenceImport()).isFalse();
    assertThat(normalization.readyForManualEvidenceEntry()).isFalse();
    assertThat(normalization.itemCount()).isEqualTo(4);
    assertThat(normalization.items())
        .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
        .containsExactly(
            "slot-id-normalization",
            "blank-value-normalization",
            "scope-name-normalization",
            "note-text-normalization");
    assertThat(normalization.checks())
        .contains(
            "slot-normalization-preserves-blank-values",
            "slot-normalization-does-not-trim-secrets",
            "slot-normalization-does-not-import-values");
    assertThat(normalization.status()).isEqualTo("passed");
  }

  @Test
  void buildsImportBlockerMatrixWithRuntimePayloadBlocked() {
    OpsShardReadinessOperatorEvidenceImportPreflightResponse matrix =
        new OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService().matrix();

    assertThat(matrix.version()).isEqualTo("Java v589");
    assertThat(matrix.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-import-blocker-matrix");
    assertThat(matrix.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-import-preflight-import-blocker-matrix.v1");
    assertThat(matrix.readyForOperatorEvidenceImportPreflight()).isTrue();
    assertThat(matrix.readyForEvidenceImport()).isFalse();
    assertThat(matrix.readyForLiveExecution()).isFalse();
    assertThat(matrix.itemCount()).isEqualTo(5);
    assertThat(matrix.items())
        .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
        .containsExactly(
            "missing-manual-value-blocker",
            "redaction-blocker",
            "runtime-payload-blocker",
            "unmapped-scope-blocker",
            "manual-entry-lock-blocker");
    assertThat(matrix.checks())
        .contains(
            "import-blocker-matrix-blocker-count-5",
            "import-blocker-matrix-blocks-runtime-payload",
            "import-blocker-matrix-keeps-import-locked");
    assertThat(matrix.status()).isEqualTo("passed");
  }

  @Test
  void buildsRedactionPreservationWithoutSecretMaterial() {
    OpsShardReadinessOperatorEvidenceImportPreflightResponse preservation =
        new OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService()
            .preservation();

    assertThat(preservation.version()).isEqualTo("Java v591");
    assertThat(preservation.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-redaction-preservation");
    assertThat(preservation.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-import-preflight-redaction-preservation.v1");
    assertThat(preservation.readyForOperatorEvidenceImportPreflight()).isTrue();
    assertThat(preservation.readyForEvidenceImport()).isFalse();
    assertThat(preservation.readyForProductionExecution()).isFalse();
    assertThat(preservation.itemCount()).isEqualTo(4);
    assertThat(preservation.items())
        .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
        .containsExactly(
            "credential-redaction-preserved",
            "raw-endpoint-redaction-preserved",
            "absence-marker-preserved",
            "review-text-boundary-preserved");
    assertThat(preservation.checks())
        .contains(
            "redaction-preservation-no-secret-material",
            "redaction-preservation-no-raw-endpoints",
            "redaction-preservation-absence-markers-only");
    assertThat(preservation.status()).isEqualTo("passed");
  }

  @Test
  void buildsMissingValueGuardWithoutSyntheticDefaults() {
    OpsShardReadinessOperatorEvidenceImportPreflightResponse guard =
        new OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService().guard();

    assertThat(guard.version()).isEqualTo("Java v593");
    assertThat(guard.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-missing-value-guard");
    assertThat(guard.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-import-preflight-missing-value-guard.v1");
    assertThat(guard.readyForOperatorEvidenceImportPreflight()).isTrue();
    assertThat(guard.readyForEvidenceImport()).isFalse();
    assertThat(guard.readyForManualEvidenceEntry()).isFalse();
    assertThat(guard.itemCount()).isEqualTo(4);
    assertThat(guard.items())
        .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
        .containsExactly(
            "blank-slot-guard",
            "missing-reviewer-guard",
            "missing-scope-guard",
            "missing-source-guard");
    assertThat(guard.checks())
        .contains(
            "missing-value-guard-keeps-preflight-ready",
            "missing-value-guard-keeps-import-locked",
            "missing-value-guard-no-synthetic-values");
    assertThat(guard.status()).isEqualTo("passed");
  }

  @Test
  void buildsTargetScopeMappingWithoutWriteRouting() {
    OpsShardReadinessOperatorEvidenceImportPreflightResponse mapping =
        new OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService().mapping();

    assertThat(mapping.version()).isEqualTo("Java v595");
    assertThat(mapping.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-target-scope-mapping");
    assertThat(mapping.profile())
        .isEqualTo(
            "java-shard-readiness-operator-evidence-import-preflight-target-scope-mapping.v1");
    assertThat(mapping.readyForOperatorEvidenceImportPreflight()).isTrue();
    assertThat(mapping.readyForEvidenceImport()).isFalse();
    assertThat(mapping.readyForProductionExecution()).isFalse();
    assertThat(mapping.itemCount()).isEqualTo(5);
    assertThat(mapping.items())
        .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
        .containsExactly(
            "read-model-scope",
            "preview-window-scope",
            "review-package-scope",
            "operator-slot-scope",
            "unmapped-scope-rejection");
    assertThat(mapping.checks())
        .contains(
            "target-scope-mapping-scope-count-5",
            "target-scope-mapping-no-write-routing",
            "target-scope-mapping-no-active-router");
    assertThat(mapping.status()).isEqualTo("passed");
  }
}
