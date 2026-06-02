package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffBlockedOperationCatalogTests {

    private static final List<String> BLOCKED_OPERATIONS = List.of(
            "write-routing",
            "active-shard-router",
            "credential-value-read",
            "raw-endpoint-parse",
            "managed-audit-connection",
            "deployment-or-rollback",
            "node-start-or-stop-java-or-mini-kv"
    );

    @Test
    void keepsConsumerReadinessChainOnTheSameBlockedOperationCatalog() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(checklist.blockedOperations()).containsExactlyElementsOf(BLOCKED_OPERATIONS);
        assertThat(digest.blockedOperations()).containsExactlyElementsOf(BLOCKED_OPERATIONS);
        assertThat(handoff.blockedOperations()).containsExactlyElementsOf(BLOCKED_OPERATIONS);
        assertThat(handoff.blockedOperations()).doesNotHaveDuplicates();
    }

    @Test
    void keepsBlockedOperationCatalogEvidencePathVersionedToV247() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_BLOCKED_OPERATION_CATALOG_EVIDENCE_PATH)
                .isEqualTo(
                        "e/247/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "blocked-operation-catalog-v247.json"
                );
    }
}
