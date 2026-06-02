package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffJsonMetadataCompletenessTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<String> REQUIRED_FIELDS = List.of(
            "receiptId",
            "version",
            "status",
            "scope",
            "summary",
            "guards",
            "validation",
            "boundary"
    );

    @Test
    void keepsEveryCatalogEvidenceJsonWithCoreMetadataFields() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            JsonNode evidence = OBJECT_MAPPER.readTree(root.resolve(receipt.evidencePath()).toFile());

            assertThat(REQUIRED_FIELDS).as(receipt.evidencePath()).allSatisfy(field ->
                    assertThat(evidence.hasNonNull(field)).as(field).isTrue());
            assertThat(evidence.path("receiptId").asText()).as(receipt.evidencePath()).contains("v" + receipt.version());
            assertThat(evidence.path("version").asText()).as(receipt.evidencePath()).isEqualTo("Java v" + receipt.version());
        }
    }

    @Test
    void keepsJsonMetadataCompletenessPathVersionedToV268() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_JSON_METADATA_COMPLETENESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/268/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "json-metadata-completeness-v268.json"
                );
    }
}
