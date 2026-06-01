package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessV1Contract {

    static final String CONTRACT_NAME = "shard-readiness.v1";

    private static final List<String> MINIMAL_FIELDS = List.of(
            "project",
            "version",
            "readOnly",
            "executionAllowed",
            "shardEnabled",
            "shardCount",
            "slotCount",
            "routingMode",
            "evidencePath",
            "status"
    );

    private OpsShardReadinessV1Contract() {
    }

    static List<String> minimalFields() {
        return MINIMAL_FIELDS;
    }

    static boolean alignsWithReadOnlyContract(OpsShardReadinessResponse readiness) {
        return readiness.readOnly()
                && !readiness.executionAllowed()
                && !readiness.shardEnabled()
                && readiness.shardCount() == 0
                && readiness.slotCount() == 0
                && "fixture".equals(readiness.routingMode())
                && "passed".equals(readiness.status());
    }
}
