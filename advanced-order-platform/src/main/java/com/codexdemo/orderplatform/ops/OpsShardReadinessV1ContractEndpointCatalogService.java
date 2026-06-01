package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractEndpointCatalogService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths.V1_CONTRACT_ENDPOINT_CATALOG;

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-endpoint-catalog-v208.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/208/evidence/java-shard-readiness-v1-contract-endpoint-catalog-v208.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractEndpointCatalogResponse catalog() {
        List<OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry> entries = endpointEntries();
        return new OpsShardReadinessV1ContractEndpointCatalogResponse(
                "advanced-order-platform",
                "Java v208",
                "shard-readiness.v1",
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
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
                blockedOperations(),
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
                EVIDENCE_PATH,
                "passed"
        );
    }

    private List<OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry> endpointEntries() {
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

    private OpsShardReadinessV1ContractEndpointCatalogResponse.EndpointEntry endpointEntry(
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

    private List<String> blockedOperations() {
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
}
