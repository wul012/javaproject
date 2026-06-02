package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffEvidenceScopeSummaryTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void keepsEveryCatalogEvidenceJsonWithReadinessHandoffScopeAndSummary() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            JsonNode evidence = OBJECT_MAPPER.readTree(root.resolve(receipt.evidencePath()).toFile());

            assertThat(evidence.path("scope").asText())
                    .as(receipt.evidencePath())
                    .containsIgnoringCase("readiness handoff");
            assertThat(evidence.path("summary").asText())
                    .as(receipt.evidencePath())
                    .isNotBlank();
        }
    }

    @Test
    void keepsEvidenceScopeSummaryPathVersionedToV260() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_EVIDENCE_SCOPE_SUMMARY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/260/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "evidence-scope-summary-v260.json"
                );
    }
}
