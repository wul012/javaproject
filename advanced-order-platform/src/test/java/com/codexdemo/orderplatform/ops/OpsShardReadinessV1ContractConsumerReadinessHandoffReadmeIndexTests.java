package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReadmeIndexTests {

    @Test
    void keepsEveryCatalogReceiptIndexedInEvidenceReadme() throws IOException {
        String readme = Files.readString(Path.of("e", "README.md"), StandardCharsets.UTF_8);

        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .allSatisfy(receipt -> assertThat(readme)
                        .as("README index for v" + receipt.version())
                        .contains("- `" + receipt.version() + "/`:"));
    }

    @Test
    void keepsReadmeIndexOrderingAlignedWithCatalogVersions() throws IOException {
        String readme = Files.readString(Path.of("e", "README.md"), StandardCharsets.UTF_8);
        int previousIndex = -1;

        for (Integer version : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .versions()) {
            int currentIndex = readme.indexOf("- `" + version + "/`:");
            assertThat(currentIndex).as("README index for v" + version).isGreaterThan(previousIndex);
            previousIndex = currentIndex;
        }
    }

    @Test
    void keepsReadmeIndexEvidencePathVersionedToV245() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_README_INDEX_EVIDENCE_PATH)
                .isEqualTo(
                        "e/245/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "readme-index-v245.json"
                );
    }
}
