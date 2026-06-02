package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffScreenshotArtifactCompletenessTests {

    @Test
    void keepsEveryCatalogScreenshotAsNonEmptyPngArtifact() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            Path evidencePath = root.resolve(receipt.evidencePath());
            String fileName = evidencePath.getFileName().toString();
            String stem = fileName.substring(0, fileName.length() - ".json".length());
            Path screenshot = root.resolve("e")
                    .resolve(String.valueOf(receipt.version()))
                    .resolve("图片")
                    .resolve(stem + ".png");
            byte[] png = Files.readAllBytes(screenshot);

            assertThat(png.length).as(screenshot.toString()).isGreaterThan(1_000);
            assertThat(png[0]).as(screenshot.toString()).isEqualTo((byte) 0x89);
            assertThat(png[1]).as(screenshot.toString()).isEqualTo((byte) 0x50);
            assertThat(png[2]).as(screenshot.toString()).isEqualTo((byte) 0x4E);
            assertThat(png[3]).as(screenshot.toString()).isEqualTo((byte) 0x47);
        }
    }

    @Test
    void keepsScreenshotArtifactCompletenessPathVersionedToV265() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_SCREENSHOT_ARTIFACT_COMPLETENESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/265/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "screenshot-artifact-completeness-v265.json"
                );
    }
}
