package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffBrowserSnapshotCompletenessTests {

    @Test
    void keepsEveryCatalogBrowserSnapshotNonEmptyAndReadable() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            Path evidencePath = root.resolve(receipt.evidencePath());
            String fileName = evidencePath.getFileName().toString();
            String stem = fileName.substring(0, fileName.length() - ".json".length());
            Path snapshot = evidencePath.getParent().resolve(stem + "-browser-snapshot.md");
            String snapshotText = Files.readString(snapshot);

            assertThat(snapshotText).as(snapshot.toString()).contains("main").contains("heading");
            assertThat(Files.size(snapshot)).as(snapshot.toString()).isGreaterThan(20L);
        }
    }

    @Test
    void keepsBrowserSnapshotCompletenessPathVersionedToV264() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_BROWSER_SNAPSHOT_COMPLETENESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/264/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "browser-snapshot-completeness-v264.json"
                );
    }
}
