package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentIntakePacketModuleCatalog {

    private OpsShardReadinessCandidateDocumentIntakePacketModuleCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentIntakePacketResponse.ModuleEntry> modules() {
        return List.of(
                module(199, "source-lineage",
                        "locks Node v1421, Node v1411, Java v1117, and future-material boundary"),
                module(200, "intake-slots",
                        "groups twenty-five precheck checkpoints into ten compact intake slots"),
                module(201, "intake-guards",
                        "maps each slot to a fail-closed guard before material is supplied"),
                module(202, "artifact-handles",
                        "names archive references without accepting or importing material"),
                module(203, "route-closeout",
                        "exposes the read-only route and final stop condition")
        );
    }

    private static OpsShardReadinessCandidateDocumentIntakePacketResponse.ModuleEntry module(
            int order,
            String code,
            String responsibility
    ) {
        return new OpsShardReadinessCandidateDocumentIntakePacketResponse.ModuleEntry(
                order,
                code,
                responsibility,
                "java shard readiness owner",
                "passed"
        );
    }
}
