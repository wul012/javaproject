package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessDeclaredOperatorLifecycleServiceTests {

  @Test
  void buildsDeclaredOperatorLifecycleEvidenceWithoutRuntimeProbeApproval() {
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
    OpsShardReadinessOperatorServiceLifecycleService operatorLifecycleService =
        new OpsShardReadinessOperatorServiceLifecycleService(liveReadGatePlanService);

    OpsShardReadinessDeclaredOperatorLifecycleResponse lifecycle =
        new OpsShardReadinessDeclaredOperatorLifecycleService(operatorLifecycleService).lifecycle();

    assertThat(lifecycle.project()).isEqualTo("advanced-order-platform");
    assertThat(lifecycle.version()).isEqualTo("Java v161");
    assertThat(lifecycle.readOnly()).isTrue();
    assertThat(lifecycle.executionAllowed()).isFalse();
    assertThat(lifecycle.operatorOwned()).isTrue();
    assertThat(lifecycle.operatorLifecycleDeclared()).isTrue();
    assertThat(lifecycle.startupCommandDeclared()).isTrue();
    assertThat(lifecycle.portDeclared()).isTrue();
    assertThat(lifecycle.getOnlySmokeDeclared()).isTrue();
    assertThat(lifecycle.cleanupDeclared()).isTrue();
    assertThat(lifecycle.failClosedDeclared()).isTrue();
    assertThat(lifecycle.runtimeProbeAllowed()).isFalse();
    assertThat(lifecycle.nodeMayStartService()).isFalse();
    assertThat(lifecycle.nodeMayStopService()).isFalse();
    assertThat(lifecycle.sourceLifecycleEvidenceVersion()).isEqualTo("Java v160");
    assertThat(lifecycle.lastVerifiedByNodeVersion()).isEqualTo("Node v387");
    assertThat(lifecycle.nextNodeConsumerHint()).isEqualTo("Node v388");
    assertThat(lifecycle.javaServiceOwner()).isEqualTo("java-platform-operator");
    assertThat(lifecycle.javaStartOwner()).isEqualTo("java-platform-operator");
    assertThat(lifecycle.javaStopOwner()).isEqualTo("java-platform-operator");
    assertThat(lifecycle.declaredWorkingDirectory()).isEqualTo("advanced-order-platform");
    assertThat(lifecycle.declaredStartupCommand())
        .isEqualTo("mvn spring-boot:run -Dspring-boot.run.profiles=local");
    assertThat(lifecycle.declaredPorts()).containsExactly("8080");
    assertThat(lifecycle.javaBaseUrlHandle()).isEqualTo("java-local-readonly-base-url");
    assertThat(lifecycle.getOnlySmokeTargets())
        .containsExactly(
            "GET /actuator/health",
            "GET /api/v1/ops/shard-readiness/declared-operator-lifecycle",
            "GET /api/v1/ops/shard-readiness/operator-service-lifecycle",
            "GET /api/v1/ops/shard-readiness/live-read-gate-plan");
    assertThat(lifecycle.failClosedRules())
        .contains(
            "missing-java-service-owner-blocks-runtime-gate",
            "missing-java-port-blocks-runtime-gate",
            "failed-java-smoke-blocks-node-consumption");
    assertThat(lifecycle.cleanupResponsibilities())
        .contains(
            "java-operator-stops-service-if-java-operator-started-it",
            "node-must-not-stop-java-from-declared-evidence");
    assertThat(lifecycle.runtimeGatePrerequisites())
        .contains(
            "mini-kv-declared-operator-lifecycle-evidence",
            "separate-approved-runtime-live-read-gate");
    assertThat(lifecycle.stopConditions())
        .contains(
            "request-would-start-java-from-this-evidence",
            "request-would-run-runtime-probe-before-mini-kv-declared-lifecycle",
            "request-would-enable-active-shard-router-or-write-routing");
    assertThat(lifecycle.evidencePath())
        .isEqualTo("e/161/evidence/java-shard-readiness-declared-operator-lifecycle-v161.json");
    assertThat(lifecycle.status()).isEqualTo("passed");
  }
}
