package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughComplianceRegistryControllerTests {

  @Test
  void registryRouteExposesCodeWalkthroughComplianceEvidence() {
    assertThat(
            OpsShardReadinessCodeWalkthroughComplianceRoutePaths
                .CODE_WALKTHROUGH_COMPLIANCE_REGISTRY)
        .isEqualTo("/code-walkthrough-compliance-registry");

    var response =
        new OpsShardReadinessCodeWalkthroughComplianceRegistryController(
                new OpsShardReadinessCodeWalkthroughComplianceRegistryService())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-code-walkthrough-compliance-registry.v1");
    assertThat(response.version()).isEqualTo("Java v1747");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }
}
