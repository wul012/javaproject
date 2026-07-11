package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionGateBoundaryCatalogTests {

  @Test
  void capturesTwentyPassedGateChecks() {
    var gateChecks = OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckCatalog.gateChecks();

    assertThat(gateChecks)
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport.EXPECTED_GATE_CHECK_COUNT);
    assertThat(gateChecks).allSatisfy(check -> assertThat(check.passed()).isTrue());
    assertThat(gateChecks)
        .extracting(OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck::group)
        .contains("read-target", "runtime-boundary", "archive", "lineage");
    assertThat(gateChecks)
        .extracting(OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck::code)
        .contains("read-targets-five-of-five", "gate-checks-twenty-of-twenty");
  }

  @Test
  void deniesEveryForbiddenRuntimeBoundaryRule() {
    var boundaryRules =
        OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryPolicyCatalog.boundaryRules();

    assertThat(boundaryRules)
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport
                .EXPECTED_BOUNDARY_RULE_COUNT);
    assertThat(boundaryRules).allSatisfy(rule -> assertThat(rule.allowed()).isFalse());
    assertThat(boundaryRules)
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.BoundaryRule::code)
        .contains(
            "no-write-routing",
            "no-credential-value",
            "no-raw-endpoint-url",
            "no-managed-audit-connection",
            "no-java-autostart",
            "no-mini-kv-write-admin");
  }
}
