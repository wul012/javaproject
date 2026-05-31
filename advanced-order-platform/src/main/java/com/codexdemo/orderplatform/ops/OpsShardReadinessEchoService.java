package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessEchoService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/echo";
    static final String FIXTURE_ENDPOINT = "/contracts/java-shard-readiness-echo-v174.fixture.json";
    static final String EVIDENCE_PATH = "e/174/evidence/java-shard-readiness-echo-v174.json";

    private final OpsShardReadinessService readinessService;
    private final OpsShardReadinessHardeningService hardeningService;
    private final OpsShardReadinessEvidenceIndexService evidenceIndexService;
    private final OpsShardReadinessEvidenceHandoffService evidenceHandoffService;

    public OpsShardReadinessEchoService(
            OpsShardReadinessService readinessService,
            OpsShardReadinessHardeningService hardeningService,
            OpsShardReadinessEvidenceIndexService evidenceIndexService,
            OpsShardReadinessEvidenceHandoffService evidenceHandoffService
    ) {
        this.readinessService = readinessService;
        this.hardeningService = hardeningService;
        this.evidenceIndexService = evidenceIndexService;
        this.evidenceHandoffService = evidenceHandoffService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessEchoResponse echo() {
        OpsShardReadinessResponse readiness = readinessService.readiness();
        OpsShardReadinessHardeningResponse hardening = hardeningService.hardening();
        OpsShardReadinessEvidenceIndexResponse evidenceIndex = evidenceIndexService.evidenceIndex();
        OpsShardReadinessEvidenceHandoffResponse evidenceHandoff = evidenceHandoffService.handoff();

        return new OpsShardReadinessEchoResponse(
                "advanced-order-platform",
                "Java v174",
                true,
                false,
                readiness.shardEnabled(),
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                readiness.version(),
                hardening.version(),
                evidenceIndex.version(),
                evidenceHandoff.version(),
                "append-only-new-echo-endpoint-preserves-v153-root-schema",
                "java-shard-readiness-read-only-echo.v1",
                "java-shard-readiness-echo-receipt-v174",
                preservedRootFields(),
                controllerSplitReceipts(),
                evidenceArchivePaths(readiness, hardening, evidenceIndex, evidenceHandoff),
                readOnlyEvidenceCapabilities(),
                forbiddenOperations(),
                consumerGuidance(),
                EVIDENCE_PATH,
                echoStatus(readiness, hardening, evidenceIndex, evidenceHandoff)
        );
    }

    private List<String> preservedRootFields() {
        return List.of(
                "project",
                "version",
                "readOnly",
                "executionAllowed",
                "shardEnabled",
                "shardCount",
                "slotCount",
                "routingMode",
                "evidencePath",
                "status"
        );
    }

    private List<String> controllerSplitReceipts() {
        return List.of(
                "Java v171:runtime-execution-controller-split",
                "Java v172:lifecycle-plan-controller-split",
                "Java v173:evidence-controller-split"
        );
    }

    private List<String> evidenceArchivePaths(
            OpsShardReadinessResponse readiness,
            OpsShardReadinessHardeningResponse hardening,
            OpsShardReadinessEvidenceIndexResponse evidenceIndex,
            OpsShardReadinessEvidenceHandoffResponse evidenceHandoff
    ) {
        return List.of(
                readiness.evidencePath(),
                hardening.evidencePath(),
                evidenceIndex.evidencePath(),
                evidenceHandoff.evidencePath(),
                "e/171/evidence/java-shard-readiness-runtime-execution-route-group-split-v171.json",
                "e/172/evidence/java-shard-readiness-lifecycle-plan-route-group-split-v172.json",
                "e/173/evidence/java-shard-readiness-evidence-route-group-split-v173.json"
        );
    }

    private List<String> readOnlyEvidenceCapabilities() {
        return List.of(
                "preserves-frozen-v153-root-readiness-contract",
                "exposes-versioned-shard-readiness-echo",
                "indexes-live-and-fixture-evidence-endpoints",
                "documents-controller-split-readiness",
                "supports-node-independent-consumption",
                "keeps-active-shard-router-disabled"
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

    private List<String> consumerGuidance() {
        return List.of(
                "consume-versioned-fixtures-and-evidence-archives",
                "do-not-treat-echo-as-runtime-permission",
                "do-not-open-write-routing-before-explicit-approval",
                "prefer-small-independent-java-and-mini-kv-evidence-milestones",
                "node-may-consume-after-java-evidence-is-versioned"
        );
    }

    private String echoStatus(
            OpsShardReadinessResponse readiness,
            OpsShardReadinessHardeningResponse hardening,
            OpsShardReadinessEvidenceIndexResponse evidenceIndex,
            OpsShardReadinessEvidenceHandoffResponse evidenceHandoff
    ) {
        boolean passed = "passed".equals(readiness.status())
                && "passed".equals(hardening.status())
                && "passed".equals(evidenceIndex.status())
                && "passed".equals(evidenceHandoff.status())
                && readiness.readOnly()
                && !readiness.executionAllowed()
                && !readiness.shardEnabled();
        return passed ? "passed" : "blocked";
    }
}
