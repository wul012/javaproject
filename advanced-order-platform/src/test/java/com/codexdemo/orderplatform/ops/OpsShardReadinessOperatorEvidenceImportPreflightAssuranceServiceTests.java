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
}
