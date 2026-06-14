package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveRegistryBoundaryTests {

  @Test
  void keepsArchiveSegmentationRuntimeFreeAndReadOnly() {
    var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.startsJavaService()).isFalse();
    assertThat(response.startsMiniKvService()).isFalse();
    assertThat(response.capturesScreenshot()).isFalse();
    assertThat(response.movesHistoricalArchive()).isFalse();
    assertThat(response.readsCredentialValue()).isFalse();
    assertThat(response.resolvesRawEndpointUrl()).isFalse();
    assertThat(response.managedAuditHttpAllowed()).isFalse();
    assertThat(response.deniedBoundaryRuleCount()).isEqualTo(response.boundaryRuleCount());
  }

  @Test
  void emitsChecksForNoRootDumpingAndNoRuntimeSideEffects() {
    var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

    assertThat(response.checks())
        .contains(
            "screenshot-explanation-archive-no-root-dumping",
            "screenshot-explanation-archive-no-screenshot-capture",
            "screenshot-explanation-archive-no-historical-move",
            "screenshot-explanation-archive-no-write-routing",
            "screenshot-explanation-archive-no-credential-value",
            "screenshot-explanation-archive-no-raw-endpoint-url",
            "screenshot-explanation-archive-no-upstream-autostart");
  }
}
