package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractOperatorChecklistService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.V1_CONTRACT_OPERATOR_CHECKLIST;

  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json";

  public static final String EVIDENCE_PATH =
      "e/196/evidence/java-shard-readiness-v1-contract-operator-checklist-v196.json";

  static final String SNAPSHOT_FREEZE_EVIDENCE_PATH =
      "e/194/evidence/java-shard-readiness-v193-evidence-packet-snapshot-freeze-v194.json";

  static final String HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
      "e/195/evidence/java-shard-readiness-v193-evidence-packet-historical-snapshot-compatibility-v195.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessV1ContractOperatorChecklistResponse checklist() {
    return OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196Checklist();
  }
}
