package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReadOnlyEvidenceCatalogService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/read-only-evidence-catalog";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json";
    static final String EVIDENCE_PATH =
            "e/175/evidence/java-shard-readiness-read-only-evidence-catalog-v175.json";

    private final OpsShardReadinessEchoService echoService;
    private final OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService passEvidenceCloseoutService;

    public OpsShardReadinessReadOnlyEvidenceCatalogService(
            OpsShardReadinessEchoService echoService,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService passEvidenceCloseoutService
    ) {
        this.echoService = echoService;
        this.passEvidenceCloseoutService = passEvidenceCloseoutService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessReadOnlyEvidenceCatalogResponse catalog() {
        OpsShardReadinessEchoResponse echo = echoService.echo();
        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse closeout =
                passEvidenceCloseoutService.closeout();
        List<String> liveEndpoints = OpsShardReadinessEvidenceEndpoints.liveEndpoints();
        List<String> fixtureEndpoints = OpsShardReadinessEvidenceEndpoints.fixtureEndpoints();

        return new OpsShardReadinessReadOnlyEvidenceCatalogResponse(
                "advanced-order-platform",
                "Java v175",
                true,
                false,
                echo.shardEnabled(),
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                echo.version(),
                closeout.version(),
                echo.receiptId(),
                closeout.receiptId(),
                "append-only-read-only-evidence-catalog-preserves-v153-root-schema",
                "java-shard-readiness-read-only-evidence-catalog.v1",
                "java-shard-readiness-read-only-evidence-catalog-receipt-v175",
                ENDPOINT,
                FIXTURE_ENDPOINT,
                liveEndpoints.size(),
                fixtureEndpoints.size(),
                liveEndpoints,
                fixtureEndpoints,
                evidenceArchivePaths(echo),
                sourceReceipts(echo, closeout),
                consumerBatches(),
                failClosedRules(echo, closeout),
                forbiddenOperations(),
                EVIDENCE_PATH,
                catalogStatus(echo, closeout, liveEndpoints, fixtureEndpoints)
        );
    }

    private List<String> evidenceArchivePaths(OpsShardReadinessEchoResponse echo) {
        return List.of(
                OpsShardReadinessService.EVIDENCE_PATH,
                OpsShardReadinessHardeningService.EVIDENCE_PATH,
                OpsShardReadinessEvidenceIndexService.EVIDENCE_PATH,
                OpsShardReadinessEvidenceVerificationService.EVIDENCE_PATH,
                OpsShardReadinessEvidenceHandoffService.EVIDENCE_PATH,
                OpsShardReadinessActiveShardPlanHandoffService.EVIDENCE_PATH,
                OpsShardReadinessLiveReadGatePlanService.EVIDENCE_PATH,
                OpsShardReadinessOperatorServiceLifecycleService.EVIDENCE_PATH,
                OpsShardReadinessDeclaredOperatorLifecycleService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionArtifactCandidateService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionPacketContributionService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionApprovalGateInputService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionLiveReadGateService.EVIDENCE_PATH,
                OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.EVIDENCE_PATH,
                "e/171/evidence/java-shard-readiness-runtime-execution-route-group-split-v171.json",
                "e/172/evidence/java-shard-readiness-lifecycle-plan-route-group-split-v172.json",
                "e/173/evidence/java-shard-readiness-evidence-route-group-split-v173.json",
                echo.evidencePath(),
                EVIDENCE_PATH
        );
    }

    private List<String> sourceReceipts(
            OpsShardReadinessEchoResponse echo,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse closeout
    ) {
        return List.of(
                echo.receiptId(),
                closeout.receiptId(),
                "Java v171:runtime-execution-controller-split",
                "Java v172:lifecycle-plan-controller-split",
                "Java v173:evidence-controller-split"
        );
    }

    private List<String> consumerBatches() {
        return List.of(
                "java-v153-v157:baseline-readiness-index-verification-handoff",
                "java-v158-v161:active-shard-plan-and-operator-lifecycle-read-only",
                "java-v162-v170:runtime-execution-read-only-pass-evidence",
                "java-v171-v174:controller-split-and-echo-readiness",
                "java-v175:read-only-evidence-catalog-for-batch-node-consumption"
        );
    }

    private List<String> failClosedRules(
            OpsShardReadinessEchoResponse echo,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse closeout
    ) {
        return List.of(
                "source-echo-status-must-be-passed:" + echo.status(),
                "source-runtime-pass-evidence-closeout-status-must-be-passed:" + closeout.status(),
                "catalog-endpoint-is-read-only",
                "catalog-fixture-is-static",
                "catalog-does-not-start-or-stop-java",
                "catalog-does-not-start-or-stop-mini-kv",
                "catalog-does-not-read-credential-values",
                "catalog-does-not-enable-write-routing-or-active-shard-router"
        );
    }

    private List<String> forbiddenOperations() {
        return List.of(
                "write-routing",
                "active-shard-router",
                "credential-value-read",
                "raw-endpoint-parse",
                "managed-audit-connection",
                "deployment-or-rollback",
                "node-start-or-stop-java-or-mini-kv"
        );
    }

    private String catalogStatus(
            OpsShardReadinessEchoResponse echo,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutResponse closeout,
            List<String> liveEndpoints,
            List<String> fixtureEndpoints
    ) {
        boolean sourcesPassed = "passed".equals(echo.status()) && "passed".equals(closeout.status());
        boolean endpointsCataloged = liveEndpoints.contains(ENDPOINT)
                && fixtureEndpoints.contains(FIXTURE_ENDPOINT)
                && liveEndpoints.size() == fixtureEndpoints.size();
        boolean boundariesHeld = echo.readOnly()
                && closeout.readOnly()
                && !echo.executionAllowed()
                && !closeout.executionAllowed()
                && !closeout.runtimeSmokeRerunByJava()
                && !closeout.startsJavaService()
                && !closeout.startsMiniKvService()
                && !closeout.stopsJavaService()
                && !closeout.stopsMiniKvService()
                && !closeout.connectsManagedAudit()
                && !closeout.credentialValueRead()
                && !closeout.rawEndpointUrlParsed()
                && !closeout.writeOperationsAllowed()
                && !closeout.activeShardPrototypeEnabled();
        return sourcesPassed && endpointsCataloged && boundariesHeld ? "passed" : "blocked";
    }
}
