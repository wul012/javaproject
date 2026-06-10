package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRetentionWindowRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRetentionWindowRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                            .RetentionWindowEntry> windows
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("retention-window-count=" + windows.size());
        windows.forEach(window -> lines.add(String.join(
                " | ",
                window.name(),
                window.sourceEvidence(),
                window.retentionWindow(),
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                        .flag("ready", window.ready()),
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                        .statusLine(window.status())
        )));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                .section("Retention Windows", lines);
    }
}
