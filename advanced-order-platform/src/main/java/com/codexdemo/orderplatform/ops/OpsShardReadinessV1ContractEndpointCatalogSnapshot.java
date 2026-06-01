package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessV1ContractEndpointCatalogSnapshot {

    private OpsShardReadinessV1ContractEndpointCatalogSnapshot() {
    }

    static OpsShardReadinessV1ContractEndpointCatalogResponse v208Catalog() {
        List<OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry> entries = v208EndpointEntries();
        return new OpsShardReadinessV1ContractEndpointCatalogResponse(
                "advanced-order-platform",
                "Java v208",
                "shard-readiness.v1",
                true,
                false,
                false,
                OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
                OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
                entries.size(),
                entries,
                entries.stream()
                        .map(entry -> "GET " + entry.liveEndpoint())
                        .toList(),
                entries.stream()
                        .map(entry -> "GET " + entry.fixtureEndpoint())
                        .toList(),
                entries.stream()
                        .map(OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry::evidencePath)
                        .toList(),
                v208BlockedOperations(),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "java-shard-readiness-v1-contract-endpoint-catalog-receipt-v208",
                OpsShardReadinessV1ContractEndpointCatalogService.EVIDENCE_PATH,
                "passed"
        );
    }

    static List<OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry> v208EndpointEntries() {
        return List.of(
                endpointEntry(
                        "alignment",
                        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentService.EVIDENCE_PATH,
                        "java-shard-readiness-v1-contract-alignment-receipt-v187"
                ),
                endpointEntry(
                        "alignment-handoff",
                        OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentHandoffService.EVIDENCE_PATH,
                        "java-shard-readiness-v1-contract-alignment-handoff-receipt-v190"
                ),
                endpointEntry(
                        "evidence-packet",
                        OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT,
                        OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractEvidencePacketService.EVIDENCE_PATH,
                        "java-shard-readiness-v1-contract-evidence-packet-receipt-v193"
                ),
                endpointEntry(
                        "operator-checklist",
                        OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT,
                        OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractOperatorChecklistService.EVIDENCE_PATH,
                        "java-shard-readiness-v1-contract-operator-checklist-receipt-v196"
                ),
                endpointEntry(
                        "handoff-manifest",
                        OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT,
                        OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractHandoffManifestService.EVIDENCE_PATH,
                        "java-shard-readiness-v1-contract-handoff-manifest-receipt-v199"
                ),
                endpointEntry(
                        "consumer-probe-plan",
                        OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerProbePlanService.EVIDENCE_PATH,
                        "java-shard-readiness-v1-contract-consumer-probe-plan-receipt-v202"
                )
        );
    }

    static List<String> v208BlockedOperations() {
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

    private static OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry endpointEntry(
            String name,
            String liveEndpoint,
            String fixtureEndpoint,
            String evidencePath,
            String receiptId
    ) {
        return new OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry(
                name,
                liveEndpoint,
                fixtureEndpoint,
                evidencePath,
                receiptId
        );
    }
}
