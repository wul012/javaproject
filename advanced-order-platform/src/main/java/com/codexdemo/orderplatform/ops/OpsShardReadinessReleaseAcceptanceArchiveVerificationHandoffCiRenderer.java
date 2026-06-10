package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCiRenderer {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCiRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof> ciProofs
    ) {
        List<String> lines = new ArrayList<>();
        lines.add("ci-proof-count=" + ciProofs.size());
        ciProofs.forEach(ci -> lines.add(ci.order()
                + ". "
                + ci.batch()
                + " | "
                + ci.commandFamily()
                + " | "
                + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .flag("readOnly", ci.readOnly())
                + " | "
                + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .flag("sourcePassed", ci.sourcePassed())
                + " | "
                + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .statusLine(ci.status())));
        return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .section("CI Proofs", lines);
    }
}
