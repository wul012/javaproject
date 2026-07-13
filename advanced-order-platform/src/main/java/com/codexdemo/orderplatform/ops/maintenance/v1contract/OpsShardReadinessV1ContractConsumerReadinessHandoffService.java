package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerReadinessHandoffService {

  public static final String ENDPOINT =
      OpsShardReadinessService.BASE_PATH
          + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF;

  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json";

  public static final String EVIDENCE_PATH =
      "e/225/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff() {
    return OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();
  }
}
