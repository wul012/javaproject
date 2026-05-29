package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessDeclaredOperatorLifecycleService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/declared-operator-lifecycle";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json";
    static final String EVIDENCE_PATH = "e/161/evidence/java-shard-readiness-declared-operator-lifecycle-v161.json";

    private final OpsShardReadinessOperatorServiceLifecycleService operatorServiceLifecycleService;

    public OpsShardReadinessDeclaredOperatorLifecycleService(
            OpsShardReadinessOperatorServiceLifecycleService operatorServiceLifecycleService
    ) {
        this.operatorServiceLifecycleService = operatorServiceLifecycleService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessDeclaredOperatorLifecycleResponse lifecycle() {
        OpsShardReadinessOperatorServiceLifecycleResponse sourceLifecycle =
                operatorServiceLifecycleService.lifecycle();

        return new OpsShardReadinessDeclaredOperatorLifecycleResponse(
                "advanced-order-platform",
                "Java v161",
                true,
                false,
                true,
                true,
                true,
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                sourceLifecycle.version(),
                "Node v387",
                "Node v388",
                "java-platform-operator",
                "java-platform-operator",
                "java-platform-operator",
                "advanced-order-platform",
                "mvn spring-boot:run -Dspring-boot.run.profiles=local",
                declaredPorts(),
                "java-local-readonly-base-url",
                getOnlySmokeTargets(),
                failClosedRules(),
                cleanupResponsibilities(),
                runtimeGatePrerequisites(),
                stopConditions(),
                EVIDENCE_PATH,
                lifecycleStatus(sourceLifecycle)
        );
    }

    private List<String> declaredPorts() {
        return List.of("8080");
    }

    private List<String> getOnlySmokeTargets() {
        return List.of(
                "GET /actuator/health",
                "GET /api/v1/ops/shard-readiness/declared-operator-lifecycle",
                "GET /api/v1/ops/shard-readiness/operator-service-lifecycle",
                "GET /api/v1/ops/shard-readiness/live-read-gate-plan"
        );
    }

    private List<String> failClosedRules() {
        return List.of(
                "missing-java-service-owner-blocks-runtime-gate",
                "missing-java-start-command-blocks-runtime-gate",
                "missing-java-port-blocks-runtime-gate",
                "missing-java-cleanup-owner-blocks-runtime-gate",
                "non-get-smoke-target-blocks-runtime-gate",
                "failed-java-smoke-blocks-node-consumption"
        );
    }

    private List<String> cleanupResponsibilities() {
        return List.of(
                "java-operator-stops-service-if-java-operator-started-it",
                "node-must-not-stop-java-from-declared-evidence",
                "node-may-clean-only-processes-started-by-separate-approved-runtime-gate",
                "archive-java-smoke-output-before-cleanup"
        );
    }

    private List<String> runtimeGatePrerequisites() {
        return List.of(
                "mini-kv-declared-operator-lifecycle-evidence",
                "separate-approved-runtime-live-read-gate",
                "operator-confirms-java-service-running-and-port",
                "operator-confirms-get-only-smoke-before-node-consumption",
                "node-records-fail-closed-result-before-consuming"
        );
    }

    private List<String> stopConditions() {
        return List.of(
                "source-lifecycle-status-not-passed",
                "request-would-start-java-from-this-evidence",
                "request-would-stop-java-from-this-evidence",
                "request-would-run-runtime-probe-before-mini-kv-declared-lifecycle",
                "request-would-run-non-get-smoke",
                "request-would-read-credential-or-raw-endpoint-value",
                "request-would-enable-active-shard-router-or-write-routing"
        );
    }

    private String lifecycleStatus(OpsShardReadinessOperatorServiceLifecycleResponse sourceLifecycle) {
        boolean passed = "passed".equals(sourceLifecycle.status())
                && sourceLifecycle.readOnly()
                && !sourceLifecycle.executionAllowed()
                && sourceLifecycle.operatorOwned()
                && !sourceLifecycle.nodeMayStartService()
                && !sourceLifecycle.nodeMayStopService();
        return passed ? "passed" : "blocked";
    }
}
