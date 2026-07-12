package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupConsumerSignoffPacketServiceTests {

  @Test
  void buildsConsumerSignoffPacketWithoutOpeningRuntimeCapabilities() {
    OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse packet =
        OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.consumerSignoffPacketService()
            .packet();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(397);
    assertThat(packet.project()).isEqualTo("advanced-order-platform");
    assertThat(packet.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(packet.readOnly()).isTrue();
    assertThat(packet.executionAllowed()).isFalse();
    assertThat(packet.consumerSignoffPacketEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-consumer-signoff-packet");
    assertThat(packet.consumerSignoffPacketProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-consumer-signoff-packet.v1");
    assertThat(packet.releaseEvidenceBundleEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-release-evidence-bundle");
    assertThat(packet.policyGuardEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-policy-guard");
    assertThat(packet.signoffItemCount()).isEqualTo(5);
    assertThat(packet.signoffItems())
        .extracting(OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse.SignoffItem::name)
        .containsExactly(
            "release-evidence-bundle",
            "policy-guard",
            "acceptance-receipt",
            "read-only-boundary",
            "execution-disabled");
    assertThat(packet.consumerInstruction()).contains("must not open write routing");
    assertThat(packet.status()).isEqualTo("passed");
  }
}
