package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessV1ContractHandoffManifestSnapshot {

    private OpsShardReadinessV1ContractHandoffManifestSnapshot() {
    }

    static OpsShardReadinessV1ContractHandoffManifestResponse v199Manifest() {
        OpsShardReadinessV1ContractOperatorChecklistResponse checklist =
                OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196Checklist();
        return new OpsShardReadinessV1ContractHandoffManifestResponse(
                "advanced-order-platform",
                "Java v199",
                checklist.contractName(),
                true,
                false,
                false,
                OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT,
                OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT,
                checklist.packetEndpoint(),
                checklist.packetFixtureEndpoint(),
                checklist.packetEvidencePath(),
                checklist.checklistEndpoint(),
                checklist.checklistFixtureEndpoint(),
                checklist.evidencePath(),
                v199PrerequisiteEvidence(checklist),
                v199ManifestSections(),
                v199ConsumerReadTargets(checklist),
                v199ConsumerFixtureTargets(checklist),
                v199OperatorHandoffChecks(),
                checklist.blockedOperations(),
                v199VerificationChecks(checklist),
                checklist.packetFrozen(),
                true,
                checklist.historicalSnapshotsProtected(),
                checklist.writeRoutingAllowed(),
                checklist.activeShardRouterAllowed(),
                checklist.credentialValueRead(),
                checklist.rawEndpointParsed(),
                checklist.managedAuditConnectionAllowed(),
                checklist.deploymentOrRollbackAllowed(),
                checklist.nodeMayStartOrStopJavaOrMiniKv(),
                "java-shard-readiness-v1-contract-handoff-manifest-receipt-v199",
                OpsShardReadinessV1ContractHandoffManifestService.EVIDENCE_PATH,
                "passed"
        );
    }

    static List<String> v199PrerequisiteEvidence(
            OpsShardReadinessV1ContractOperatorChecklistResponse checklist
    ) {
        return List.of(
                checklist.packetEvidencePath(),
                checklist.packetSnapshotFreezeEvidencePath(),
                checklist.packetHistoricalCompatibilityEvidencePath(),
                checklist.evidencePath(),
                "e/197/evidence/java-shard-readiness-v196-operator-checklist-snapshot-freeze-v197.json",
                "e/198/evidence/java-shard-readiness-v196-operator-checklist-historical-snapshot-compatibility-v198.json",
                OpsShardReadinessV1ContractHandoffManifestService.EVIDENCE_PATH
        );
    }

    static List<String> v199ManifestSections() {
        return List.of(
                "contract-summary",
                "read-only-endpoints",
                "fixture-endpoints",
                "required-evidence",
                "operator-handoff-checks",
                "blocked-operations"
        );
    }

    static List<String> v199ConsumerReadTargets(
            OpsShardReadinessV1ContractOperatorChecklistResponse checklist
    ) {
        return List.of(
                OpsShardReadinessService.ENDPOINT,
                checklist.packetEndpoint(),
                checklist.checklistEndpoint(),
                OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT
        );
    }

    static List<String> v199ConsumerFixtureTargets(
            OpsShardReadinessV1ContractOperatorChecklistResponse checklist
    ) {
        return List.of(
                OpsShardReadinessService.FIXTURE_ENDPOINT,
                checklist.packetFixtureEndpoint(),
                checklist.checklistFixtureEndpoint(),
                OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT
        );
    }

    static List<String> v199OperatorHandoffChecks() {
        return List.of(
                "read-manifest-with-get-only",
                "verify-packet-and-checklist-receipts-are-present",
                "verify-snapshot-freeze-evidence-before-consuming-current-registry",
                "verify-historical-compatibility-evidence-before-adding-new-consumers",
                "keep-upstream-actions-disabled"
        );
    }

    static List<String> v199VerificationChecks(
            OpsShardReadinessV1ContractOperatorChecklistResponse checklist
    ) {
        return List.of(
                "contract-name:" + checklist.contractName(),
                "packet-endpoint:" + checklist.packetEndpoint(),
                "checklist-endpoint:" + checklist.checklistEndpoint(),
                "manifest-section-count:6",
                "consumer-read-target-count:4",
                "consumer-fixture-target-count:4",
                "operator-handoff-check-count:5",
                "prerequisite-evidence-count:7",
                "execution-allowed:false"
        );
    }
}
