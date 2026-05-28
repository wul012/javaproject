package com.codexdemo.orderplatform.ops;

public record OpsShardReadinessResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean shardEnabled,
        int shardCount,
        int slotCount,
        String routingMode,
        String evidencePath,
        String status
) {
}
