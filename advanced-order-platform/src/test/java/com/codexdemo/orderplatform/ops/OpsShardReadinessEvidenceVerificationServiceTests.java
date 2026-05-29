package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessEvidenceVerificationServiceTests {

    @Test
    void verifiesFrozenShardReadinessEvidenceIndex() {
        OpsShardReadinessEvidenceVerificationResponse verification =
                new OpsShardReadinessEvidenceVerificationService(
                        new OpsShardReadinessEvidenceIndexService()
                ).verification();

        assertThat(verification.project()).isEqualTo("advanced-order-platform");
        assertThat(verification.version()).isEqualTo("Java v156");
        assertThat(verification.readOnly()).isTrue();
        assertThat(verification.executionAllowed()).isFalse();
        assertThat(verification.sourceIndexVersion()).isEqualTo("Java v155");
        assertThat(verification.sourceIndexEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/evidence-index");
        assertThat(verification.verifiedEntryCount()).isEqualTo(2);
        assertThat(verification.verifiedEvidenceVersions())
                .containsExactly("Java v153", "Java v154");
        assertThat(verification.checks())
                .extracting(OpsShardReadinessEvidenceVerificationResponse.VerificationCheck::checkId)
                .containsExactly(
                        "index-read-only-and-non-executable",
                        "required-contract-fields-covered",
                        "source-entry-count",
                        "all-sources-frozen",
                        "no-rolling-current-pointer",
                        "versioned-fixture-endpoints",
                        "versioned-archive-paths",
                        "node-archive-mutation-forbidden"
                );
        assertThat(verification.checks())
                .allSatisfy(check -> assertThat(check.passed()).isTrue());
        assertThat(verification.fallbackPolicy())
                .contains("do-not-read-rolling-current-files-for-historical-baselines");
        assertThat(verification.evidencePath())
                .isEqualTo("e/156/evidence/java-shard-readiness-evidence-verification-v156.json");
        assertThat(verification.status()).isEqualTo("passed");
    }
}
