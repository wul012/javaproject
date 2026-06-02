package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport.normalizeScopeText;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport.readmeLines;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport.readmePrefix;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReadmeDescriptionAlignmentTests {

    @Test
    void keepsEveryCatalogReadmeEntryDescriptiveAndScopeAligned() throws IOException {
        List<String> readmeLines = readmeLines();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : receipts()) {
            String prefix = readmePrefix(receipt);
            String line = readmeLines.stream()
                    .filter(candidate -> candidate.startsWith(prefix))
                    .findFirst()
                    .orElse("");

            assertThat(line).as("README line for v" + receipt.version()).contains("readiness handoff");
            assertThat(normalizeScopeText(line)).as("README line for v" + receipt.version())
                    .contains(normalizeScopeText(receipt.scope()));
        }
    }

    @Test
    void keepsReadmeDescriptionAlignmentPathVersionedToV276() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                        .CONSUMER_READINESS_HANDOFF_README_DESCRIPTION_ALIGNMENT_EVIDENCE_PATH,
                276,
                "readme-description-alignment"
        );
    }
}
