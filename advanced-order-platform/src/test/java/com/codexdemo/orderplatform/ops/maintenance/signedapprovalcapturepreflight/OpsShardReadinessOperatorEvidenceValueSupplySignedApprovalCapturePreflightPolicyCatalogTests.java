package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalogTests {

  @Test
  void listsTwentyCapturePoliciesWithoutRuntimeOrSiblingMutation() {
    var policies =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
            .allPolicies();

    assertThat(policies)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .POLICY_COUNT);
    assertThat(policies.stream().map(policy -> policy.code()).collect(Collectors.toSet()))
        .hasSize(20);
    assertThat(policies)
        .allSatisfy(
            policy -> {
              assertThat(policy.policy()).isNotBlank();
              assertThat(policy.enforcement()).isNotBlank();
            });
    assertThat(policies).anySatisfy(policy -> assertThat(policy.code()).contains("NO_WRITE_ROUTE"));
    assertThat(policies)
        .anySatisfy(policy -> assertThat(policy.code()).contains("NO_SIBLING_MUTATION"));
  }
}
