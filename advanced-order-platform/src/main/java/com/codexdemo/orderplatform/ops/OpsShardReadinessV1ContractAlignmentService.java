package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractAlignmentService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT;
    static final String FIXTURE_ENDPOINT =
            "/contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json";
    static final String EVIDENCE_PATH =
            "e/187/evidence/java-shard-readiness-v1-contract-alignment-v187.json";

    @Transactional(readOnly = true)
    public OpsShardReadinessV1ContractAlignmentResponse alignment() {
        OpsShardReadinessResponse readiness =
                OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceReadiness();

        boolean readOnlyMatches = readiness.readOnly();
        boolean executionBlocked = !readiness.executionAllowed();
        boolean shardRoutingDisabled = !readiness.shardEnabled();
        boolean shardCountsClosed = readiness.shardCount() == 0 && readiness.slotCount() == 0;
        boolean routingModeFixtureBacked = "fixture".equals(readiness.routingMode());
        boolean passed = OpsShardReadinessV1Contract.alignsWithReadOnlyContract(readiness);

        return new OpsShardReadinessV1ContractAlignmentResponse(
                "advanced-order-platform",
                "Java v187",
                OpsShardReadinessV1ContractAlignmentSnapshot.v187ContractName(),
                true,
                false,
                false,
                readiness.version(),
                OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceEndpoint(),
                OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceFixtureEndpoint(),
                readiness.evidencePath(),
                OpsShardReadinessV1ContractAlignmentSnapshot.v187MinimalFields(),
                true,
                readOnlyMatches,
                executionBlocked,
                shardRoutingDisabled,
                shardCountsClosed,
                routingModeFixtureBacked,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "java-shard-readiness-v1-contract-alignment-receipt-v187",
                verificationChecks(
                        readiness,
                        readOnlyMatches,
                        executionBlocked,
                        shardRoutingDisabled,
                        shardCountsClosed,
                        routingModeFixtureBacked
                ),
                blockedOperations(),
                EVIDENCE_PATH,
                passed ? "passed" : "blocked"
        );
    }

    private List<String> verificationChecks(
            OpsShardReadinessResponse readiness,
            boolean readOnlyMatches,
            boolean executionBlocked,
            boolean shardRoutingDisabled,
            boolean shardCountsClosed,
            boolean routingModeFixtureBacked
    ) {
        return List.of(
                "contract-name:" + OpsShardReadinessV1ContractAlignmentSnapshot.v187ContractName(),
                "source-readiness-version:" + readiness.version(),
                "minimal-field-count:" + OpsShardReadinessV1ContractAlignmentSnapshot.v187MinimalFields().size(),
                "read-only-matches:" + readOnlyMatches,
                "execution-blocked:" + executionBlocked,
                "shard-routing-disabled:" + shardRoutingDisabled,
                "shard-counts-closed:" + shardCountsClosed,
                "routing-mode-fixture-backed:" + routingModeFixtureBacked
        );
    }

    private List<String> blockedOperations() {
        return List.of(
                "write-routing",
                "active-shard-router",
                "credential-value-read",
                "raw-endpoint-parse",
                "managed-audit-connection",
                "deployment-or-rollback",
                "node-start-or-stop-java-or-mini-kv"
        );
    }
}
