package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveTestSupport.screenshot;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffScreenshotArtifactCompletenessTests {

    @Test
    void keepsEveryCatalogScreenshotAsNonEmptyPngArtifact() throws IOException {
        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : receipts()) {
            Path screenshotPath = screenshot(receipt);
            byte[] png = Files.readAllBytes(screenshotPath);

            assertThat(png.length).as(screenshotPath.toString()).isGreaterThan(1_000);
            assertThat(png[0]).as(screenshotPath.toString()).isEqualTo((byte) 0x89);
            assertThat(png[1]).as(screenshotPath.toString()).isEqualTo((byte) 0x50);
            assertThat(png[2]).as(screenshotPath.toString()).isEqualTo((byte) 0x4E);
            assertThat(png[3]).as(screenshotPath.toString()).isEqualTo((byte) 0x47);
        }
    }

    @Test
    void keepsScreenshotArtifactCompletenessPathVersionedToV265() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_SCREENSHOT_ARTIFACT_COMPLETENESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/265/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "screenshot-artifact-completeness-v265.json"
                );
    }
}
