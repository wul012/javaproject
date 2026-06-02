package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveSlugParityTests {

    @Test
    void keepsArchiveArtifactSlugsAlignedWithCatalogEvidenceJson() {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            Path evidencePath = root.resolve(receipt.evidencePath());
            String evidenceFileName = evidencePath.getFileName().toString();
            String archiveStem = evidenceFileName.substring(0, evidenceFileName.length() - ".json".length());
            Path versionRoot = root.resolve("e").resolve(String.valueOf(receipt.version()));

            assertThat(evidencePath.getFileName().toString()).as(receipt.evidencePath()).isEqualTo(archiveStem + ".json");
            assertThat(evidencePath.getParent().resolve(archiveStem + "-browser-snapshot.md").getFileName().toString())
                    .as(receipt.evidencePath())
                    .isEqualTo(archiveStem + "-browser-snapshot.md");
            assertThat(versionRoot.resolve(archiveStem + ".html").getFileName().toString())
                    .as(receipt.evidencePath())
                    .isEqualTo(archiveStem + ".html");
            assertThat(versionRoot.resolve("图片").resolve(archiveStem + ".png").getFileName().toString())
                    .as(receipt.evidencePath())
                    .isEqualTo(archiveStem + ".png");
        }
    }

    @Test
    void keepsArchiveSlugParityPathVersionedToV262() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_ARCHIVE_SLUG_PARITY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/262/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "archive-slug-parity-v262.json"
                );
    }
}
