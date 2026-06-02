package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffExplanationArchiveCompletenessTests {

    @Test
    void keepsEveryCatalogExplanationArchiveNonEmpty() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            Path explanation = root.resolve("e")
                    .resolve(String.valueOf(receipt.version()))
                    .resolve("解释")
                    .resolve("说明.md");

            assertThat(Files.size(explanation)).as(explanation.toString()).isGreaterThan(20L);
        }
    }

    @Test
    void keepsExplanationArchiveCompletenessPathVersionedToV263() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_EXPLANATION_ARCHIVE_COMPLETENESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/263/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "explanation-archive-completeness-v263.json"
                );
    }
}
