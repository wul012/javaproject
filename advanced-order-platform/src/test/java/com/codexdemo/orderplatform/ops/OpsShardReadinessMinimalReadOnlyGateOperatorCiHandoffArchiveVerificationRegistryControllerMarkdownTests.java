package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryControllerMarkdownTests {

  @Test
  void registryRouteExposesArchiveVerificationEvidence() {
    assertThat(
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY)
        .isEqualTo("/minimal-read-only-gate-operator-ci-handoff-archive-verification-registry");

    var response =
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryController(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport
                    .service())
            .registry();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-verification-registry");
    assertThat(response.version()).isEqualTo("Java v1377");
    assertThat(response.sourceHandoffVersion()).isEqualTo("Java v1352");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void rendersStableArchiveVerificationMarkdownSections() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport
            .registry();

    assertThat(response.markdownSectionCount()).isEqualTo(6);
    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .MarkdownSection
                ::heading)
        .containsExactly(
            "Source Handoff",
            "Artifact Verifications",
            "Operator Lane Verifications",
            "CI Batch Verifications",
            "Boundary Verifications",
            "Scorecard");
    assertThat(response.markdownSections().get(0).lines().get(0))
        .isEqualTo("source-handoff-count=1");
    assertThat(response.markdownSections().get(2).lines().get(0))
        .isEqualTo("operator-lane-verification-count=4");
    assertThat(response.markdownSections().get(4).lines())
        .anySatisfy(line -> assertThat(line).contains("no-write-routing", "locked=true"));
  }
}
