package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessHardeningServiceTests {

    @Test
    void buildsAdditiveShardReadinessHardeningEvidence() {
        OpsShardReadinessHardeningResponse hardening =
                new OpsShardReadinessHardeningService().hardening();

        assertThat(hardening.project()).isEqualTo("advanced-order-platform");
        assertThat(hardening.version()).isEqualTo("Java v154");
        assertThat(hardening.readOnly()).isTrue();
        assertThat(hardening.executionAllowed()).isFalse();
        assertThat(hardening.sourceEvidenceVersion()).isEqualTo("Java v153");
        assertThat(hardening.sourceEndpoint()).isEqualTo("/api/v1/ops/shard-readiness");
        assertThat(hardening.sourceFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-v153.fixture.json");
        assertThat(hardening.sourceEvidencePath())
                .isEqualTo("e/153/evidence/java-shard-readiness-v153.json");
        assertThat(hardening.fieldExplanations())
                .extracting(OpsShardReadinessHardeningResponse.FieldExplanation::field)
                .containsExactly("readOnly", "executionAllowed", "shardEnabled", "routingMode", "status");
        assertThat(hardening.errorSemantics())
                .extracting(OpsShardReadinessHardeningResponse.ErrorSemantic::status)
                .containsExactly(
                        "BLOCK_NEW_JAVA_HARDENING_CONSUMPTION",
                        "FAIL_CLOSED",
                        "CONTRACT_BREAK"
                );
        assertThat(hardening.compatibilityGuarantees())
                .contains(
                        "v153-shard-readiness-core-fields-unchanged",
                        "v370-v373-node-archive-chain-not-mutated",
                        "hardening-output-is-additive-sibling-evidence"
                );
        assertThat(hardening.forbiddenChanges())
                .contains(
                        "mutate-node-v370-v373-archives",
                        "change-v153-endpoint-field-names-or-types",
                        "enable-shard-routing-or-execution-from-hardening-endpoint"
                );
        assertThat(hardening.evidencePath())
                .isEqualTo("e/154/evidence/java-shard-readiness-hardening-v154.json");
        assertThat(hardening.status()).isEqualTo("passed");
    }
}
