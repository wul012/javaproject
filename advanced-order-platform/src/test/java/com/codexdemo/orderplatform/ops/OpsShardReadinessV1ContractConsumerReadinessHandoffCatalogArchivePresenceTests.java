package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests {

    @Test
    void keepsCatalogArchiveArtifactsPresentForEveryPostHandoffReceipt() {
        Path root = Path.of("").toAbsolutePath();

        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .flatExtracting(receipt -> archivePaths(root, receipt))
                .allSatisfy(path -> assertThat(Files.exists(path))
                        .as(path.toString())
                        .isTrue());
    }

    @Test
    void keepsCatalogArchivePresenceEvidencePathVersionedToV243() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_CATALOG_ARCHIVE_PRESENCE_EVIDENCE_PATH)
                .isEqualTo(
                        "e/243/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "catalog-archive-presence-v243.json"
                );
    }

    private static List<Path> archivePaths(
            Path root,
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
    ) {
        Path evidencePath = root.resolve(receipt.evidencePath());
        String evidenceFileName = evidencePath.getFileName().toString();
        String archiveStem = evidenceFileName.substring(0, evidenceFileName.length() - ".json".length());
        Path versionRoot = root.resolve("e").resolve(String.valueOf(receipt.version()));

        return List.of(
                evidencePath,
                evidencePath.getParent().resolve(archiveStem + "-browser-snapshot.md"),
                versionRoot.resolve(archiveStem + ".html"),
                versionRoot.resolve("图片").resolve(archiveStem + ".png"),
                versionRoot.resolve("解释").resolve("说明.md")
        );
    }
}
