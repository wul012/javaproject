package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessV1ContractConsumerProbePlanSnapshot {

    private OpsShardReadinessV1ContractConsumerProbePlanSnapshot() {
    }

    static OpsShardReadinessV1ContractConsumerProbePlanResponse v202ProbePlan() {
        OpsShardReadinessV1ContractHandoffManifestResponse manifest =
                OpsShardReadinessV1ContractHandoffManifestSnapshot.v199Manifest();
        return new OpsShardReadinessV1ContractConsumerProbePlanResponse(
                "advanced-order-platform",
                "Java v202",
                manifest.contractName(),
                true,
                false,
                false,
                OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT,
                OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT,
                manifest.manifestEndpoint(),
                manifest.manifestFixtureEndpoint(),
                manifest.evidencePath(),
                manifest.receiptId(),
                v202ReadTargets(manifest),
                v202FixtureTargets(manifest),
                v202ProbeSequence(manifest),
                v202RequiredEvidence(manifest),
                v202StopConditions(),
                manifest.blockedOperations(),
                v202VerificationChecks(manifest),
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
                OpsShardReadinessV1ContractConsumerProbePlanService.EVIDENCE_PATH,
                "passed"
        );
    }

    static List<String> v202ReadTargets(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
        return List.of(
                OpsShardReadinessService.ENDPOINT,
                manifest.packetEndpoint(),
                manifest.checklistEndpoint(),
                manifest.manifestEndpoint(),
                OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT
        );
    }

    static List<String> v202FixtureTargets(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
        return List.of(
                OpsShardReadinessService.FIXTURE_ENDPOINT,
                manifest.packetFixtureEndpoint(),
                manifest.checklistFixtureEndpoint(),
                manifest.manifestFixtureEndpoint(),
                OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT
        );
    }

    static List<String> v202ProbeSequence(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
        return List.of(
                "GET " + OpsShardReadinessService.ENDPOINT,
                "GET " + manifest.packetEndpoint(),
                "GET " + manifest.checklistEndpoint(),
                "GET " + manifest.manifestEndpoint(),
                "GET " + OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT,
                "GET " + OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT
        );
    }

    static List<String> v202RequiredEvidence(OpsShardReadinessV1ContractHandoffManifestResponse manifest) {
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
                OpsShardReadinessV1ContractConsumerProbePlanService.EVIDENCE_PATH
        );
    }

    static List<String> v202StopConditions() {
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

    static List<String> v202VerificationChecks(
            OpsShardReadinessV1ContractHandoffManifestResponse manifest
    ) {
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
