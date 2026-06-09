package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                            .BoundaryLock> locks
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("boundary-lock-count=" + locks.size());
        locks.forEach(lock -> lines.add(lock.code()
                + " | locked="
                + lock.locked()
                + " | "
                + lock.reason()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport.section(
                "Boundary Locks",
                lines
        );
    }
}
