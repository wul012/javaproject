package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffWalkthroughIndexTests {

    @Test
    void keepsEveryCatalogReceiptCoveredByCodeWalkthroughArchive() throws IOException {
        List<String> walkthroughFiles;
        try (var files = Files.list(Path.of("代码讲解记录_生产雏形阶段3"))) {
            walkthroughFiles = files
                    .map(path -> path.getFileName().toString())
                    .toList();
        }

        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .allSatisfy(receipt -> assertThat(walkthroughFiles)
                        .as("walkthrough for v" + receipt.version())
                        .anySatisfy(fileName -> assertThat(fileName)
                                .contains("version-" + receipt.version() + "-")));
    }

    @Test
    void keepsWalkthroughIndexEvidencePathVersionedToV246() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_WALKTHROUGH_INDEX_EVIDENCE_PATH)
                .isEqualTo(
                        "e/246/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "walkthrough-index-v246.json"
                );
    }
}
