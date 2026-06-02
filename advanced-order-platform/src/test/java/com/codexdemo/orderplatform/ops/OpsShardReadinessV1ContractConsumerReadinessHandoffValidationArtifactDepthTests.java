package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffValidationArtifactDepthTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void keepsEveryCatalogEvidenceJsonWithMavenAndBrowserValidationDepth() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            JsonNode validation = OBJECT_MAPPER
                    .readTree(root.resolve(receipt.evidencePath()).toFile())
                    .path("validation");
            List<String> entries = new ArrayList<>();
            validation.forEach(entry -> entries.add(entry.asText()));

            assertThat(validation.isArray()).as(receipt.evidencePath()).isTrue();
            assertThat(entries).as(receipt.evidencePath()).hasSizeGreaterThanOrEqualTo(2);
            assertThat(entries).as(receipt.evidencePath()).anyMatch(entry -> entry.contains("mvn -q"));
            assertThat(entries).as(receipt.evidencePath()).anyMatch(entry -> entry.contains("Playwright"));
            assertThat(entries).as(receipt.evidencePath()).allSatisfy(entry -> assertThat(entry).isNotBlank());
        }
    }

    @Test
    void keepsValidationArtifactDepthPathVersionedToV275() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_VALIDATION_ARTIFACT_DEPTH_EVIDENCE_PATH)
                .isEqualTo(
                        "e/275/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "validation-artifact-depth-v275.json"
                );
    }
}
