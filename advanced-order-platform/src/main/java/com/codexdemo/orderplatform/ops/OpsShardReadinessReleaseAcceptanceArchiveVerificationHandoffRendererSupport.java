package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection section(
            String heading,
            List<String> lines
    ) {
        return new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                .MarkdownSection(heading, List.copyOf(lines));
    }

    static String flag(String name, boolean value) {
        return name + "=" + value;
    }

    static String statusLine(String status) {
        return "status=" + status;
    }
}
