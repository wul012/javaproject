package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffHtmlArchiveVersionAlignmentTests {

    @Test
    void keepsEveryCatalogHtmlArchiveAlignedToItsReceiptVersion() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            Path evidencePath = root.resolve(receipt.evidencePath());
            String fileName = evidencePath.getFileName().toString();
            String stem = fileName.substring(0, fileName.length() - ".json".length());
            Path html = root.resolve("e")
                    .resolve(String.valueOf(receipt.version()))
                    .resolve(stem + ".html");
            String htmlText = Files.readString(html);

            assertThat(htmlText).as(html.toString()).contains("<title>").contains("Java v" + receipt.version());
            assertThat(Files.size(html)).as(html.toString()).isGreaterThan(100L);
        }
    }

    @Test
    void keepsHtmlArchiveVersionAlignmentPathVersionedToV266() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_HTML_ARCHIVE_VERSION_ALIGNMENT_EVIDENCE_PATH)
                .isEqualTo(
                        "e/266/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "html-archive-version-alignment-v266.json"
                );
    }
}
