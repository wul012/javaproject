package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerVerificationChecklistService {

  public static final String ENDPOINT =
      OpsShardReadinessService.BASE_PATH
          + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST;

  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.fixture.json";

  public static final String EVIDENCE_PATH =
      "e/215/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.json";

  static final String HANDOFF_BUNDLE_SNAPSHOT_FREEZE_EVIDENCE_PATH =
      "e/212/evidence/java-shard-readiness-v211-consumer-handoff-bundle-snapshot-freeze-v212.json";

  static final String HANDOFF_BUNDLE_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
      "e/213/evidence/java-shard-readiness-v211-consumer-handoff-bundle-historical-compatibility-v213.json";

  public static final String HANDOFF_BUNDLE_INTEGRITY_EVIDENCE_PATH =
      "e/214/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-integrity-v214.json";

  static final String CONSUMER_VERIFICATION_CHECKLIST_SNAPSHOT_FREEZE_EVIDENCE_PATH =
      "e/216/evidence/java-shard-readiness-v215-consumer-verification-checklist-snapshot-freeze-v216.json";

  static final String CONSUMER_VERIFICATION_CHECKLIST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
      "e/217/evidence/java-shard-readiness-v215-consumer-verification-checklist-historical-compatibility-v217.json";

  static final String CONSUMER_VERIFICATION_CHECKLIST_INTEGRITY_EVIDENCE_PATH =
      "e/218/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-integrity-v218.json";

  public static final String CONSUMER_VERIFICATION_CHECKLIST_ROUTE_INVENTORY_EVIDENCE_PATH =
      "e/219/evidence/java-shard-readiness-v1-contract-consumer-route-inventory-v219.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist() {
    return OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
  }
}
