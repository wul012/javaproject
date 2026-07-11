package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport.walkthroughFileNames;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffWalkthroughIndexTests {

  @Test
  void keepsEveryCatalogReceiptCoveredByCodeWalkthroughArchive() throws IOException {
    List<String> walkthroughFiles = walkthroughFileNames();

    assertThat(receipts())
        .allSatisfy(
            receipt ->
                assertThat(walkthroughFiles)
                    .as("walkthrough for v" + receipt.version())
                    .anySatisfy(
                        fileName ->
                            assertThat(fileName).contains("version-" + receipt.version() + "-")));
  }

  @Test
  void keepsWalkthroughIndexEvidencePathVersionedToV246() {
    assertEvidencePath(
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_WALKTHROUGH_INDEX_EVIDENCE_PATH,
        246,
        "walkthrough-index");
  }
}
