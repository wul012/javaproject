package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryConsumerReplayTests {

    @Test
    void preparesOperatorAndCiConsumerPackets() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
                        .registry();

        assertThat(response.consumerPacketCount()).isEqualTo(4);
        assertThat(response.readyConsumerPacketCount()).isEqualTo(4);
        assertThat(response.consumerPackets())
                .allSatisfy(packet -> {
                    assertThat(packet.includesDigest()).isTrue();
                    assertThat(packet.includesBoundaryLocks()).isTrue();
                    assertThat(packet.ready()).isTrue();
                    assertThat(packet.status()).isEqualTo("passed");
                });
        assertThat(response.consumerPackets())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                        .ConsumerPacket::packet)
                .containsExactly(
                        "operator-runbook-extract",
                        "ci-batch-matrix",
                        "boundary-lock-manifest",
                        "archive-scorecard-summary"
                );
    }

    @Test
    void replayInstructionsAreReadOnlyAndSourcePassed() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
                        .registry();

        assertThat(response.replayInstructions())
                .allSatisfy(instruction -> {
                    assertThat(instruction.readOnly()).isTrue();
                    assertThat(instruction.sourcePassed()).isTrue();
                    assertThat(instruction.status()).isEqualTo("passed");
                    assertThat(instruction.instruction()).contains("reuse archived");
                });
    }
}
