package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerProbePlanService {

  public static final String ENDPOINT =
      OpsShardReadinessService.BASE_PATH
          + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_PROBE_PLAN;

  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json";

  public static final String EVIDENCE_PATH =
      "e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessV1ContractConsumerProbePlanResponse probePlan() {
    return OpsShardReadinessV1ContractConsumerProbePlanSnapshot.v202ProbePlan();
  }
}
