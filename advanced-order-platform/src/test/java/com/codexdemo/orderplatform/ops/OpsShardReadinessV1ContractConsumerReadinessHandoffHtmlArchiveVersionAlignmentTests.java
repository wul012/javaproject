package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveTestSupport.htmlArchive;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffHtmlArchiveVersionAlignmentTests {

    @Test
    void keepsEveryCatalogHtmlArchiveAlignedToItsReceiptVersion() throws IOException {
        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : receipts()) {
            Path html = htmlArchive(receipt);
            String htmlText = Files.readString(html);

            assertThat(htmlText).as(html.toString()).contains("<title>").contains("Java v" + receipt.version());
            assertThat(Files.size(html)).as(html.toString()).isGreaterThan(100L);
        }
    }

    @Test
    void keepsHtmlArchiveVersionAlignmentPathVersionedToV266() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                        .CONSUMER_READINESS_HANDOFF_HTML_ARCHIVE_VERSION_ALIGNMENT_EVIDENCE_PATH,
                266,
                "html-archive-version-alignment"
        );
    }
}
