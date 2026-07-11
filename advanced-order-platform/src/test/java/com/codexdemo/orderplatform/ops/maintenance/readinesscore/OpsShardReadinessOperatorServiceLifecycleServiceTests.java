package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorServiceLifecycleServiceTests {

  @Test
  void buildsReadOnlyOperatorServiceLifecycleEvidence() {
    OpsShardReadinessEvidenceIndexService indexService =
        new OpsShardReadinessEvidenceIndexService();
    OpsShardReadinessEvidenceVerificationService verificationService =
        new OpsShardReadinessEvidenceVerificationService(indexService);
    OpsShardReadinessEvidenceHandoffService evidenceHandoffService =
        new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
    OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService =
        new OpsShardReadinessActiveShardPlanHandoffService(evidenceHandoffService);
    OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService =
        new OpsShardReadinessLiveReadGatePlanService(activeShardPlanHandoffService);

    OpsShardReadinessOperatorServiceLifecycleResponse lifecycle =
        new OpsShardReadinessOperatorServiceLifecycleService(liveReadGatePlanService).lifecycle();

    assertThat(lifecycle.project()).isEqualTo("advanced-order-platform");
    assertThat(lifecycle.version()).isEqualTo("Java v160");
    assertThat(lifecycle.readOnly()).isTrue();
    assertThat(lifecycle.executionAllowed()).isFalse();
    assertThat(lifecycle.operatorOwned()).isTrue();
    assertThat(lifecycle.runtimeProbeAllowed()).isFalse();
    assertThat(lifecycle.nodeMayStartService()).isFalse();
    assertThat(lifecycle.nodeMayStopService()).isFalse();
    assertThat(lifecycle.sourceGatePlanVersion()).isEqualTo("Java v159");
    assertThat(lifecycle.lastVerifiedByNodeVersion()).isEqualTo("Node v385");
    assertThat(lifecycle.nextNodeConsumerHint()).isEqualTo("Node v386");
    assertThat(lifecycle.javaServiceOwner()).isEqualTo("java-service-operator-placeholder");
    assertThat(lifecycle.javaStartOwner()).isEqualTo("java-service-operator-placeholder");
    assertThat(lifecycle.javaStopOwner()).isEqualTo("java-service-operator-placeholder");
    assertThat(lifecycle.javaPortDeclaration()).isEqualTo("operator-declared-port-before-window");
    assertThat(lifecycle.javaBaseUrlTemplate()).isEqualTo("http://127.0.0.1:{java-port}");
    assertThat(lifecycle.operatorPrerequisites())
        .containsExactly(
            "operator-confirms-java-service-owner",
            "operator-confirms-start-command-and-port-before-window",
            "operator-confirms-stop-responsibility-before-window",
            "operator-confirms-get-only-smoke-targets",
            "operator-confirms-no-credential-or-raw-endpoint-value-read");
    assertThat(lifecycle.getOnlySmokeTargets())
        .containsExactly(
            "GET /actuator/health",
            "GET /api/v1/ops/shard-readiness/operator-service-lifecycle",
            "GET /api/v1/ops/shard-readiness/live-read-gate-plan",
            "GET /api/v1/ops/shard-readiness/active-shard-plan-handoff");
    assertThat(lifecycle.failClosedRules())
        .contains(
            "missing-operator-owner-blocks-runtime-probe",
            "missing-operator-declared-port-blocks-runtime-probe",
            "failed-smoke-blocks-node-consumption");
    assertThat(lifecycle.cleanupResponsibilities())
        .contains(
            "operator-stops-java-if-operator-started-java",
            "node-must-not-stop-java-from-this-evidence");
    assertThat(lifecycle.stopConditions())
        .contains(
            "source-gate-plan-status-not-passed",
            "request-would-start-java-from-this-evidence",
            "request-would-run-non-get-smoke");
    assertThat(lifecycle.evidencePath())
        .isEqualTo("e/160/evidence/java-shard-readiness-operator-service-lifecycle-v160.json");
    assertThat(lifecycle.status()).isEqualTo("passed");
  }
}
