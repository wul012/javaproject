package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestHandoffCatalog {

    private OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestHandoffCatalog() {
    }

    static List<OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.HandoffNote>
    notes() {
        return List.of(
                note("Node", "Reuse Java v99 and mini-kv v108 frozen references; do not request new Java writes."),
                note("Java", "Keep this endpoint read-only and outside the release approval mutation chain."),
                note("mini-kv", "Treat mini-kv v108 as non-participation evidence only; no startup or writes."),
                note("Ops", "Archive the manifest with the Node v1983-v2002 split closeout and CI result.")
        );
    }

    private static OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.HandoffNote
    note(String audience, String note) {
        return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                .HandoffNote(audience, note, true);
    }
}
