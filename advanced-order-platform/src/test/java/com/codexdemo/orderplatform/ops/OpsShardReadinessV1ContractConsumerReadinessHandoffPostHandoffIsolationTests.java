package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.evidencePaths;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffIsolationTests {

    @Test
    void keepsPostHandoffEvidencePathsOutOfTheFrozenV225Payload() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();
        List<String> frozenPayloadStrings = frozenPayloadStrings(handoff);

        for (String postHandoffEvidencePath : evidencePaths()) {
            assertThat(frozenPayloadStrings)
                    .as(postHandoffEvidencePath)
                    .allSatisfy(value -> assertThat(value).doesNotContain(postHandoffEvidencePath));
        }
    }

    @Test
    void keepsFrozenV225ReceiptAndEvidencePathSeparateFromThePostHandoffCatalog() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(evidencePaths()).doesNotContain(handoff.evidencePath());
        assertThat(handoff.receiptId()).endsWith("v225");
        assertThat(handoff.evidencePath()).startsWith("e/225/");
    }

    @Test
    void keepsPostHandoffIsolationPathVersionedToV272() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                        .CONSUMER_READINESS_HANDOFF_POST_HANDOFF_ISOLATION_EVIDENCE_PATH,
                272,
                "post-handoff-isolation"
        );
    }

    private static List<String> frozenPayloadStrings(
            OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff
    ) {
        List<String> values = new ArrayList<>();
        values.add(handoff.receiptId());
        values.add(handoff.evidencePath());
        values.add(handoff.evidenceDigestEvidencePath());
        values.add(handoff.evidenceDigestReceiptId());
        values.addAll(handoff.digestEvidence());
        values.addAll(handoff.handoffGuardEvidence());
        values.addAll(handoff.handoffChecks());
        values.addAll(handoff.blockedOperations());
        return values;
    }
}
