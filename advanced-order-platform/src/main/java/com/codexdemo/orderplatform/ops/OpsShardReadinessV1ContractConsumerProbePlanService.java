package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerProbePlanService {

    public static final String ENDPOINT =
            "/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan";

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json";

    private final OpsShardReadinessV1ContractHandoffManifestService manifestService;

    public OpsShardReadinessV1ContractConsumerProbePlanService(
            OpsShardReadinessV1ContractHandoffManifestService manifestService
    ) {
        this.manifestService = manifestService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractConsumerProbePlanResponse probePlan() {
        OpsShardReadinessV1ContractHandoffManifestResponse manifest = manifestService.manifest();
        return new OpsShardReadinessV1ContractConsumerProbePlanResponse(
                "advanced-order-platform",
                "Java v202",
                manifest.contractName(),
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
                manifest.manifestEndpoint(),
                manifest.manifestFixtureEndpoint(),
                manifest.evidencePath(),
                manifest.receiptId(),
                readTargets(manifest),
                fixtureTargets(manifest),
                probeSequence(manifest),
                requiredEvidence(manifest),
                stopConditions(),
                manifest.blockedOperations(),
                verificationChecks(manifest),
                true,
                false,
                false,
                false,
                manifest.writeRoutingAllowed(),
                manifest.activeShardRouterAllowed(),
                manifest.credentialValueRead(),
                manifest.rawEndpointParsed(),
                manifest.managedAuditConnectionAllowed(),
                manifest.deploymentOrRollbackAllowed(),
                manifest.nodeMayStartOrStopJavaOrMiniKv(),
                "java-shard-readiness-v1-contract-consumer-probe-plan-receipt-v202",
                EVIDENCE_PATH,
                "passed"
        );
    }

    private static List<String> readTargets(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
        return List.of(
                OpsShardReadinessService.ENDPOINT,
                manifest.packetEndpoint(),
                manifest.checklistEndpoint(),
                manifest.manifestEndpoint(),
                ENDPOINT
        );
    }

    private static List<String> fixtureTargets(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
        return List.of(
                OpsShardReadinessService.FIXTURE_ENDPOINT,
                manifest.packetFixtureEndpoint(),
                manifest.checklistFixtureEndpoint(),
                manifest.manifestFixtureEndpoint(),
                FIXTURE_ENDPOINT
        );
    }

    private static List<String> probeSequence(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
        return List.of(
                "GET " + OpsShardReadinessService.ENDPOINT,
                "GET " + manifest.packetEndpoint(),
                "GET " + manifest.checklistEndpoint(),
                "GET " + manifest.manifestEndpoint(),
                "GET " + ENDPOINT,
                "GET " + FIXTURE_ENDPOINT
        );
    }

    private static List<String> requiredEvidence(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
        return List.of(
                manifest.packetEvidencePath(),
                "e/194/evidence/java-shard-readiness-v193-contract-evidence-packet-snapshot-freeze-v194.json",
                "e/195/evidence/java-shard-readiness-v193-contract-evidence-packet-historical-snapshot-compatibility-v195.json",
                manifest.checklistEvidencePath(),
                "e/197/evidence/java-shard-readiness-v196-operator-checklist-snapshot-freeze-v197.json",
                "e/198/evidence/java-shard-readiness-v196-operator-checklist-historical-snapshot-compatibility-v198.json",
                manifest.evidencePath(),
                "e/200/evidence/java-shard-readiness-v199-handoff-manifest-snapshot-freeze-v200.json",
                "e/201/evidence/java-shard-readiness-v199-handoff-manifest-historical-snapshot-compatibility-v201.json",
                EVIDENCE_PATH
        );
    }

    private static List<String> stopConditions() {
        return List.of(
                "missing-read-only-window",
                "non-get-request-required",
                "upstream-action-required",
                "credential-value-required",
                "raw-endpoint-url-required",
                "managed-audit-connection-required",
                "java-or-mini-kv-process-control-required",
                "write-routing-or-active-shard-router-required"
        );
    }

    private static List<String> verificationChecks(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
        return List.of(
                "contract-name:" + manifest.contractName(),
                "manifest-endpoint:" + manifest.manifestEndpoint(),
                "read-target-count:5",
                "fixture-target-count:5",
                "probe-sequence-count:6",
                "required-evidence-count:10",
                "stop-condition-count:8",
                "probes-are-get-only:true",
                "upstream-actions-allowed:false",
                "execution-allowed:false"
        );
    }
}
