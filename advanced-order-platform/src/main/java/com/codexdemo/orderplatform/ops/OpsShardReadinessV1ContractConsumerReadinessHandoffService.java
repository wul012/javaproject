package com.codexdemo.orderplatform.ops;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerReadinessHandoffService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF;

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/225/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.json";

    static final String CONSUMER_READINESS_HANDOFF_SNAPSHOT_FREEZE_EVIDENCE_PATH =
            "e/226/evidence/java-shard-readiness-v225-consumer-readiness-handoff-snapshot-freeze-v226.json";

    static final String CONSUMER_READINESS_HANDOFF_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
            "e/227/evidence/java-shard-readiness-v225-consumer-readiness-handoff-historical-compatibility-v227.json";

    static final String CONSUMER_READINESS_HANDOFF_INTEGRITY_EVIDENCE_PATH =
            "e/228/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-integrity-v228.json";

    static final String CONSUMER_READINESS_HANDOFF_ROUTE_INVENTORY_EVIDENCE_PATH =
            "e/229/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-route-inventory-v229.json";

    static final String CONSUMER_READINESS_HANDOFF_EVIDENCE_CHAIN_EVIDENCE_PATH =
            "e/230/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-evidence-chain-v230.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff() {
        return OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();
    }
}
