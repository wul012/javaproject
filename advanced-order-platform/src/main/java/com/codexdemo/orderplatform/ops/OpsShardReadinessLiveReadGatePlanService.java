package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessLiveReadGatePlanService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/live-read-gate-plan";
    static final String FIXTURE_ENDPOINT = "/contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json";
    static final String EVIDENCE_PATH = "e/159/evidence/java-shard-readiness-live-read-gate-plan-v159.json";

    private final OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService;

    public OpsShardReadinessLiveReadGatePlanService(
            OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService
    ) {
        this.activeShardPlanHandoffService = activeShardPlanHandoffService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessLiveReadGatePlanResponse plan() {
        OpsShardReadinessActiveShardPlanHandoffResponse sourceHandoff = activeShardPlanHandoffService.handoff();

        return new OpsShardReadinessLiveReadGatePlanResponse(
                "advanced-order-platform",
                "Java v159",
                true,
                false,
                false,
                false,
                false,
                true,
                sourceHandoff.version(),
                "Node v383",
                "Node v384",
                requiredServiceOwnershipFields(),
                javaServiceLifecyclePlan(),
                smokeTargets(),
                failClosedRules(),
                cleanupResponsibilities(),
                stopConditions(),
                EVIDENCE_PATH,
                planStatus(sourceHandoff)
        );
    }

    private List<String> requiredServiceOwnershipFields() {
        return List.of(
                "java-service-owner",
                "java-base-url-or-port",
                "java-start-command-owner",
                "java-stop-responsibility",
                "node-smoke-timeout-and-fail-closed-policy",
                "mini-kv-service-owner-if-mini-kv-live-read-is-in-scope"
        );
    }

    private List<String> javaServiceLifecyclePlan() {
        return List.of(
                "node-may-not-start-java-from-this-plan",
                "java-operator-starts-service-before-live-read-window",
                "java-port-must-be-declared-by-operator-before-node-probe",
                "node-probes-get-only-smoke-targets-after-service-owner-confirms-readiness",
                "operator-stops-java-service-after-window-if-operator-started-it"
        );
    }

    private List<String> smokeTargets() {
        return List.of(
                "GET /actuator/health",
                "GET /api/v1/ops/shard-readiness/live-read-gate-plan",
                "GET /api/v1/ops/shard-readiness/active-shard-plan-handoff",
                "GET /api/v1/ops/shard-readiness/evidence-handoff"
        );
    }

    private List<String> failClosedRules() {
        return List.of(
                "missing-service-owner-blocks-live-read",
                "missing-port-or-base-url-blocks-live-read",
                "non-get-request-blocks-live-read",
                "failed-smoke-blocks-node-consumption",
                "cleanup-owner-missing-blocks-live-read"
        );
    }

    private List<String> cleanupResponsibilities() {
        return List.of(
                "java-operator-stops-java-if-java-operator-started-it",
                "node-must-not-stop-pre-existing-java-service",
                "node-may-close-only-processes-it-started-in-a-separate-approved-plan",
                "archive-smoke-output-before-service-cleanup"
        );
    }

    private List<String> stopConditions() {
        return List.of(
                "source-boundary-handoff-status-not-passed",
                "request-would-start-java-without-service-owner",
                "request-would-start-mini-kv-without-service-owner",
                "request-would-enable-active-shard-router-or-write-routing",
                "request-would-read-credential-or-raw-endpoint-value",
                "request-would-run-non-get-smoke"
        );
    }

    private String planStatus(OpsShardReadinessActiveShardPlanHandoffResponse sourceHandoff) {
        boolean passed = "passed".equals(sourceHandoff.status())
                && sourceHandoff.readOnly()
                && !sourceHandoff.executionAllowed()
                && !sourceHandoff.activeShardPrototypeEnabled();
        return passed ? "passed" : "blocked";
    }
}
