package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationRenderer {

    private OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                            .BoundaryVerification> boundaries
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("boundary-verification-count=" + boundaries.size());
        boundaries.forEach(boundary -> lines.add(String.join(
                " | ",
                boundary.code(),
                boundary.forbiddenAction(),
                "denied=" + boundary.denied(),
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport
                        .statusLine("status", boundary.status())
        )));
        return OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport.section(
                "Boundary Verification",
                lines
        );
    }
}
