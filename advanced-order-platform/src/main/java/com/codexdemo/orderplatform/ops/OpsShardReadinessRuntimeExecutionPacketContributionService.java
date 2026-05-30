package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRuntimeExecutionPacketContributionService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/runtime-execution-packet-contribution";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json";
    static final String EVIDENCE_PATH =
            "e/163/evidence/java-shard-readiness-runtime-execution-packet-contribution-v163.json";

    private final OpsShardReadinessRuntimeExecutionArtifactCandidateService runtimeArtifactCandidateService;

    public OpsShardReadinessRuntimeExecutionPacketContributionService(
            OpsShardReadinessRuntimeExecutionArtifactCandidateService runtimeArtifactCandidateService
    ) {
        this.runtimeArtifactCandidateService = runtimeArtifactCandidateService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRuntimeExecutionPacketContributionResponse contribution() {
        OpsShardReadinessRuntimeExecutionArtifactCandidateResponse sourceCandidate =
                runtimeArtifactCandidateService.candidate();

        return new OpsShardReadinessRuntimeExecutionPacketContributionResponse(
                "advanced-order-platform",
                "Java v163",
                true,
                false,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                sourceCandidate.version(),
                "Node v396",
                "Node v397",
                "java-side-runtime-execution-packet-contribution",
                "java-runtime-packet-contribution-approval-record-v163",
                "must-be-correlated-by-node-approved-cross-project-runtime-window",
                "8080",
                "requires-mini-kv-runtime-packet-contribution",
                "java-platform-operator-confirmed",
                "mvn spring-boot:run -Dspring-boot.run.profiles=local",
                "java-platform-operator",
                "java-platform-operator",
                acceptedRequirementRows(),
                getOnlySmokeCommands(),
                cleanupProofArtifacts(),
                processCleanupRules(),
                crossProjectMissingArtifacts(),
                failClosedRules(),
                stopConditions(),
                EVIDENCE_PATH,
                contributionStatus(sourceCandidate)
        );
    }

    private List<String> acceptedRequirementRows() {
        return List.of(
                "operator-approval-record:java-side-record-present-cross-project-signature-required",
                "concrete-loopback-ports:java-8080-present-mini-kv-required",
                "get-only-smoke-command:java-get-only-present-mini-kv-required",
                "cleanup-proof:java-cleanup-proof-reference-present-runtime-start-archive-required",
                "service-owner-confirmation:java-platform-operator-confirmed-mini-kv-required",
                "process-cleanup-rules:java-stop-only-owned-process-rules-present-mini-kv-required"
        );
    }

    private List<String> getOnlySmokeCommands() {
        return List.of(
                "GET java-loopback-port-8080 /actuator/health",
                "GET java-loopback-port-8080 /api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
                "GET java-loopback-port-8080 /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate"
        );
    }

    private List<String> cleanupProofArtifacts() {
        return List.of(
                "java-cleanup-owner-confirmation:java-platform-operator",
                "java-cleanup-proof-reference:archive-after-approved-runtime-start",
                "java-cleanup-non-execution-proof:no-process-started-by-this-contribution"
        );
    }

    private List<String> processCleanupRules() {
        return List.of(
                "capture-java-process-id-only-after-approved-runtime-packet-start",
                "stop-only-java-process-started-by-approved-runtime-packet",
                "never-stop-pre-existing-java-service",
                "archive-java-health-and-smoke-output-before-cleanup"
        );
    }

    private List<String> crossProjectMissingArtifacts() {
        return List.of(
                "mini-kv-runtime-execution-packet-contribution",
                "node-approved-cross-project-runtime-window",
                "correlated-operator-approval-record-for-java-and-mini-kv"
        );
    }

    private List<String> failClosedRules() {
        return List.of(
                "missing-mini-kv-packet-contribution-blocks-runtime-execution",
                "missing-node-approved-runtime-window-blocks-runtime-execution",
                "uncorrelated-operator-approval-record-blocks-runtime-execution",
                "non-get-smoke-command-blocks-runtime-execution",
                "missing-stop-only-owned-process-rule-blocks-runtime-execution"
        );
    }

    private List<String> stopConditions() {
        return List.of(
                "source-candidate-status-not-passed",
                "request-would-start-java-from-this-contribution",
                "request-would-stop-java-from-this-contribution",
                "request-would-run-runtime-probe-from-this-contribution",
                "request-would-treat-java-only-contribution-as-cross-project-packet",
                "request-would-read-credential-or-raw-endpoint-value",
                "request-would-enable-active-shard-router-or-write-routing"
        );
    }

    private String contributionStatus(OpsShardReadinessRuntimeExecutionArtifactCandidateResponse sourceCandidate) {
        boolean passed = "passed".equals(sourceCandidate.status())
                && sourceCandidate.readOnly()
                && !sourceCandidate.executionAllowed()
                && sourceCandidate.javaRuntimeArtifactCandidatePresent()
                && sourceCandidate.javaRuntimeArtifactsComplete()
                && !sourceCandidate.crossProjectRuntimeArtifactsComplete()
                && !sourceCandidate.runtimeExecutionPacketExecutable()
                && !sourceCandidate.startsJavaService()
                && !sourceCandidate.startsMiniKvService();
        return passed ? "passed" : "blocked";
    }
}
