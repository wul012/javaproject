package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveTestSupport.evidenceJson;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffJsonGuardCompletenessTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void keepsEveryCatalogEvidenceJsonWithNonEmptyGuardEntries() throws IOException {
        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : receipts()) {
            JsonNode guards = OBJECT_MAPPER.readTree(evidenceJson(receipt).toFile()).path("guards");
            List<String> guardEntries = new ArrayList<>();
            guards.forEach(entry -> guardEntries.add(entry.asText()));

            assertThat(guards.isArray()).as(receipt.evidencePath()).isTrue();
            assertThat(guardEntries).as(receipt.evidencePath()).hasSizeGreaterThanOrEqualTo(2);
            assertThat(guardEntries).as(receipt.evidencePath()).allSatisfy(entry -> assertThat(entry).isNotBlank());
        }
    }

    @Test
    void keepsJsonGuardCompletenessPathVersionedToV267() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                        .CONSUMER_READINESS_HANDOFF_JSON_GUARD_COMPLETENESS_EVIDENCE_PATH,
                267,
                "json-guard-completeness"
        );
    }
}
