package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffJsonGuardCompletenessTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void keepsEveryCatalogEvidenceJsonWithNonEmptyGuardEntries() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            JsonNode guards = OBJECT_MAPPER.readTree(root.resolve(receipt.evidencePath()).toFile()).path("guards");
            List<String> guardEntries = new ArrayList<>();
            guards.forEach(entry -> guardEntries.add(entry.asText()));

            assertThat(guards.isArray()).as(receipt.evidencePath()).isTrue();
            assertThat(guardEntries).as(receipt.evidencePath()).hasSizeGreaterThanOrEqualTo(2);
            assertThat(guardEntries).as(receipt.evidencePath()).allSatisfy(entry -> assertThat(entry).isNotBlank());
        }
    }

    @Test
    void keepsJsonGuardCompletenessPathVersionedToV267() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_JSON_GUARD_COMPLETENESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/267/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "json-guard-completeness-v267.json"
                );
    }
}
