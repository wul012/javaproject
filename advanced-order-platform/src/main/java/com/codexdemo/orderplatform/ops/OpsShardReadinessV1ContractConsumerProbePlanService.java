package com.codexdemo.orderplatform.ops;

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

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractConsumerProbePlanResponse probePlan() {
        return OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202ProbePlan();
    }
}
