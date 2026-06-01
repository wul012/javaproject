package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractHandoffManifestService {

    public static final String ENDPOINT =
            "/api/v1/ops/shard-readiness/v1-contract-handoff-manifest";

    public static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json";

    public static final String EVIDENCE_PATH =
            "e/199/evidence/java-shard-readiness-v1-contract-handoff-manifest-v199.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractHandoffManifestResponse manifest() {
        return OpsShardReadinessV1ContractHandoffManifestSnapshot.v199Manifest();
    }
}
