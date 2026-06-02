package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveTestSupport.browserSnapshot;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffBrowserSnapshotCompletenessTests {

    @Test
    void keepsEveryCatalogBrowserSnapshotNonEmptyAndReadable() throws IOException {
        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : receipts()) {
            Path snapshot = browserSnapshot(receipt);
            String snapshotText = Files.readString(snapshot);

            assertThat(snapshotText).as(snapshot.toString()).contains("main").contains("heading");
            assertThat(Files.size(snapshot)).as(snapshot.toString()).isGreaterThan(20L);
        }
    }

    @Test
    void keepsBrowserSnapshotCompletenessPathVersionedToV264() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_BROWSER_SNAPSHOT_COMPLETENESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/264/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "browser-snapshot-completeness-v264.json"
                );
    }
}
