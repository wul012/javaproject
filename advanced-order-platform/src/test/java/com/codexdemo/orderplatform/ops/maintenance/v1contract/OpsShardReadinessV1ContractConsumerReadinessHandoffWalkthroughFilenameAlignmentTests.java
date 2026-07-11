package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.receipts;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport.lowercaseWalkthroughFileNames;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffTextArchiveTestSupport.scopeSlug;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffWalkthroughFilenameAlignmentTests {

  @Test
  void keepsEveryCatalogWalkthroughFilenameAlignedToVersionAndScopeSlug() throws IOException {
    List<String> walkthroughFiles = lowercaseWalkthroughFileNames();

    for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
        receipt : receipts()) {
      String versionToken = "version-" + receipt.version() + "-";

      assertThat(walkthroughFiles)
          .as("walkthrough filename for v" + receipt.version())
          .anySatisfy(
              fileName -> assertThat(fileName).contains(versionToken).contains(scopeSlug(receipt)));
    }
  }

  @Test
  void keepsWalkthroughFilenameAlignmentPathVersionedToV277() {
    assertEvidencePath(
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_WALKTHROUGH_FILENAME_ALIGNMENT_EVIDENCE_PATH,
        277,
        "walkthrough-filename-alignment");
  }
}
