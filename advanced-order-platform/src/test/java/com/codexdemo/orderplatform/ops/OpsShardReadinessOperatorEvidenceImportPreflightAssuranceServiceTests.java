package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceImportPreflightAssuranceServiceTests {

    @Test
    void buildsDigestBlueprintWithoutEvidenceValues() {
        OpsShardReadinessOperatorEvidenceImportPreflightResponse blueprint =
                new OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService()
                        .blueprint();

        assertThat(blueprint.version()).isEqualTo("Java v597");
        assertThat(blueprint.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-digest-blueprint");
        assertThat(blueprint.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-import-preflight-digest-blueprint.v1");
        assertThat(blueprint.sourcePlan()).isEqualTo("Node v886");
        assertThat(blueprint.readyForOperatorEvidenceImportPreflight()).isTrue();
        assertThat(blueprint.readyForEvidenceImport()).isFalse();
        assertThat(blueprint.readyForLiveExecution()).isFalse();
        assertThat(blueprint.itemCount()).isEqualTo(4);
        assertThat(blueprint.items())
                .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
                .containsExactly(
                        "slot-count-digest",
                        "blocker-count-digest",
                        "lock-flag-digest",
                        "source-plan-digest"
                );
        assertThat(blueprint.checks()).contains(
                "digest-blueprint-does-not-hash-values",
                "digest-blueprint-covers-lock-flags",
                "digest-blueprint-ready-for-route"
        );
        assertThat(blueprint.status()).isEqualTo("passed");
    }

    @Test
    void buildsRouteProfileSummaryWithGetOnlyBoundary() {
        OpsShardReadinessOperatorEvidenceImportPreflightResponse summary =
                new OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService()
                        .summary();

        assertThat(summary.version()).isEqualTo("Java v599");
        assertThat(summary.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-route-profile-summary");
        assertThat(summary.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-import-preflight-route-profile-summary.v1");
        assertThat(summary.readyForOperatorEvidenceImportPreflight()).isTrue();
        assertThat(summary.readyForEvidenceImport()).isFalse();
        assertThat(summary.readyForManualEvidenceEntry()).isFalse();
        assertThat(summary.itemCount()).isEqualTo(4);
        assertThat(summary.items())
                .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
                .containsExactly(
                        "foundation-route-profile",
                        "assurance-route-profile",
                        "json-contract-profile",
                        "get-only-boundary"
                );
        assertThat(summary.checks()).contains(
                "import-preflight-route-profile-foundation-routes-6",
                "import-preflight-route-profile-assurance-routes-6",
                "import-preflight-route-profile-get-only"
        );
        assertThat(summary.status()).isEqualTo("passed");
    }

    @Test
    void buildsArchivePlanWithoutFileWrites() {
        OpsShardReadinessOperatorEvidenceImportPreflightResponse plan =
                new OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService()
                        .plan();

        assertThat(plan.version()).isEqualTo("Java v601");
        assertThat(plan.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-archive-plan");
        assertThat(plan.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-import-preflight-archive-plan.v1");
        assertThat(plan.readyForOperatorEvidenceImportPreflight()).isTrue();
        assertThat(plan.readyForEvidenceImport()).isFalse();
        assertThat(plan.readyForProductionExecution()).isFalse();
        assertThat(plan.itemCount()).isEqualTo(4);
        assertThat(plan.items())
                .extracting(OpsShardReadinessOperatorEvidenceImportPreflightResponse.PreflightItem::name)
                .containsExactly(
                        "json-capture-plan",
                        "digest-blueprint-plan",
                        "route-output-plan",
                        "no-file-write"
                );
        assertThat(plan.checks()).contains(
                "import-preflight-archive-plan-external-capture",
                "import-preflight-archive-plan-no-file-write",
                "import-preflight-archive-plan-ready"
        );
        assertThat(plan.status()).isEqualTo("passed");
    }
}
