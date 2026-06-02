package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffValidationCommandCoverageTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void keepsEveryCatalogEvidenceJsonWithMavenAndPlaywrightValidation() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            JsonNode validation = OBJECT_MAPPER
                    .readTree(root.resolve(receipt.evidencePath()).toFile())
                    .path("validation");
            List<String> validationEntries = new ArrayList<>();
            validation.forEach(entry -> validationEntries.add(entry.asText()));

            assertThat(validationEntries).as(receipt.evidencePath()).anyMatch(entry -> entry.contains("mvn -q"));
            assertThat(validationEntries).as(receipt.evidencePath()).anyMatch(entry -> entry.contains("Playwright"));
        }
    }

    @Test
    void keepsValidationCommandCoverageEvidencePathVersionedToV257() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_VALIDATION_COMMAND_COVERAGE_EVIDENCE_PATH)
                .isEqualTo(
                        "e/257/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "validation-command-coverage-v257.json"
                );
    }
}
