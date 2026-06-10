package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierCiLaneCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierCiLaneCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .CiLane> lanes(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                            source
            ) {
        return source.ciMatrix().stream()
                .map(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierCiLaneCatalog
                        ::lane)
                .toList();
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
            .CiLane lane(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                            .CiMatrixEntry source
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                .CiLane(
                        source.order(),
                        source.batch(),
                        source.commandFamily(),
                        source.readOnly(),
                        source.sourcePassed(),
                        replayGroup(source.commandFamily()),
                        source.status()
                );
    }

    private static String replayGroup(String commandFamily) {
        return switch (commandFamily) {
            case "focused" -> "focused-preflight";
            case "grouped" -> "grouped-non-docker-regression";
            case "build" -> "package-build";
            case "smoke" -> "read-only-smoke";
            default -> "read-only-review";
        };
    }
}
