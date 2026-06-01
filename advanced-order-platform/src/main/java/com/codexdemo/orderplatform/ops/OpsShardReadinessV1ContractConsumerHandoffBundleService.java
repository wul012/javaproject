package com.codexdemo.orderplatform.ops;

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
        return OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle();
    }
}
