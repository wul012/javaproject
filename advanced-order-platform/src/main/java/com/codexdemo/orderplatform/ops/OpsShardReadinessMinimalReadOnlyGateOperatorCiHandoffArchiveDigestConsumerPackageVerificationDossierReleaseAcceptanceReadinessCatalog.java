package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReadinessCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReadinessCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
            .ReleaseReadinessGate> gates(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                            source
            ) {
        return List.of(
                gate("source-dossier-status", "status=" + source.status(), 1,
                        "passed".equals(source.status()) ? 1 : 0),
                gate("source-package-snapshot", "source-dossier-snapshot=" + source.sourcePackageSnapshotCount(), 1,
                        source.sourcePackageSnapshotCount()),
                gate("section-digests", "section-digests=" + source.passedSectionDigestCount(), 9,
                        source.passedSectionDigestCount()),
                gate("audience-routes", "audience-routes=" + source.readyAudienceRouteCount(), 4,
                        source.readyAudienceRouteCount()),
                gate("ci-lanes", "ci-lanes=" + source.readOnlyCiLaneCount(), 5,
                        source.readOnlyCiLaneCount()),
                gate("boundary-audits", "boundary-audits=" + source.lockedBoundaryAuditCount(), 8,
                        source.lockedBoundaryAuditCount())
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
            .ReleaseReadinessGate gate(String code, String evidence, int expected, int actual) {
        boolean passed = expected == actual;
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                .ReleaseReadinessGate(code, evidence, expected, actual, passed, passed ? "passed" : "blocked");
    }
}
