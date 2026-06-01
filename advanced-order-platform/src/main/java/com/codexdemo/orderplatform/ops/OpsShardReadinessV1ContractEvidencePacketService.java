package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractEvidencePacketService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/v1-contract-evidence-packet";
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json";
    static final String EVIDENCE_PATH =
            "e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractEvidencePacketResponse packet() {
        return OpsShardReadinessV1ContractEvidencePacketSnapshot.v193Packet();
    }

}
