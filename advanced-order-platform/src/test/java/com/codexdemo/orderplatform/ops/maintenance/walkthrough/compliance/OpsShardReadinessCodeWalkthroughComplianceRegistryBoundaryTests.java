package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughComplianceRegistryBoundaryTests {

  @Test
  void keepsRuntimeAndSecretBoundariesClosed() {
    var response = WalkthroughTestData.registry();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.startsJavaService()).isFalse();
    assertThat(response.startsMiniKvService()).isFalse();
    assertThat(response.readsCredentialValue()).isFalse();
    assertThat(response.resolvesRawEndpointUrl()).isFalse();
    assertThat(response.managedAuditHttpAllowed()).isFalse();
    assertThat(response.deniedBoundaryRuleCount()).isEqualTo(response.boundaryRuleCount());
    assertThat(response.boundaryRules())
        .extracting(OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.BoundaryRule::code)
        .contains(
            "no-write-routing",
            "no-active-shard-router",
            "no-credential-value",
            "no-raw-endpoint-url",
            "no-managed-audit-connection",
            "no-minikv-autostart");
  }

  @Test
  void emitsBoundaryChecksForOperatorScan() {
    var response = WalkthroughTestData.registry();

    assertThat(response.checks())
        .contains(
            "code-walkthrough-compliance-no-write-routing",
            "code-walkthrough-compliance-no-active-shard-router",
            "code-walkthrough-compliance-no-credential-value",
            "code-walkthrough-compliance-no-raw-endpoint-url",
            "code-walkthrough-compliance-no-managed-audit-connection",
            "code-walkthrough-compliance-no-upstream-autostart");
  }
}
