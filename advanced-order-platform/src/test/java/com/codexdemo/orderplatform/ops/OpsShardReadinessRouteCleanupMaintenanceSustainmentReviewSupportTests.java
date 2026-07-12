package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupportTests {

  @Test
  void buildsReadOnlyPassedReviewResponse() {
    OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse response =
        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
            "Java v535",
            "/api/v1/ops/shard-readiness/example",
            "java-example.v1",
            List.of(
                OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                    "contract-freeze",
                    "release-reviewer",
                    "frozen-minimal-fields",
                    OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.ENDPOINT)),
            List.of("example-check"));

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v535");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.itemCount()).isOne();
    assertThat(response.passedItemCount()).isOne();
    assertThat(response.sourcePlan()).isEqualTo("Node v549");
    assertThat(response.items().getFirst().status()).isEqualTo("passed");
    assertThat(response.checks())
        .contains("source-plan-Node v549", "example-check", "sustainment-review-remains-read-only");
    assertThat(response.status()).isEqualTo("passed");
  }
}
