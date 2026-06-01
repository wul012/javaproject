package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot {

    private OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot() {
    }

    static OpsShardReadinessV1ContractConsumerHandoffBundleResponse v211Bundle() {
        OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
                OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog();
        return new OpsShardReadinessV1ContractConsumerHandoffBundleResponse(
                "advanced-order-platform",
                "Java v211",
                catalog.contractName(),
                true,
                false,
                false,
                OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
                OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
                catalog.endpointCatalogEndpoint(),
                catalog.endpointCatalogFixtureEndpoint(),
                catalog.evidencePath(),
                catalog.receiptId(),
                catalog.contractEndpointCount(),
                catalog.liveProbeEndpoints(),
                catalog.fixtureProbeEndpoints(),
                v211RequiredEvidence(catalog),
                v211HandoffEvidence(catalog),
                catalog.blockedOperations(),
                catalog.probesAreGetOnly(),
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
                "java-shard-readiness-v1-contract-consumer-handoff-bundle-receipt-v211",
                OpsShardReadinessV1ContractConsumerHandoffBundleService.EVIDENCE_PATH,
                "passed"
        );
    }

    static List<String> v211RequiredEvidence(OpsShardReadinessV1ContractEndpointCatalogResponse catalog) {
        return List.of(
                catalog.evidencePaths().get(0),
                catalog.evidencePaths().get(1),
                catalog.evidencePaths().get(2),
                catalog.evidencePaths().get(3),
                catalog.evidencePaths().get(4),
                catalog.evidencePaths().get(5),
                catalog.evidencePath(),
                OpsShardReadinessV1ContractConsumerHandoffBundleService
                        .ENDPOINT_CATALOG_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerHandoffBundleService
                        .ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH
        );
    }

    static List<String> v211HandoffEvidence(OpsShardReadinessV1ContractEndpointCatalogResponse catalog) {
        return List.of(
                catalog.evidencePath(),
                OpsShardReadinessV1ContractConsumerHandoffBundleService
                        .ENDPOINT_CATALOG_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerHandoffBundleService
                        .ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                OpsShardReadinessV1ContractConsumerHandoffBundleService.EVIDENCE_PATH
        );
    }
}
