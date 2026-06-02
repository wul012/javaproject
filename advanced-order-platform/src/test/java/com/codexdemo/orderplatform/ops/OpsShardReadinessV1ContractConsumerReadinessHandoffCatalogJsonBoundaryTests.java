package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogJsonBoundaryTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void keepsEveryCatalogEvidenceJsonReadOnlyAndNonExecutable() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            JsonNode evidence = OBJECT_MAPPER.readTree(root.resolve(receipt.evidencePath()).toFile());

            assertThat(evidence.path("version").asText())
                    .as(receipt.evidencePath())
                    .isEqualTo("Java v" + receipt.version());
            assertThat(evidence.path("status").asText()).as(receipt.evidencePath()).isEqualTo("passed");
            assertThat(evidence.path("readOnly").asBoolean()).as(receipt.evidencePath()).isTrue();
            assertThat(evidence.path("executionAllowed").asBoolean()).as(receipt.evidencePath()).isFalse();
        }
    }

    @Test
    void keepsCatalogJsonBoundaryEvidencePathVersionedToV244() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_CATALOG_JSON_BOUNDARY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/244/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "catalog-json-boundary-v244.json"
                );
    }
}
