package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                            .ConsumerPacket> packets
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("consumer-packet-count=" + packets.size());
        packets.forEach(packet -> lines.add(packet.packet()
                + " | "
                + packet.owner()
                + " | digest="
                + packet.includesDigest()
                + " | boundary-locks="
                + packet.includesBoundaryLocks()
                + " | status="
                + packet.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport.section(
                "Consumer Packets",
                lines
        );
    }
}
