package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractEvidencePacketService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.V1_CONTRACT_EVIDENCE_PACKET;
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessV1ContractEvidencePacketResponse packet() {
    return OpsShardReadinessV1ContractEvidencePacketSnapshot.v193Packet();
  }
}
