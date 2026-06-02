package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReadmeDescriptionAlignmentTests {

    @Test
    void keepsEveryCatalogReadmeEntryDescriptiveAndScopeAligned() throws IOException {
        List<String> readmeLines = Files.readAllLines(Path.of("e", "README.md"), StandardCharsets.UTF_8);

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            String prefix = "- `" + receipt.version() + "/`:";
            String line = readmeLines.stream()
                    .filter(candidate -> candidate.startsWith(prefix))
                    .findFirst()
                    .orElse("");

            assertThat(line).as("README line for v" + receipt.version()).contains("readiness handoff");
            assertThat(normalizeScopeText(line)).as("README line for v" + receipt.version())
                    .contains(normalizeScopeText(receipt.scope()));
        }
    }

    @Test
    void keepsReadmeDescriptionAlignmentPathVersionedToV276() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_README_DESCRIPTION_ALIGNMENT_EVIDENCE_PATH)
                .isEqualTo(
                        "e/276/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "readme-description-alignment-v276.json"
                );
    }

    private static String normalizeScopeText(String value) {
        return value.toLowerCase()
                .replace('-', ' ')
                .replace('/', ' ');
    }
}
