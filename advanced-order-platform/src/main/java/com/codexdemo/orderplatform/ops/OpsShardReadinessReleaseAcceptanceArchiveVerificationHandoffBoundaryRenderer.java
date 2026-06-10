package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffBoundaryRenderer {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffBoundaryRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard> boundaries
    ) {
        List<String> lines = new ArrayList<>();
        lines.add("boundary-guard-count=" + boundaries.size());
        boundaries.forEach(boundary -> lines.add(String.join(
                " | ",
                boundary.code(),
                boundary.lockedBehavior(),
                boundary.auditEvidence(),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .flag("locked", boundary.locked()),
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .statusLine(boundary.status())
        )));
        return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .section("Boundary Guards", lines);
    }
}
