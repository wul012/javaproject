package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughDepthBoundaryTests {

  @Test
  void keepsDepthRegistryRuntimeFreeAndReadOnly() {
    var response = WalkthroughTestData.registry();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.startsJavaService()).isFalse();
    assertThat(response.startsMiniKvService()).isFalse();
    assertThat(response.readsCredentialValue()).isFalse();
    assertThat(response.resolvesRawEndpointUrl()).isFalse();
    assertThat(response.managedAuditHttpAllowed()).isFalse();
    assertThat(response.deniedBoundaryRuleCount()).isEqualTo(response.boundaryRuleCount());
  }

  @Test
  void namesAllForbiddenRuntimeActions() {
    var response = WalkthroughTestData.registry();

    assertThat(response.boundaryRules())
        .extracting(OpsShardReadinessCodeWalkthroughDepthRegistryResponse.BoundaryRule::code)
        .contains(
            "no-write-routing",
            "no-active-shard-router",
            "no-credential-value",
            "no-raw-endpoint-url",
            "no-managed-audit-http",
            "no-deployment-or-rollback",
            "no-java-autostart",
            "no-minikv-autostart");
  }
}
