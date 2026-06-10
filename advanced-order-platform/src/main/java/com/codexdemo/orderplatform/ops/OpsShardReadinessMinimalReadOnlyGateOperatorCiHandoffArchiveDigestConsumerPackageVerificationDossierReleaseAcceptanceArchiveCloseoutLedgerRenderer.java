package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveCloseoutLedgerRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveCloseoutLedgerRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                            .CloseoutLedgerEntry> ledger
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("closeout-ledger-count=" + ledger.size());
        ledger.forEach(entry -> lines.add(entry.order()
                + ". "
                + entry.item()
                + " | "
                + entry.owner()
                + " | "
                + entry.evidence()
                + " | "
                + OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                .flag("ready", entry.ready())
                + " | "
                + OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                .statusLine(entry.status())));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                .section("Closeout Ledger", lines);
    }
}
