package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierHandoffCatalog {

    private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierHandoffCatalog() {
    }

    static List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.HandoffNote> notes() {
        return List.of(
                note("node", "Consume as frozen Java v90 context-normalization evidence for Node v1968-v1982."),
                note("java", "Keep managed audit connection execution blocked; this dossier is archive-only."),
                note("mini-kv", "Treat mini-kv v99 WAL regression evidence as sibling-only, with no Java startup."),
                note("operators", "Warnings are retained as evidence that missing runtime context does not authorize execution.")
        );
    }

    private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.HandoffNote note(
            String audience,
            String note
    ) {
        return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
                .HandoffNote(audience, note, true);
    }
}
