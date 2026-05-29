package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessEvidenceHandoffServiceTests {

    @Test
    void buildsCompletedShardReadinessEvidenceHandoff() {
        OpsShardReadinessEvidenceIndexService indexService = new OpsShardReadinessEvidenceIndexService();
        OpsShardReadinessEvidenceVerificationService verificationService =
                new OpsShardReadinessEvidenceVerificationService(indexService);
        OpsShardReadinessEvidenceHandoffResponse handoff =
                new OpsShardReadinessEvidenceHandoffService(indexService, verificationService).handoff();

        assertThat(handoff.project()).isEqualTo("advanced-order-platform");
        assertThat(handoff.version()).isEqualTo("Java v157");
        assertThat(handoff.readOnly()).isTrue();
        assertThat(handoff.executionAllowed()).isFalse();
        assertThat(handoff.sourceIndexVersion()).isEqualTo("Java v155");
        assertThat(handoff.sourceVerificationVersion()).isEqualTo("Java v156");
        assertThat(handoff.lastConsumedByNodeVersion()).isEqualTo("Node v378");
        assertThat(handoff.completedEvidenceVersions())
                .containsExactly("Java v155", "Java v156");
        assertThat(handoff.handoffArtifacts())
                .contains(
                        "/contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "e/156/evidence/java-shard-readiness-evidence-verification-v156.json"
                );
        assertThat(handoff.consumerRules())
                .contains(
                        "consume-only-completed-and-tagged-java-evidence",
                        "do-not-read-rolling-current-files-for-historical-baselines"
                );
        assertThat(handoff.stopConditions())
                .contains(
                        "node-requests-live-read-without-explicit-service-plan",
                        "request-would-enable-write-routing-or-active-sharding"
                );
        assertThat(handoff.evidencePath())
                .isEqualTo("e/157/evidence/java-shard-readiness-evidence-handoff-v157.json");
        assertThat(handoff.status()).isEqualTo("passed");
    }
}
