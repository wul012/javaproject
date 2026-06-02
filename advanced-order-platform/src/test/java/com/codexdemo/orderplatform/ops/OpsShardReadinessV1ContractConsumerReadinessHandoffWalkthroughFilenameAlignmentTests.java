package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffWalkthroughFilenameAlignmentTests {

    @Test
    void keepsEveryCatalogWalkthroughFilenameAlignedToVersionAndScopeSlug() throws IOException {
        List<String> walkthroughFiles;
        try (var files = Files.list(Path.of("代码讲解记录_生产雏形阶段3"))) {
            walkthroughFiles = files
                    .map(path -> path.getFileName().toString().toLowerCase())
                    .toList();
        }

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            String versionToken = "version-" + receipt.version() + "-";
            String scopeSlug = receipt.scope().replace(' ', '-').replace('/', '-').toLowerCase();

            assertThat(walkthroughFiles)
                    .as("walkthrough filename for v" + receipt.version())
                    .anySatisfy(fileName -> assertThat(fileName)
                            .contains(versionToken)
                            .contains(scopeSlug));
        }
    }

    @Test
    void keepsWalkthroughFilenameAlignmentPathVersionedToV277() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_WALKTHROUGH_FILENAME_ALIGNMENT_EVIDENCE_PATH)
                .isEqualTo(
                        "e/277/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "walkthrough-filename-alignment-v277.json"
                );
    }
}
