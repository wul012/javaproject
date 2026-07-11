package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryControllerMarkdownTests {

  @Test
  void registryRouteExposesOperatorCiHandoffEvidence() {
    assertThat(OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY)
        .isEqualTo("/minimal-read-only-gate-operator-ci-handoff-registry");

    var response =
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryController(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport.service())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-registry");
    assertThat(response.version()).isEqualTo("Java v1352");
    assertThat(response.sourceArchiveVersion()).isEqualTo("Java v1337");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void rendersStableOperatorCiHandoffMarkdownSections() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport.registry();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.MarkdownSection
                ::heading)
        .containsExactly(
            "Source Archive", "Operator Lanes", "CI Batches", "Boundary Locks", "Scorecard");
    assertThat(response.markdownSections().get(1).lines().get(0))
        .isEqualTo("operator-lane-count=4");
    assertThat(response.markdownSections().get(2).lines().get(0)).isEqualTo("ci-batch-count=5");
    assertThat(response.markdownSections().get(3).lines())
        .anySatisfy(line -> assertThat(line).contains("no-write-routing", "locked=true"));
  }

  @Test
  void aggregateChecksRemainStableAndBoundaryFocused() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport.registry();

    assertThat(response.checks()).hasSize(15);
    assertThat(response.checks())
        .contains(
            "minimal-read-only-gate-operator-ci-handoff-source-plan-Node v367",
            "minimal-read-only-gate-operator-ci-handoff-required-archive-Node v368",
            "minimal-read-only-gate-operator-ci-handoff-recommended-plan-Node v369",
            "minimal-read-only-gate-operator-ci-handoff-source-archive-version-Java v1337",
            "minimal-read-only-gate-operator-ci-handoff-lane-count-4",
            "minimal-read-only-gate-operator-ci-handoff-ci-batch-count-5",
            "minimal-read-only-gate-operator-ci-handoff-boundary-lock-count-8",
            "minimal-read-only-gate-operator-ci-handoff-no-upstream-autostart",
            "minimal-read-only-gate-operator-ci-handoff-no-write-routing",
            "minimal-read-only-gate-operator-ci-handoff-no-secret-value");
    assertThat(response.status()).isEqualTo("passed");
  }
}
