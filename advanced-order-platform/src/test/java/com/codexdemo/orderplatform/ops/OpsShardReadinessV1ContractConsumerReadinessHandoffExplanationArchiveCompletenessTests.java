package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveTestSupport.explanation;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffExplanationArchiveCompletenessTests {

    @Test
    void keepsEveryCatalogExplanationArchiveNonEmpty() throws IOException {
        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : receipts()) {
            Path explanationPath = explanation(receipt);

            assertThat(Files.size(explanationPath)).as(explanationPath.toString()).isGreaterThan(20L);
        }
    }

    @Test
    void keepsExplanationArchiveCompletenessPathVersionedToV263() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                        .CONSUMER_READINESS_HANDOFF_EXPLANATION_ARCHIVE_COMPLETENESS_EVIDENCE_PATH,
                263,
                "explanation-archive-completeness"
        );
    }
}
