package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.versions;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport.readme;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport.readmePrefix;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReadmeIndexTests {

    @Test
    void keepsEveryCatalogReceiptIndexedInEvidenceReadme() throws IOException {
        String readme = readme();

        assertThat(receipts())
                .allSatisfy(receipt -> assertThat(readme)
                        .as("README index for v" + receipt.version())
                        .contains(readmePrefix(receipt)));
    }

    @Test
    void keepsReadmeIndexOrderingAlignedWithCatalogVersions() throws IOException {
        String readme = readme();
        int previousIndex = -1;

        for (Integer version : versions()) {
            int currentIndex = readme.indexOf("- `" + version + "/`:");
            assertThat(currentIndex).as("README index for v" + version).isGreaterThan(previousIndex);
            previousIndex = currentIndex;
        }
    }

    @Test
    void keepsReadmeIndexEvidencePathVersionedToV245() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffService
                        .CONSUMER_READINESS_HANDOFF_README_INDEX_EVIDENCE_PATH,
                245,
                "readme-index"
        );
    }
}
