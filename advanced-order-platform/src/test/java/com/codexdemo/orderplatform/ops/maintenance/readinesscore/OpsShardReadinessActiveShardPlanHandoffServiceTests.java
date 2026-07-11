package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessActiveShardPlanHandoffServiceTests {

  @Test
  void buildsReadOnlyActiveShardPlanBoundaryHandoff() {
    OpsShardReadinessEvidenceIndexService indexService =
        new OpsShardReadinessEvidenceIndexService();
    OpsShardReadinessEvidenceVerificationService verificationService =
        new OpsShardReadinessEvidenceVerificationService(indexService);
    OpsShardReadinessEvidenceHandoffService evidenceHandoffService =
        new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
    OpsShardReadinessActiveShardPlanHandoffResponse handoff =
        new OpsShardReadinessActiveShardPlanHandoffService(evidenceHandoffService).handoff();

    assertThat(handoff.project()).isEqualTo("advanced-order-platform");
    assertThat(handoff.version()).isEqualTo("Java v158");
    assertThat(handoff.readOnly()).isTrue();
    assertThat(handoff.executionAllowed()).isFalse();
    assertThat(handoff.activeShardPrototypeEnabled()).isFalse();
    assertThat(handoff.liveReadAllowed()).isFalse();
    assertThat(handoff.sourceHandoffVersion()).isEqualTo("Java v157");
    assertThat(handoff.lastConsumedByNodeVersion()).isEqualTo("Node v380");
    assertThat(handoff.nodeArchiveVerificationVersion()).isEqualTo("Node v381");
    assertThat(handoff.javaRole()).isEqualTo("read-only-active-shard-plan-boundary-handoff");
    assertThat(handoff.activePrototypeAuthority()).isEqualTo("mini-kv-active-prototype-plan");
    assertThat(handoff.frozenJavaEvidence())
        .containsExactly(
            "Java v157",
            "/api/v1/ops/shard-readiness/evidence-handoff",
            "/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
            "e/157/evidence/java-shard-readiness-evidence-handoff-v157.json");
    assertThat(handoff.nodeConsumptionReferences())
        .contains(
            "Node v380 consumed Java v157 handoff as frozen evidence",
            "Node v381 verified the v380 archive with frozen evidence replay");
    assertThat(handoff.javaBoundaryRules())
        .contains(
            "active-shard-prototype-authority-stays-with-mini-kv-plan",
            "do-not-enable-java-shard-router-or-write-routing");
    assertThat(handoff.stopConditions())
        .contains(
            "request-would-enable-active-shard-prototype",
            "node-requests-live-read-without-service-responsibility-plan");
    assertThat(handoff.evidencePath())
        .isEqualTo("e/158/evidence/java-shard-readiness-active-shard-plan-handoff-v158.json");
    assertThat(handoff.status()).isEqualTo("passed");
  }
}
