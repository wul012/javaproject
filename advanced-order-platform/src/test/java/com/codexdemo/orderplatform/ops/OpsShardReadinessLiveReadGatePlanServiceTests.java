package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessLiveReadGatePlanServiceTests {

    @Test
    void buildsReadOnlyLiveReadGateLifecyclePlan() {
        OpsShardReadinessEvidenceIndexService indexService = new OpsShardReadinessEvidenceIndexService();
        OpsShardReadinessEvidenceVerificationService verificationService =
                new OpsShardReadinessEvidenceVerificationService(indexService);
        OpsShardReadinessEvidenceHandoffService evidenceHandoffService =
                new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
        OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService =
                new OpsShardReadinessActiveShardPlanHandoffService(evidenceHandoffService);
        OpsShardReadinessLiveReadGatePlanResponse plan =
                new OpsShardReadinessLiveReadGatePlanService(activeShardPlanHandoffService).plan();

        assertThat(plan.project()).isEqualTo("advanced-order-platform");
        assertThat(plan.version()).isEqualTo("Java v159");
        assertThat(plan.readOnly()).isTrue();
        assertThat(plan.executionAllowed()).isFalse();
        assertThat(plan.liveReadGateAllowed()).isFalse();
        assertThat(plan.serviceStartAllowedByNode()).isFalse();
        assertThat(plan.serviceStopAllowedByNode()).isFalse();
        assertThat(plan.failClosedRequired()).isTrue();
        assertThat(plan.sourceBoundaryHandoffVersion()).isEqualTo("Java v158");
        assertThat(plan.lastVerifiedByNodeVersion()).isEqualTo("Node v383");
        assertThat(plan.nextNodeConsumerHint()).isEqualTo("Node v384");
        assertThat(plan.requiredServiceOwnershipFields())
                .containsExactly(
                        "java-service-owner",
                        "java-base-url-or-port",
                        "java-start-command-owner",
                        "java-stop-responsibility",
                        "node-smoke-timeout-and-fail-closed-policy",
                        "mini-kv-service-owner-if-mini-kv-live-read-is-in-scope"
                );
        assertThat(plan.javaServiceLifecyclePlan())
                .contains(
                        "node-may-not-start-java-from-this-plan",
                        "java-port-must-be-declared-by-operator-before-node-probe"
                );
        assertThat(plan.smokeTargets())
                .containsExactly(
                        "GET /actuator/health",
                        "GET /api/v1/ops/shard-readiness/live-read-gate-plan",
                        "GET /api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "GET /api/v1/ops/shard-readiness/evidence-handoff"
                );
        assertThat(plan.failClosedRules())
                .contains(
                        "missing-port-or-base-url-blocks-live-read",
                        "failed-smoke-blocks-node-consumption"
                );
        assertThat(plan.cleanupResponsibilities())
                .contains(
                        "java-operator-stops-java-if-java-operator-started-it",
                        "node-must-not-stop-pre-existing-java-service"
                );
        assertThat(plan.stopConditions())
                .contains(
                        "request-would-start-java-without-service-owner",
                        "request-would-enable-active-shard-router-or-write-routing"
                );
        assertThat(plan.evidencePath()).isEqualTo("e/159/evidence/java-shard-readiness-live-read-gate-plan-v159.json");
        assertThat(plan.status()).isEqualTo("passed");
    }
}
