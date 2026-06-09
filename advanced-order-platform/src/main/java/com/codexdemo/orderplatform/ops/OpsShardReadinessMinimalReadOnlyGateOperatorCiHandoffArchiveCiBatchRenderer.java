package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                            .CiBatchVerification> batches
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("ci-batch-verification-count=" + batches.size());
        batches.forEach(batch -> lines.add(batch.order()
                + ". "
                + batch.batch()
                + " | "
                + batch.commandFamily()
                + " | archived="
                + batch.archived()
                + " | status="
                + batch.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport.section(
                "CI Batch Verifications",
                lines
        );
    }
}
