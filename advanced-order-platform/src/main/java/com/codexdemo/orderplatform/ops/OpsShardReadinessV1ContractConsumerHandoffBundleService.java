package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerHandoffBundleService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_HANDOFF_BUNDLE;

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/211/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.json";

    static final String ENDPOINT_CATALOG_SNAPSHOT_FREEZE_EVIDENCE_PATH =
            "e/209/evidence/java-shard-readiness-v208-endpoint-catalog-snapshot-freeze-v209.json";

    static final String ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
            "e/210/evidence/java-shard-readiness-v208-endpoint-catalog-historical-compatibility-v210.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractConsumerHandoffBundleResponse bundle() {
        OpsShardReadinessV1ContractEndpointCatalogResponse catalog =
                OpsShardReadinessV1ContractEndpointCatalogSnapshot.v208Catalog();
        return new OpsShardReadinessV1ContractConsumerHandoffBundleResponse(
                "advanced-order-platform",
                "Java v211",
                catalog.contractName(),
                true,
                false,
                false,
                ENDPOINT,
                FIXTURE_ENDPOINT,
                catalog.endpointCatalogEndpoint(),
                catalog.endpointCatalogFixtureEndpoint(),
                catalog.evidencePath(),
                catalog.receiptId(),
                catalog.contractEndpointCount(),
                catalog.liveProbeEndpoints(),
                catalog.fixtureProbeEndpoints(),
                requiredEvidence(catalog),
                handoffEvidence(catalog),
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
                EVIDENCE_PATH,
                "passed"
        );
    }

    private List<String> requiredEvidence(OpsShardReadinessV1ContractEndpointCatalogResponse catalog) {
        return List.of(
                catalog.evidencePaths().get(0),
                catalog.evidencePaths().get(1),
                catalog.evidencePaths().get(2),
                catalog.evidencePaths().get(3),
                catalog.evidencePaths().get(4),
                catalog.evidencePaths().get(5),
                catalog.evidencePath(),
                ENDPOINT_CATALOG_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH
        );
    }

    private List<String> handoffEvidence(OpsShardReadinessV1ContractEndpointCatalogResponse catalog) {
        return List.of(
                catalog.evidencePath(),
                ENDPOINT_CATALOG_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                ENDPOINT_CATALOG_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                EVIDENCE_PATH
        );
    }
}
