package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractConsumerEvidenceDigestService {

  public static final String ENDPOINT =
      OpsShardReadinessService.BASE_PATH
          + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_EVIDENCE_DIGEST;

  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.fixture.json";

  public static final String EVIDENCE_PATH =
      "e/220/evidence/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.json";

  static final String CONSUMER_EVIDENCE_DIGEST_SNAPSHOT_FREEZE_EVIDENCE_PATH =
      "e/221/evidence/java-shard-readiness-v220-consumer-evidence-digest-snapshot-freeze-v221.json";

  static final String CONSUMER_EVIDENCE_DIGEST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
      "e/222/evidence/java-shard-readiness-v220-consumer-evidence-digest-historical-compatibility-v222.json";

  static final String CONSUMER_EVIDENCE_DIGEST_INTEGRITY_EVIDENCE_PATH =
      "e/223/evidence/java-shard-readiness-v1-contract-consumer-evidence-digest-integrity-v223.json";

  public static final String CONSUMER_EVIDENCE_DIGEST_READINESS_COMPLETION_EVIDENCE_PATH =
      "e/224/evidence/java-shard-readiness-v1-contract-consumer-readiness-completion-v224.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest() {
    return OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
  }
}
